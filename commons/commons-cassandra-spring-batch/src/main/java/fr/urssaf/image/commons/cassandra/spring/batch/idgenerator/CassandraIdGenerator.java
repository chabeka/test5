package fr.urssaf.image.commons.cassandra.spring.batch.idgenerator;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.recipes.locks.InterProcessMutex;
import com.netflix.curator.framework.state.ConnectionState;
import com.netflix.curator.framework.state.ConnectionStateListener;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;

/**
 * Générateur d'id utilisant cassandra et zookeeper La valeur du dernier id
 * utilisé est stockée dans cassandra On utilise zookeeper pour accéder de
 * manière exclusive à la séquence
 * 
 */
public class CassandraIdGenerator implements IdGenerator {

   private static final Logger LOG = LoggerFactory
         .getLogger(CassandraIdGenerator.class);
   private static final String SEQUENCE_CF = "Sequences";
   private static final String SEQUENCE_KEY = "sequences";
   private final String sequenceName;
   private final ColumnFamilyTemplate<String, String> template;
   private final CuratorFramework curatorClient;
   private int lockTimeOut = 20;

   /**
    * Constructeur
    * @param keyspace      Keyspace cassandra
    * @param curatorClient Connexion à zookeeper
    * @param sequenceName  Nom de la séquence (doit identifier la séquence de manière unique)
    */
   public CassandraIdGenerator(Keyspace keyspace,
         CuratorFramework curatorClient, String sequenceName) {
      this.sequenceName = sequenceName;
      this.curatorClient = curatorClient;
      template = new ThriftColumnFamilyTemplate<String, String>(keyspace,
            SEQUENCE_CF, StringSerializer.get(), StringSerializer.get());

   }

   @Override
   public long getNextId() {

      // Il faut obtenir un lock avant d'accéder à la séquence
      final LockInfo lockInfo = new LockInfo();

      // Conformément aux recommandations d'utilisation de la classe de lock
      // (https://github.com/Netflix/curator/wiki/Shared-lock)
      // On capte les événements de déconnexion, car en cas de déconnexion, on
      // n'est plus sûr d'avoir le lock

      ConnectionStateListener stateListener = new ConnectionStateListener() {
         @Override
         public void stateChanged(CuratorFramework client,
               ConnectionState newState) {
            switch (newState) {
            case SUSPENDED:
            case LOST:
               lockInfo.lockOk = false;
               break;
            case RECONNECTED:
               lockInfo.lockOk = true;
               break;
            default:
            }
         }
      };
      curatorClient.getConnectionStateListenable().addListener(stateListener);

      try {

         InterProcessMutex mutex = new InterProcessMutex(curatorClient,
               "/sequences/" + sequenceName);
         try {
            if (!mutex.acquire(lockTimeOut, TimeUnit.SECONDS)) {
               throw new IdGeneratorException(
                     "Erreur lors de la tentative d'acquisition du lock pour la séquence "
                           + sequenceName
                           + " : on n'a pas obtenu le lock au bout de 20 secondes.");
            }
            // On a le lock.
            // On lit la valeur courante de la séquence
            long currentId = readCurrentSequenceValue();

            // On écrit dans cassandra la valeur incrémentée
            writeSequenceValue(currentId + 1);

            // On vérifie qu'on a toujours le lock. Si oui, on peut utiliser la
            // séquence. Pour cela, on force un événement ZK
            curatorClient.sync("/sequences", null);
            if (lockInfo.lockOk) {
               // On peut utiliser la valeur incrémentée
               return currentId + 1;
            } else {
               throw new IdGeneratorException(
                     "Erreur lors de la tentative d'acquisition du lock pour la séquence "
                           + sequenceName
                           + ". Problème de connexion zookeeper ?");
            }

         } catch (Exception e) {
            throw new IdGeneratorException(
                  "Erreur lors de la tentative d'acquisition du lock pour la séquence "
                        + sequenceName, e);
         } finally {
            try {
               if (mutex.isAcquiredInThisProcess()) mutex.release();
            } catch (Exception e) {
               LOG.error(
                     "Erreur lors de la libération du lock pour la séquence "
                           + sequenceName, e);
            }
         }
      } finally {
         curatorClient.getConnectionStateListenable().removeListener(
               stateListener);
      }
   }

   private long readCurrentSequenceValue() {
      HColumn<String, Long> col = template.querySingleColumn(SEQUENCE_KEY,
            sequenceName, LongSerializer.get());
      return col == null ? 0 : col.getValue();
   }

   private void writeSequenceValue(long value) {
      ColumnFamilyUpdater<String, String> updater = template
            .createUpdater(SEQUENCE_KEY);
      updater.setLong(sequenceName, value);
      template.update(updater);
   }

   /**
    * Spécifie le timeout du lock, en seconde (par défaut : 20s)
    * @param lockTimeOut timeout, en seconde
    */
   public void setLockTimeOut(int lockTimeOut) {
      this.lockTimeOut = lockTimeOut;
   }

   /**
    * Récupère la valeur du timeout pour le lock
    * @return  timeout, en seconde
    */
   public int getLockTimeOut() {
      return lockTimeOut;
   }

   private static class LockInfo {
      public boolean lockOk = true;
   }

}
