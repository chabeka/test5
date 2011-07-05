package fr.urssaf.image.sae.ecde.modele.source;

import java.io.File;

import org.apache.log4j.Logger;

/**
 *  Classe ECDESOURCE
 *  
 *  Cette classe permet la configuration d'un ECDE
 *  
 *  Elle a comme attribut :
 *  <ul>
 *    <li>Host : DNS de l'ECDE. Son format doit être conforme à la syntaxe du host dans une URL</li>
 *    <li>basePath : Chemin absolu du répertoire racine de l'ECDE accessible en local.</li>
 *  </ul>
 *  
 *  
 * 
 * */


public class EcdeSource {
   
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeSource.class);
 
   
   // Attributs
   private String host; 
   private File basePath;
   
   
   /**
    * @return the host
    */
   public final String getHost() {
      return host;
   }
   /**
    * @param host the host to set
    */
   public final void setHost(String host) {
      this.host = host;
   }
   /**
    * @return the basePath
    */
   public final File getBasePath() {
      return basePath;
   }
   /**
    * @param basePath the basePath to set
    */
   public final void setBasePath(File basePath) {
      this.basePath = basePath;
   }
   
}
