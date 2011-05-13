package fr.urssaf.image.sae.webservices.security.igc.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Classe de manipulation de {@link XMLConfiguration}
 * 
 * 
 */
public final class IgcUtils {

   private IgcUtils() {

   }
   
   private static final String IGC_CONFIG  = "src/test/resources/igcConfig.xml";

   /**
    * 
    * Création d'un fichier de configuration IgcConfig
    * 
    * @param newConfig
    *           chemin du nouveau fichier
    * @param acRacines
    *           chemin du repertoire AC racine
    * @param crls
    *           chemin du repertoire des CRL
    * @param urls
    *           URL de téléchargerment des CRLs
    */
   public static void loadConfig(String newConfig, String acRacines,
         String crls, String... urls) {

      XMLConfiguration newXML = new XMLConfiguration();

      try {
         newXML.load(IGC_CONFIG);

         newXML.addProperty("repertoireACRacines", acRacines);
         newXML.addProperty("repertoireCRL", crls);

         for (String url : urls) {

            newXML.addProperty("URLTelechargementCRL.url", url);
         }

         newXML.save(newConfig);

      } catch (ConfigurationException e) {

         throw new IllegalStateException(e);
      }
   }

}
