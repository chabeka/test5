package fr.urssaf.image.commons.cassandra.spring.batch.dao;

import me.prettyprint.hector.api.Keyspace;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.item.ExecutionContext;

/**
 * Classe implémentant ExecutionContextDao, qui utilise cassandra.
 * L'implémentation est inspirée de
 * org.springframework.batch.core.repository.dao.JdbcExecutionContextDao
 * 
 * @see org.springframework.batch.core.repository.dao.JdbcExecutionContextDao
 * @author Samuel Carrière
 * 
 */
public class CassandraExecutionContextDao extends AbstractCassandraDAO
      implements ExecutionContextDao {

   /**
    * Constructeur
    * @param keyspace : keyspace cassandra 
    */
   public CassandraExecutionContextDao(Keyspace keyspace) {
      super(keyspace);
   }

   @Override
   /** {@inheritDoc} */
   public final ExecutionContext getExecutionContext(JobExecution jobExecution) {
      // Dans l'implémentation cassandra, le contexte est désérialisé en même
      // temps que
      // les autres propriétés du jobExecution.
      return jobExecution.getExecutionContext();
   }

   @Override
   /** {@inheritDoc} */
   public final ExecutionContext getExecutionContext(StepExecution stepExecution) {
      // Dans l'implémentation cassandra, le contexte est désérialisé en même
      // temps que
      // les autres propriétés du stepExecution.
      return stepExecution.getExecutionContext();
   }

   /** {@inheritDoc} */
   public void saveExecutionContext(JobExecution jobExecution) {
      // Dans l'implémentation cassandra, le contexte est sérialisé en même
      // temps que
      // les autres propriétés du jobExecution.
      // Donc on ne fait rien de plus.
   }

   /** {@inheritDoc} */
   public void saveExecutionContext(StepExecution stepExecution) {
      // Dans l'implémentation cassandra, le contexte est sérialisé en même
      // temps que
      // les autres propriétés du stepExecution.
      // Donc on ne fait rien de plus.
   }

   /** {@inheritDoc} */
   public void updateExecutionContext(JobExecution jobExecution) {
      // Dans l'implémentation cassandra, le contexte est sérialisé en même
      // temps que
      // les autres propriétés du jobExecution.
      // Donc on ne fait rien de plus.

      // Sinon, on pourrait faire :
      /*
       * ColumnFamilyUpdater<Long, String> updater =
       * jobExecutionTemplate.createUpdater(jobExecution.getId());
       * Serializer<Object> os = ObjectSerializer.get();
       * updater.setByteArray(JS_EXECUTION_CONTEXT_COLUMN,
       * os.toBytes(jobExecution.getExecutionContext()));
       * jobExecutionTemplate.update(updater);
       */
   }

   /** {@inheritDoc} */
   public void updateExecutionContext(StepExecution stepExecution) {
      // Dans l'implémentation cassandra, le contexte est sérialisé en même
      // temps que
      // les autres propriétés du stepExecution.
      // Donc on ne fait rien de plus.

   }

}
