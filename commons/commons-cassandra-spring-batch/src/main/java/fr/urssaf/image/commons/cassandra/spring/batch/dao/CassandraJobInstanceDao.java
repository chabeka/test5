package fr.urssaf.image.commons.cassandra.spring.batch.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.*;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.template.*;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.*;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.*;

import org.apache.cassandra.thrift.IndexOperator;
import org.springframework.batch.admin.service.SearchableJobInstanceDao;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.util.Assert;

import fr.urssaf.image.commons.cassandra.helper.IndexedSlicesPredicateHelper;
import fr.urssaf.image.commons.cassandra.spring.batch.idgenerator.IdGenerator;
import fr.urssaf.image.commons.cassandra.spring.batch.serializer.JobParametersSerializer;

/**
 * Classe implémentant JobInstanceDao, qui utilise cassandra. L'implémentation
 * est inspirée de
 * org.springframework.batch.core.repository.dao.JdbcJobInstanceDao
 * 
 * @see org.springframework.batch.core.repository.dao.JdbcJobInstanceDao
 * @author Samuel Carrière
 */
public class CassandraJobInstanceDao extends AbstractCassandraDAO implements
      SearchableJobInstanceDao {

   private final IdGenerator idGenerator;

   /**
    * Constructeur
    * @param keyspace : keyspace cassandra 
    * @param idGenerator : générateur d'id pour les jobInstance
    */
   public CassandraJobInstanceDao(Keyspace keyspace, IdGenerator idGenerator) {
      super(keyspace);
      this.idGenerator = idGenerator;
   }

   /** {@inheritDoc} */
   public JobInstance createJobInstance(String jobName,
         JobParameters jobParameters) {
      Assert.notNull(jobName, "Job name must not be null.");
      Assert.notNull(jobParameters, "JobParameters must not be null.");

      Assert.state(getJobInstance(jobName, jobParameters) == null,
            "JobInstance must not already exist");

      /*
       * // En utilisant un entityManager, ça serait super simple :
       * 
       * UUID id = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
       * CassandraJobInstance instance = new CassandraJobInstance(id,
       * jobParameters, jobName); entityManager.save(instance);
       */

      long id = idGenerator.getNextId();
      JobInstance instance = new JobInstance(id, jobParameters, jobName);
      instance.incrementVersion();
      saveJobInstance(instance);

      return instance;
   }

   /**
    * Enregistre une jobInstance dans cassandra
    * 
    * @param instance
    */
   private void saveJobInstance(JobInstance instance) {
      // On enregistre l'instance dans JobInstance
      ColumnFamilyUpdater<Long, String> updater = jobInstanceTemplate
            .createUpdater(instance.getId());
      updater.setString(JI_NAME_COLUMN, instance.getJobName());
      JobParametersSerializer serializer = JobParametersSerializer.get();
      byte[] bytes = serializer.toBytes(instance.getJobParameters());
      updater.setByteArray(JI_PARAMETERS_COLUMN, bytes);
      byte[] jobKey = createJobKey(instance.getJobName(), instance
            .getJobParameters());
      updater.setByteArray(JI_JOB_KEY_COLUMN, jobKey);
      updater.setInteger(JI_VERSION, instance.getVersion());

      // On indexe l'instance dans JobInstancesByName
      ColumnFamilyUpdater<String, Long> updater2 = jobInstancesByNameTemplate
            .createUpdater(instance.getJobName());
      updater2.setByteArray(instance.getId(), new byte[0]);

      try {
         // On écrit dans cassandra
         jobInstanceTemplate.update(updater);
         jobInstancesByNameTemplate.update(updater2);
      } catch (RuntimeException e) {
         // En cas d'échec, on fait le ménage
         deleteJobInstance(instance.getId(), instance.getJobName());
         throw e;
      }
   }

   /**
    * Supprime une instance de Job de la base cassandra, ainsi que les entités
    * jobExecution et stepExecution associées
    * 
    * @param instanceId
    *           Id de l'instance à supprimer
    * @param executionDao
    *           DAO permettant de supprimer les executions de l'instance
    * @param stepExecutionDao
    *           DAO permettant de supprimer les steps de l'instance
    */
   public void deleteJobInstance(Long instanceId,
         CassandraJobExecutionDao executionDao,
         CassandraStepExecutionDao stepExecutionDao) {
      Assert.notNull(instanceId, "JobInstanceId cannot be null.");
      
      JobInstance jobInstance = getJobInstance(instanceId);
      // Suppression...
      deleteJobInstance(jobInstance, executionDao, stepExecutionDao);
   }

   /**
    * Supprime une instance de Job de la base cassandra, ainsi que les entités
    * jobExecution et stepExecution associées
    * 
    * @param jobInstance
    *           instance à supprimer
    * @param executionDao
    *           DAO permettant de supprimer les executions de l'instance
    * @param stepExecutionDao
    *           DAO permettant de supprimer les steps de l'instance
    */
   private void deleteJobInstance(JobInstance jobInstance,
         CassandraJobExecutionDao executionDao,
         CassandraStepExecutionDao stepExecutionDao) {
      // Suppression dans JobInstance et JobInstancesByName
      deleteJobInstance(jobInstance.getId(), jobInstance.getJobName());

      // suppression des jobExecution et des steps
      executionDao.deleteJobExecutionsOfInstance(jobInstance, stepExecutionDao);
   }

   /**
    * Supprime une instance de Job de la base cassandra
    * 
    * @param instanceId
    *           Id de l'instance à supprimer
    * @param jobName
    *           Nom du job
    */
   private void deleteJobInstance(Long instanceId, String jobName) {
      // Suppression dans JobInstance
      Mutator<Long> mutator = HFactory.createMutator(keyspace, LongSerializer
            .get());
      mutator.addDeletion(instanceId, JOBINSTANCE_CFNAME);
      mutator.execute();

      // Suppression dans JobInstancesByName
      Mutator<String> mutator2 = HFactory.createMutator(keyspace,
            StringSerializer.get());
      mutator2.addDeletion(jobName, JOBINSTANCES_BY_NAME_CFNAME, instanceId,
            LongSerializer.get());
      mutator2.execute();
   }

   /** {@inheritDoc} */
   public JobInstance getJobInstance(Long instanceId) {
      ColumnFamilyResult<Long, String> result = jobInstanceTemplate
            .queryColumns(instanceId);
      return getJobInstance(instanceId, result);
   }

   /**
    * Crée un jobInstance à partir de colonnes lues dans cassandra
    * 
    * @param instanceId
    * @param result
    * @return
    */
   private JobInstance getJobInstance(Long instanceId,
         ColumnFamilyResult<Long, String> result) {
      if (result == null || !result.hasResults()) return null;
      String jobName = result.getString(JI_NAME_COLUMN);
      byte[] serializedParams = result.getByteArray(JI_PARAMETERS_COLUMN);
      JobParametersSerializer serializer = JobParametersSerializer.get();
      JobParameters jobParameters = serializer.fromBytes(serializedParams);

      JobInstance instance = new JobInstance(instanceId, jobParameters, jobName);

      // On fait comme dans l'implémentation jdbc : on ne lit pas la version
      // stockée, car
      // "should always be at version=0 because they never get updated"
      instance.incrementVersion();

      return instance;
   }

   /** {@inheritDoc} */
   public JobInstance getJobInstance(JobExecution jobExecution) {
      // Récupération de l'id de l'instance à partir de l'id de l'exécution
      long instanceId = jobExecutionTemplate.querySingleColumn(
            jobExecution.getId(), JE_JOB_INSTANCE_ID_COLUMN,
            LongSerializer.get()).getValue();
      // Récupération de l'instance à partir de son id
      return getJobInstance(instanceId);
   }

   /** {@inheritDoc} */
   public JobInstance getJobInstance(String jobName, JobParameters jobParameters) {
      Assert.notNull(jobName, "Job name must not be null.");
      Assert.notNull(jobParameters, "JobParameters must not be null.");
      byte[] jobKey = createJobKey(jobName, jobParameters);

      IndexedSlicesPredicate<Long, String, byte[]> predicate = new IndexedSlicesPredicate<Long, String, byte[]>(
            LongSerializer.get(), StringSerializer.get(), BytesArraySerializer
                  .get());
      predicate.addExpression(JI_JOB_KEY_COLUMN, IndexOperator.EQ, jobKey);

      // Il est obligatoire de préciser une "start_key". (à ne pas confondre
      // avec Starsky !)
      // Ce "start_key" ne correspond pas à la clé de JobInstance, mais à la clé
      // de JobInstance.jobKey_idx
      // Il faut donc l'exprimer en bytes, mais l'API d'hector veut l'exprimer
      // en long, ce qui n'est pas bon.
      // Pour contourner le problème on passe par
      // IndexedSlicesPredicateHelper.setEmptyStartKey
      IndexedSlicesPredicateHelper.setEmptyStartKey(predicate);
      // predicate.startKey(new byte[0]);
      predicate.count(1);
      ColumnFamilyResult<Long, String> result = jobInstanceTemplate
            .queryColumns(predicate);

      if (!result.hasResults()) return null;
      long instanceId = result.getKey();
      return getJobInstance(instanceId, result);

      /*
       * // Solution non optimisée : on fait une requête nous permettant de
       * récupérer l'id de l'instance // On pourrait aussi en profiter pour
       * récupérer toutes colonnes de JobInstance
       * 
       * IndexedSlicesQuery<Long, String, byte[]> query =
       * HFactory.createIndexedSlicesQuery( keyspace, LongSerializer.get(),
       * StringSerializer.get(), BytesArraySerializer.get());
       * query.addEqualsExpression(JI_JOB_KEY_COLUMN, jobKey);
       * query.setColumnFamily(jobInstanceCFName);
       * 
       * 
       * //query.setRange("", "", false, 100); // On prend toutes les colonnes
       * query.setColumnNames(JI_JOB_KEY_COLUMN); // On ne prend qu'une colonne
       * query.setRowCount(1); QueryResult<OrderedRows<Long, String, byte[]>>
       * result = query.execute(); if (result == null || result.get().getCount()
       * == 0) return null; Row<Long, String, byte[]> row =
       * result.get().iterator().next(); long instanceId = row.getKey(); return
       * getJobInstance(instanceId);
       */
   }

   /**
    * Crée une "clé" permettant de résumer un job et ses paramètres.
    * Implémentation inspirée de celle de JdbcJobInstanceDao, sauf que cette
    * dernière est bugguée puisqu'elle ne tient pas compte de jobName.
    * 
    * @param jobParameters
    * @return la "clé" (correspond à un MD5)
    */
   protected byte[] createJobKey(String jobName, JobParameters jobParameters) {

      Map<String, JobParameter> props = jobParameters.getParameters();
      props.put("__jobName", new JobParameter(jobName));
      StringBuffer stringBuffer = new StringBuffer();
      List<String> keys = new ArrayList<String>(props.keySet());
      Collections.sort(keys);
      for (String key : keys) {
         JobParameter jobParameter = props.get(key);
         String value = jobParameter.getValue() == null ? "" : jobParameter
               .toString();
         stringBuffer.append(key + "=" + value + ";");
      }

      MessageDigest digest;
      try {
         digest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
         throw new IllegalStateException(
               "MD5 algorithm not available.  Fatal (should be in the JDK).");
      }
      try {
         byte[] bytes = digest
               .digest(stringBuffer.toString().getBytes("UTF-8"));
         return bytes;
      } catch (UnsupportedEncodingException e) {
         throw new IllegalStateException(
               "UTF-8 encoding not available.  Fatal (should be in the JDK).");
      }
   }

   /** {@inheritDoc} */
   public List<JobInstance> getJobInstances(String jobName, int start, int count) {
      // On se sert de JobInstancesByName, dont la clé est jobName, pour
      // récupérer les id des jobInstances
      SliceQuery<String, Long, Long> sliceQuery = HFactory.createSliceQuery(
            keyspace, StringSerializer.get(), LongSerializer.get(),
            LongSerializer.get()).setKey(jobName).setColumnFamily(
            JOBINSTANCES_BY_NAME_CFNAME);
      Long nullLong = null;
      Boolean reversed = true;
      ColumnSliceIterator<String, Long, Long> iterator = new ColumnSliceIterator<String, Long, Long>(
            sliceQuery, nullLong, nullLong, reversed);
      int compteur = 0;
      List<Long> jobIds = new ArrayList<Long>(count);
      while (iterator.hasNext()) {
         if (compteur >= start + count) break;
         HColumn<Long, Long> col = iterator.next();
         if (compteur >= start) {
            long instanceId = col.getName();
            jobIds.add(instanceId);
         }
         compteur++;
      }

      // Pour optimiser, on récupère tous les jobs d'un coup dans cassandra
      ColumnFamilyResult<Long, String> result = jobInstanceTemplate
            .queryColumns(jobIds);
      // Par contre, les lignes renvoyées ne sont pas triées. Il faut donc les trier
      
      Map<Long, JobInstance> map = new HashMap<Long, JobInstance>(jobIds.size());
      while (true) {
         if (result.hasResults()) {
            long instanceId = result.getKey();
            map.put(instanceId, getJobInstance(instanceId, result));
         }
         if (result.hasNext()) result = result.next(); else break;
      }
      
      // On renvoie les jobInstance dans l'ordre des jobIds
      List<JobInstance> jobs = new ArrayList<JobInstance>(count);
      for (Long jobId : jobIds) {
         if (map.containsKey(jobId)) {
            jobs.add(map.get(jobId));
         }
      }
      return jobs;
   }

   /**
    * {@inheritDoc} La liste est limitée à 500 réponses max
    * */
   public List<String> getJobNames() {
      // On se sert de JobInstancesByName, dont la clé est jobName
      RangeSlicesQuery<String, UUID, Long> query = HFactory
            .createRangeSlicesQuery(keyspace, StringSerializer.get(),
                  UUIDSerializer.get(), LongSerializer.get());
      query.setReturnKeysOnly();
      query.setColumnFamily(JOBINSTANCES_BY_NAME_CFNAME);
      query.setKeys(null, null);
      query.setRowCount(500);
      QueryResult<OrderedRows<String, UUID, Long>> result = query.execute();
      OrderedRows<String, UUID, Long> orderedRows = result.get();
      ArrayList<String> list = new ArrayList<String>();
      for (Row<String, UUID, Long> row : orderedRows) {
         String key = row.getKey();
         list.add(key);
      }
      return list;
   }

   @Override
   /** {@inheritDoc} */
   public int countJobInstances(String name) {
      return jobInstancesByNameTemplate.countColumns(name);
   }

}
