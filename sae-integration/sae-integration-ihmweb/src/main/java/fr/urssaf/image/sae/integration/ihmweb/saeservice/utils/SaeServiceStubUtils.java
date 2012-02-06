package fr.urssaf.image.sae.integration.ihmweb.saeservice.utils;

import java.util.List;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.engine.Phase;

import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.modele.SaeServiceStub;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.security.LogInMessageHandler;
import fr.urssaf.image.sae.integration.ihmweb.saeservice.security.VIHandler;
import fr.urssaf.image.sae.integration.ihmweb.utils.ViUtils;

/**
 * Méthodes utilitaires pour l'instanciation du Stub du service web SaeService
 */
public final class SaeServiceStubUtils {

   private SaeServiceStubUtils() {

   }

   
   /**
    * Renvoie le stub du service web, branché sur l'URL fournie en argument, et
    * configuré pour ajouter à l'en-tête SOAP le VI contenu dans le fichier de
    * ressource spécifié en paramètre.
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @param ficRessourceVi
    *           le VI à intégrer au message SOAP, sous la forme de l'emplacement
    *           du fichier de ressource contenant ce VI.
    * @return le stub
    */
   public static SaeServiceStub getServiceStub(String urlServiceWeb,
         String ficRessourceVi) {

      try {

         // Création d'une configuration Axis2 par défaut
         ConfigurationContext configContext = 
            ConfigurationContextFactory.createConfigurationContextFromFileSystem(null , null) ;
         
         // ----------------------------------------------
         // Gestion du VI + Log du message SOAP de request
         // ----------------------------------------------
         
         // 1) Ajout d'une propriété dans laquelle on met le nom du fichier de VI
         //    à inclure dans le message SOAP. L'inclusion sera faite dans un handler
         configContext.setProperty(VIHandler.PROP_FICHIER_VI, ficRessourceVi);
         
         // 2) Ajout d'un Handler lors de la phase "MessageOut" pour insérer le VI
         AxisConfiguration axisConfig = configContext.getAxisConfiguration();
         List<Phase> outFlowPhases = axisConfig.getOutFlowPhases();
         Phase messageOut = findPhaseByName(outFlowPhases,"MessageOut");
         messageOut.addHandler(new VIHandler());
         
         
         // ----------------------------------------------
         // Log du message SOAP de response
         // ----------------------------------------------
         
         List<Phase> inFlowPhases = axisConfig.getInFlowPhases();
         Phase dispatch = findPhaseByName(inFlowPhases,"Dispatch");
         dispatch.addHandler(new LogInMessageHandler());
         
         List<Phase> inFaultPhases = axisConfig.getInFaultFlowPhases();
         dispatch = findPhaseByName(inFaultPhases,"Dispatch");
         dispatch.addHandler(new LogInMessageHandler());
         
         
         // Création du Stub
         SaeServiceStub service = new SaeServiceStub(configContext,
               urlServiceWeb);
         
         // Renvoie du Stub
         return service;

      } catch (Exception e) {
         throw new IntegrationRuntimeException(e);
      }

   }

   /**
    * Renvoie le stub du service web, branché sur l'URL fournie en argument, et
    * configuré pour ajouter à l'en-tête SOAP le VI ne contenant pas les
    * informations d'authentification.
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @return le stub
    */
   public static SaeServiceStub getServiceStubSansVi(String urlServiceWeb) {
      return getServiceStub(urlServiceWeb, ViUtils.FIC_VI_SANS_VI);
   }

   /**
    * Renvoie le stub du service web, branché sur l'URL fournie en argument, et
    * configuré pour ajouter à l'en-tête SOAP le VI contenant des éléments
    * valides pour l'authentification.
    * 
    * @param urlServiceWeb
    *           l'URL du service web SaeService
    * @return le stub
    */
   public static SaeServiceStub getServiceStubAvecViOk(String urlServiceWeb) {
      return getServiceStub(urlServiceWeb, ViUtils.FIC_VI_OK);
   }
   
   
   
   private static Phase findPhaseByName(List<Phase> phases, String nomPhaseRecherchee) {
      
      Phase result = null;
      
      for(Phase phase: phases) {
         if (phase.getName().equals(nomPhaseRecherchee)) {
            result = phase;
            break;
         }
      }
      
      return result;
      
   }

}
