package fr.urssaf.image.sae.igc.util;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public final class ConfigurationUtils {

   private ConfigurationUtils() {

   }

   public static String getAbsolute(String path) {

      String absolutePath = null;

      if (path != null) {
         absolutePath = new File(path).getAbsolutePath();
      }

      return absolutePath;
   }
   
   private static final String IGC_CONFIG = "src/test/resources/igcConfig/";

   public static XMLConfiguration createConfig(String path) {
      try {
         return new XMLConfiguration(getIgcConfig(path));
      } catch (ConfigurationException e) {
         throw new IllegalArgumentException(e);
      }
   }

   public static String getIgcConfig(String file) {

      return new File(IGC_CONFIG + file).getAbsolutePath();
   }

}
