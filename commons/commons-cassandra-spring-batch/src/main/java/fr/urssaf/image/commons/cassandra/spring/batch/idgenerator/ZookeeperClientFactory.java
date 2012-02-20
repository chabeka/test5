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
    * @throws IOException
    */
   public static CuratorFramework getClient(String connexionString, String namespace) throws IOException {
      Builder builder = CuratorFrameworkFactory.builder();
      builder.connectString(connexionString).namespace(namespace);
      builder.retryPolicy(new ExponentialBackoffRetry(100, 10));
      CuratorFramework zkClient = builder.build();
      zkClient.start();
      return zkClient;
   }
   
}
