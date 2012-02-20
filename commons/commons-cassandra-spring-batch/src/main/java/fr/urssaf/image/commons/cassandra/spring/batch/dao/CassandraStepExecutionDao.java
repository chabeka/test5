package fr.urssaf.image.commons.cassandra.spring.batch.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.serializers.*;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.*;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.admin.service.SearchableStepExecutionDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.util.Assert;

import fr.urssaf.image.commons.cassandra.serializer.NullableDateSerializer;
import fr.urssaf.image.commons.cassandra.spring.batch.idgenerator.IdGenerator;
import fr.urssaf.image.commons.cassandra.spring.batch.serializer.ExecutionContextSerializer;

/**
 * Classe implémentant StepExecutionDao, qui utilise cassandra. L'implémentation
 * est inspirée de
 * org.springframework.batch.core.repository.dao.JdbcStepExecutionDao
 * 
 * @see org.springframework.batch.core.repository.dao.JdbcStepExecutionDao
 * @see org.springframework.batch.admin.service.JdbcSearchableStepExecutionDao
 * @author Samuel Carrière
 */
public class CassandraStepExecutionDao extends AbstractCassandraDAO implements
      SearchableStepExecutionDao {

   private final IdGenerator idGenerator;

   /**
    * Constructeur
    * @param keyspace : keyspace cassandra 
    * @param idGenerator : générateur d'id pour les stepExecution
    */
   public CassandraStepExecutionDao(Keyspace keyspace, IdGenerator idGenerator) {
      super(keyspace);
      this.idGenerator = idGenerator;
   }

   /** {@inheritDoc} */
   @Override
   public void addStepExecutions(JobExecution jobExecution) {
      Assert.notNull(jobExecution, "JobExecution cannot be null.");
      Assert.notNull(jobExecution.getId(), "JobExecution Id cannot be null.");
      long jobExecutionId = jobExecution.getId();

      // Récupération des id des steps
      HSlicePredicate<Long> predicate = new HSlicePredicate<Long>(LongSerializer.get());
      predicate.setReversed(true);
      predicate.setCount(500);
      ColumnFamilyResult<Long, Long> result = jobExecutionToJobStepTemplate.queryColumns(jobExecutionId, predicate);
      Collection<Long> stepIds = result.getColumnNames();

      // Chargement des steps à partir des id trouvés
      getStepExecutionsFromIds(jobExecution, stepIds);
   }

   private Collection<StepExecution> getStepExecutionsFromIds(JobExecution jobExecution, Collection<Long> stepIds) {

      // On fait un multi-get pour récupérer toutes les lignes d'un coup
      MultigetSliceQuery<Long, String, byte[]> query = HFactory.createMultigetSliceQuery(keyspace, LongSerializer.get(), StringSerializer.get(), BytesArraySerializer.get());
      query.setKeys(stepIds);
      query.setColumnFamily(JOBSTEP_CFNAME);
      query.setRange(null, null, false, 500);
      QueryResult<Rows<Long, String, byte[]>> result = query.execute();
      Rows<Long, String, byte[]> rows = result.get();
      
      // On parcours les lignes dans l'ordre des stepIds
      List<StepExecution> list = new ArrayList<StepExecution>(stepIds.size());
      for (Long stepId : stepIds) {
         Row<Long, String, byte[]> row = rows.getByKey(stepId);
         list.add(getStepExecutionFromRow(jobExecution, stepId, row.getColumnSlice()));
      }
      return list;
   }

   /**
    * Méthode utilitaire pour récupérer la valeur d'une colonne
    * @param <T>           type de la valeur
    * @param slice         slice de colonnes contenant la colonne à lire
    * @param colName       nom de la colonne à lire
    * @param serializer    sérialiseur à utiliser    
    * @return
    */
   private <T> T getValue(ColumnSlice<String, byte[]> slice, String colName, Serializer<T> serializer) {
      byte[] bytes = slice.getColumnByName(colName).getValue();
      return serializer.fromBytes(bytes);
   }
   
   /**
    * Crée un objet StepExecution à partir d'une ligne lue de cassandra
    * 
    * @param jobExecution
    *           : le jobExecution référencé par le step à créer (éventuellement null)
    * @param result
    *           : données cassandra
    * @return
    */
   private StepExecution getStepExecutionFromRow(JobExecution jobExecution, Long stepId,
         ColumnSlice<String, byte[]> slice) {
      
      Serializer<String> ss = StringSerializer.get();
      Serializer<Integer> is = IntegerSerializer.get();
      Serializer<Date> ds = NullableDateSerializer.get();
      Serializer<ExecutionContext> os = ExecutionContextSerializer.get();

      if (slice.getColumns().size() == 0) return null;
      String stepName = getValue(slice, JS_STEP_NAME_COLUMN, ss);
      StepExecution step;
      if (jobExecution == null) {
         step = new StepExecution(stepName, null);
      }
      else {
         step = new StepExecution(stepName, jobExecution, stepId);         
      }
      step.setVersion(getValue(slice, JS_VERSION_COLUMN, is));
      step.setStartTime(getValue(slice, JS_START_TIME_COLUMN, ds));
      step.setEndTime(getValue(slice, JS_END_TIME_COLUMN, ds));
      step.setStatus(BatchStatus.valueOf(getValue(slice, JS_STATUS_COLUMN, ss)));
      step.setCommitCount(getValue(slice, JS_COMMITCOUNT_COLUMN, is));
      step.setReadCount(getValue(slice, JS_READCOUNT_COLUMN, is));
      step.setFilterCount(getValue(slice, JS_FILTERCOUNT_COLUMN, is));
      step.setWriteCount(getValue(slice, JS_WRITECOUNT_COLUMN, is));
      step.setReadSkipCount(getValue(slice, JS_READSKIPCOUNT_COLUMN, is));
      step.setWriteSkipCount(getValue(slice, JS_WRITESKIPCOUNT_COLUMN, is));
      step.setProcessSkipCount(getValue(slice, JS_PROCESSSKIPCOUNT_COLUMN, is));
      step.setRollbackCount(getValue(slice, JS_ROLLBACKCOUNT_COLUMN, is));
      String exitCode = getValue(slice, JS_EXIT_CODE_COLUMN, ss);
      String exitMessage = getValue(slice, JS_EXIT_MESSAGE_COLUMN, ss);
      step.setExitStatus(new ExitStatus(exitCode, exitMessage));
      step.setLastUpdated(getValue(slice, JS_LAST_UPDATED_COLUMN, ds));
      ExecutionContext executionContext = getValue(slice, JS_EXECUTION_CONTEXT_COLUMN, os);
      step.setExecutionContext(executionContext);
      return step;
      
   }

   /** {@inheritDoc} */
   public StepExecution getStepExecution(JobExecution jobExecution,
         Long stepExecutionId) {

      SliceQuery<Long, String, byte[]> query = HFactory.createSliceQuery(keyspace, LongSerializer.get(), StringSerializer.get(), BytesArraySerializer.get());
      query.setKey(stepExecutionId);
      query.setColumnFamily(JOBSTEP_CFNAME);
      query.setRange(null, null, false, 500);
      QueryResult<ColumnSlice<String,byte[]>> result = query.execute();
      ColumnSlice<String, byte[]> slice = result.get();
      if (slice == null) return null;
      return getStepExecutionFromRow(jobExecution, stepExecutionId, slice);
   }

   /** {@inheritDoc} */
   public void saveStepExecution(StepExecution stepExecution) {
      Assert
            .isNull(stepExecution.getId(),
                  "to-be-saved (not updated) StepExecution can't already have an id assigned");
      Assert
            .isNull(stepExecution.getVersion(),
                  "to-be-saved (not updated) StepExecution can't already have a version assigned");
      validateStepExecution(stepExecution);

      stepExecution.incrementVersion();

      long stepId = idGenerator.getNextId();
      stepExecution.setId(stepId);

      saveStepExecutionToCassandra(stepExecution);
   }

   /**
    * Enregistre un step dans cassandra Le step doit avoir un id affecté.
    * 
    * @param stepExecution
    *           : step à enregistrer
    */
   private void saveStepExecutionToCassandra(StepExecution stepExecution) {
      Serializer<ExecutionContext> os = ExecutionContextSerializer.get();
      Serializer<Date> ds = NullableDateSerializer.get();
      Serializer<Long> ls = LongSerializer.get();
      Serializer<String> ss = StringSerializer.get();
      Serializer<byte[]> bs = BytesArraySerializer.get();
      ColumnFamilyUpdater<Long, String> updater = jobStepTemplate
            .createUpdater(stepExecution.getId());

      updater.setLong(JS_JOB_EXECUTION_ID_COLUMN, stepExecution
            .getJobExecutionId());
      updater.setInteger(JS_VERSION_COLUMN, stepExecution.getVersion());
      updater.setString(JS_STEP_NAME_COLUMN, stepExecution.getStepName());
      updater.setByteArray(JS_START_TIME_COLUMN, ds.toBytes(stepExecution
            .getStartTime()));
      updater.setByteArray(JS_END_TIME_COLUMN, ds.toBytes(stepExecution
            .getEndTime()));
      updater.setString(JS_STATUS_COLUMN, stepExecution.getStatus().name());
      updater.setInteger(JS_COMMITCOUNT_COLUMN, stepExecution.getCommitCount());
      updater.setInteger(JS_READCOUNT_COLUMN, stepExecution.getReadCount());
      updater.setInteger(JS_FILTERCOUNT_COLUMN, stepExecution.getFilterCount());
      updater.setInteger(JS_WRITECOUNT_COLUMN, stepExecution.getWriteCount());
      updater.setInteger(JS_READSKIPCOUNT_COLUMN, stepExecution
            .getReadSkipCount());
      updater.setInteger(JS_WRITESKIPCOUNT_COLUMN, stepExecution
            .getWriteSkipCount());
      updater.setInteger(JS_PROCESSSKIPCOUNT_COLUMN, stepExecution
            .getProcessSkipCount());
      updater.setInteger(JS_ROLLBACKCOUNT_COLUMN, stepExecution
            .getRollbackCount());
      updater.setString(JS_EXIT_CODE_COLUMN, stepExecution.getExitStatus()
            .getExitCode());
      updater.setString(JS_EXIT_MESSAGE_COLUMN, stepExecution.getExitStatus()
            .getExitDescription());
      updater.setByteArray(JS_LAST_UPDATED_COLUMN, ds.toBytes(stepExecution
            .getLastUpdated()));
      updater.setByteArray(JS_EXECUTION_CONTEXT_COLUMN, os
            .toBytes(stepExecution.getExecutionContext()));

      // On écrit dans cassandra
      jobExecutionTemplate.update(updater);
      
      // Alimentation des différents index
      Long stepId = stepExecution.getId();
      Long jobExecutionId = stepExecution.getJobExecutionId();
      String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
      final byte[] EMPTY = new byte[0];
      Mutator<byte[]> mutator = HFactory.createMutator(keyspace, bs);

      // Dans JobExecutionToJobStep 
      //    clé = jobExecutionId
      //    Nom de colonne = jobStepId
      //    Valeur = vide
      mutator.addInsertion(ls.toBytes(jobExecutionId) , JOBEXECUTION_TO_JOBSTEP_CFNAME, 
            HFactory.createColumn(stepId, EMPTY, ls, bs));
      
      // Dans JobSteps 
      //    clé = "jobSteps"
      //    Nom de colonne = stepId
      //    Valeur = composite(jobName, stepName)
      Composite value = new Composite();
      value.addComponent(jobName, ss);
      value.addComponent(stepExecution.getStepName(), ss);
      mutator.addInsertion(ss.toBytes(JOB_STEPS_KEY) , JOBSTEPS_CFNAME, 
            HFactory.createColumn(stepId, value, ls, CompositeSerializer.get()));

      mutator.execute();
      
   }

   /**
    * Validate StepExecution. At a minimum, JobId, StartTime, and Status cannot
    * be null. EndTime can be null for an unfinished job.
    * 
    * @param value
    * @throws IllegalArgumentException
    */
   private void validateStepExecution(StepExecution stepExecution) {
      Assert.notNull(stepExecution);
      Assert.notNull(stepExecution.getStepName(),
            "StepExecution step name cannot be null.");
      Assert.notNull(stepExecution.getStartTime(),
            "StepExecution start time cannot be null.");
      Assert.notNull(stepExecution.getStatus(),
            "StepExecution status cannot be null.");
   }

   /** {@inheritDoc} */
   public void updateStepExecution(StepExecution stepExecution) {
      // Le nom de la méthode n'est pas super explicite, mais is s'agit
      // d'enregister le stepExecution
      // en base de données.

      validateStepExecution(stepExecution);
      Assert.notNull(stepExecution.getId(),
            "StepExecution Id cannot be null. StepExecution must saved"
                  + " before it can be updated.");
      stepExecution.incrementVersion();
      saveStepExecutionToCassandra(stepExecution);
   }

   /**
    * Supprime un step de la base de données
    * 
    * @param stepExecutionId
    *           : id du step à supprimer
    */
   public void deleteStepExecution(Long stepExecutionId) {
      jobStepTemplate.deleteRow(stepExecutionId);
      jobStepsTemplate.deleteColumn(JOB_STEPS_KEY, stepExecutionId);
   }

   /**
    * Supprime tous les steps d'un jobExecution donné
    * 
    * @param jobExecution
    *           : jobExecution concerné
    */
   public void deleteStepsOfExecution(JobExecution jobExecution) {
      Collection<StepExecution> steps = jobExecution.getStepExecutions();
      for (StepExecution stepExecution : steps) {
         deleteStepExecution(stepExecution.getId());
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   /** {@inheritDoc} */
   public int countStepExecutions(String jobNamePattern, String stepNamePattern) {
      ColumnFamilyResult<String, Long> result = jobStepsTemplate.queryColumns(JOB_STEPS_KEY);
      CompositeSerializer cs = CompositeSerializer.get();
      Serializer<String> ss = StringSerializer.get();
      
      Collection<Long> colNames = result.getColumnNames();
      int compteur = 0;
      for (Long colName : colNames) {
         byte[] value = result.getByteArray(colName);
         Composite c = cs.fromBytes(value);
         String jobName = (String) c.getComponent(0).getValue(ss);
         String stepName = (String) c.getComponent(1).getValue(ss);
         if (checkPattern(jobNamePattern, jobName) && checkPattern(stepNamePattern, stepName)) compteur ++;
      }
      return compteur;
   }

   /**
    * Renvoie vrai si valeur "value" respect le pattern "pattern"
    * @param pattern  pattern ou "*" représente n'importe quelle séquence de caractères
    * @param value    valeur à tester
    * @return
    */
   private boolean checkPattern(String pattern, String value) {
      String patString = pattern.replaceAll(
            "(\\[|\\]|\\(|\\)|\\^|\\$|\\{|\\}|\\.|\\?|\\+|\\*|\\\\)", "\\\\$1");
      patString = StringUtils.replace(patString, "\\*", ".*");
      Pattern patt = Pattern.compile(patString);
      Matcher matcher = patt.matcher(value);
      return matcher.matches();
   }
   
   @SuppressWarnings("unchecked")
   @Override
   /** {@inheritDoc} */
   public Collection<StepExecution> findStepExecutions(String jobNamePattern,
         String stepNamePattern, int start, int count) {
      
      CompositeSerializer cs = CompositeSerializer.get();
      Serializer<String> ss = StringSerializer.get();
      Serializer<Long> ls = LongSerializer.get();
      
      SliceQuery<String, Long, Composite> sliceQuery = HFactory.createSliceQuery(
            keyspace, ss, ls, cs);
      sliceQuery.setKey(JOB_STEPS_KEY);
      sliceQuery.setColumnFamily(JOBSTEPS_CFNAME);
      
      ColumnSliceIterator<String, Long, Composite> iterator = new ColumnSliceIterator<String, Long, Composite>(
            sliceQuery, Long.MAX_VALUE, Long.MIN_VALUE, true);

      int compteur = 0;
      List<Long> stepIds = new ArrayList<Long>(count);
      while (iterator.hasNext()) {
         HColumn<Long, Composite> col = iterator.next();
         Long stepId = col.getName();
         Composite c = col.getValue();
         String jobName = (String) c.getComponent(0).getValue(ss);
         String stepName = (String) c.getComponent(1).getValue(ss);
         if (checkPattern(jobNamePattern, jobName) && checkPattern(stepNamePattern, stepName)) {
            compteur ++;
            if (compteur >= start) {
               stepIds.add(stepId);
            }
            if (compteur == count + start) break;
         }
      }
      return getStepExecutionsFromIds(null, stepIds);
   }

   @SuppressWarnings("unchecked")
   @Override
   /** {@inheritDoc} */
   public Collection<String> findStepNamesForJobExecution(String jobName,
         String excludesPattern) {
      CompositeSerializer cs = CompositeSerializer.get();
      Serializer<String> ss = StringSerializer.get();
      
      ColumnFamilyResult<String, Long> result = jobStepsTemplate.queryColumns(JOB_STEPS_KEY);
      
      Collection<Long> colNames = result.getColumnNames();
      Set<String> stepNames = new HashSet<String>();
      for (Long colName : colNames) {
         byte[] value = result.getByteArray(colName);
         Composite c = cs.fromBytes(value);
         String currentJobName = (String) c.getComponent(0).getValue(ss);
         String currentStepName = (String) c.getComponent(1).getValue(ss);
         if (currentJobName.equals(jobName) && ! checkPattern(excludesPattern, currentStepName)) {
            stepNames.add(currentStepName);
         }
      }
      return stepNames;
   }

}
