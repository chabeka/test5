package fr.urssaf.image.commons.cassandra.spring.batch.helper;

import org.cassandraunit.DataLoader;
import org.cassandraunit.dataset.xml.ClassPathXmlDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.curator.test.TestingServer;


/**
 * Classe facilitant la mise en place des tests unitaires cassandra et zookeeper
 * Ces "factories" peuvent être instanciées par spring
 *
 */
public final class CassandraTestHelper {

   private static final Logger LOG = LoggerFactory.getLogger(CassandraTestHelper.class);

   /**
    * Classe statique
    */
   private CassandraTestHelper() {
   }

   /**
    * Permet de lancer un serveur cassandra local (sur le port 9171),
    * et optionnellement de charger un jeu de données
    * @param init          : vrai s'il faut lancer un serveur cassandra local
    * @param dataSet       : nom du jeu de données XML à charger (éventuellement null)
    * @return     le retour n'est pas utilisé, mais nécessaire sinon spring râle 
    * @throws Exception    : si une erreur survient
    */
   public static boolean initLocalCassandraServer(boolean init, String dataSet) throws Exception {
      LOG.debug("initLocalCassandraServer : init={} - dataSet={}", init, dataSet);
      if (!init) return false;
      EmbeddedCassandraServerHelper.startEmbeddedCassandra();
      if (dataSet!=null && !dataSet.isEmpty()) {
         DataLoader dataLoader = new DataLoader("TestCluster", "localhost:9171");
         ClassPathXmlDataSet set = new ClassPathXmlDataSet(dataSet);
         LOG.debug("initLocalCassandraServer : keyspace={}", set.getKeyspace().getName());
         dataLoader.load(set);
      }
      return true;
   }
   
   /**
    * Permet de lancer un serveur zookeeper local,
    * et optionnellement de charger un jeu de données
    * @param init          : vrai s'il faut lancer un serveur zookeeper local
    * @param port          : n° du port à utiliser pour le serveur (normalement : 2181)
    * @return  Le serveur créé, ou null
    * @throws Exception    : si une erreur survient
    */
   public static TestingServer initLocalZookeeperServer(boolean init, int port) throws Exception {
      LOG.debug("initLocalZookeeperServer : init={} - port={}", init, port);
      if (init) return new TestingServer(port);
      return null;
   }
   
}
