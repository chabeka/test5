package fr.urssaf.image.sae.integration.ihmweb.saeservice.utils;

import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.security.VIHandler;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;


/**
 * Méthodes utilitaires pour l'instanciation du Stub du service web SaeService
 */
public final class SaeServiceStubUtils {

   
   private SaeServiceStubUtils() {
      
   }
   
   
   /**
    * Renvoie le stub du service web, branché sur l'URL fournie en argument,
    * et configuré pour ajouter à l'en-tête SOAP le VI contenu dans le fichier
    * de ressource spécifié en paramètre.
    * 
    * @param urlServiceWeb l'URL du service web SaeService
    * @param ficRessourceVi le VI à intégrer au message SOAP, sous la forme de l'emplacement
    *                       du fichier de ressource contenant ce VI.
    * @return le stub
    */
   public static SaeServiceStub getServiceStub(
         String urlServiceWeb,
         String ficRessourceVi) {
      
      try {
         
         ConfigurationContext configContext = 
            ConfigurationContextFactory.createBasicConfigurationContext(
                  "axis2-sae-integration-ihmweb.xml");
         
         configContext.setProperty(
               VIHandler.PROP_FICHIER_VI, 
               ficRessourceVi);

         SaeServiceStub service = new SaeServiceStub(
               configContext,
               urlServiceWeb);
         
         
         // TODO : Pouvoir paramétrer le timeout
//         Options options = service._getServiceClient().getOptions();
//         options.setTimeOutInMilliSeconds(120000);
//         service._getServiceClient().setOptions(options);
//         
//         System.out.println("Timeout en ms : " + service._getServiceClient().getOptions().getTimeOutInMilliSeconds());
         
         
         
         return service;
         
      }
      catch(Exception e) {
         throw new IntegrationRuntimeException(e);
      }
      
      
   }
   
   
   /**
    * Renvoie le stub du service web, branché sur l'URL fournie en argument,
    * et configuré pour ajouter à l'en-tête SOAP le VI ne contenant pas les
    * informations d'authentification. 
    * 
    * @param urlServiceWeb l'URL du service web SaeService
    * @return le stub
    */
   public static SaeServiceStub getServiceStubSansVi(
         String urlServiceWeb) {
      return getServiceStub(
            urlServiceWeb,
            ViUtils.FIC_VI_SANS_VI);
   }
   
   
   /**
    * Renvoie le stub du service web, branché sur l'URL fournie en argument,
    * et configuré pour ajouter à l'en-tête SOAP le VI contenant des éléments
    * valides pour l'authentification.
    * 
    * @param urlServiceWeb l'URL du service web SaeService
    * @return le stub
    */
   public static SaeServiceStub getServiceStubAvecViOk(
         String urlServiceWeb) {
      return getServiceStub(
            urlServiceWeb,
            ViUtils.FIC_VI_OK);
   }
   
}
