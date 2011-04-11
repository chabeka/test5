package fr.urssaf.image.sae.webservice.client.demo.util;

import java.io.InputStream;

/**
 * Classe utilitaire de {@link ClassLoader}
 * 
 * 
 */
public final class ResourceUtils {

   private ResourceUtils() {

   }

   /**
    * retourne un fichier contenu dans le classpath
    * 
    * @param object
    *           instance d'une classe du classpth
    * @param path
    *           chemin du fichier
    * @return fichier correspondant au path
    */
   public static InputStream loadResource(Object object, String path) {

      return object.getClass().getClassLoader().getResourceAsStream(path);
   }
}
