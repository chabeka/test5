package fr.urssaf.image.commons.cassandra.spring.batch.idgenerator;

import java.io.IOException;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.CuratorFrameworkFactory.Builder;
import com.netflix.curator.retry.ExponentialBackoffRetry;

/**
 * Factory pour récupérer une connexion à zookeeper
 *
 */
public final class ZookeeperClientFactory {

   private static final int BASE_SLEEP_TIME = 100;    // En milli-secondes
   private static final int MAX_RETRIES = 10;
   /**
    * Constructeur privé
    */
   private ZookeeperClientFactory() {
   }

   /**
    * Renvoie une connexion à zookeeper
    * @param connexionString  chaîne de connexion (format : "serveur:port")
    * @param namespace        namespace de l'application
    * @return                 connexion
    * @throws IOException     quand on n'arrive pas à joindre zookeeper
    */
   public static CuratorFramework getClient(String connexionString, String namespace) throws IOException {
      Builder builder = CuratorFrameworkFactory.builder();
      builder.connectString(connexionString).namespace(namespace);
      builder.retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES));
      CuratorFramework zkClient = builder.build();
      zkClient.start();
      return zkClient;
   }
   
}
