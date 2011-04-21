package fr.urssaf.image.commons.jms.spring.exemple;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe d'execution du serveur via JMS
 * 
 * 
 */
public final class Server {

   private Server() {

   }

   /**
    * instanciation des fichiers de configuration de jsm et producer
    * 
    * @param args
    *           non utilisé
    */
   public static void main(String[] args) {
      new ClassPathXmlApplicationContext(new String[] {
            "applicationContext-server.xml", "applicationContext-jms.xml" });

   }

}
