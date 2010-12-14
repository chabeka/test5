package fr.urssaf.image.sae.webdemo.service;

import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import fr.urssaf.image.commons.util.base64.Base64Decode;
import fr.urssaf.image.sae.vi.service.ValidateService;

/**
 * Service de connexion de l'application demo
 * 
 * 
 */
@Service
public class ConnectionService {

   @Autowired
   private ApplicationContext context;

   private final ValidateService validateService;

   /**
    * Initialisation du chemin du fichier XSD <code>sae-anais.xsd</code><br>
    * Le fichier se trouve dans src/main/resources
    * 
    */
   public ConnectionService() {
      try {
         String xsdPath = this.getClass().getResource("/sae-anais.xsd").toURI()
               .getPath();
         validateService = new ValidateService(xsdPath);
      } catch (URISyntaxException e) {
         throw new IllegalStateException(e);
      }
   }

   /**
    * Retourne si la servlet est valide dans l'application<br>
    * <br>
    * La méthode récupère les beans de type {@link AbstractUrlHandlerMapping} à
    * partir de {@link ApplicationContext} et vérifie si l'un d'entre contient
    * bien la servlet<br>
    * <br>
    * ex : /connection, /connection*, /connection/*, /accueil.html sont valides<br>
    * /service.html ne l'est pas
    * 
    * @see AbstractUrlHandlerMapping#getHandlerMap()
    * 
    * @param servlet
    *           url de la servlet
    * @return true si la servlet est valide faut sinon
    */
   public final boolean isValidateService(String servlet) {

      Map<String, AbstractUrlHandlerMapping> matchingBeans = BeanFactoryUtils
            .beansOfTypeIncludingAncestors(context,
                  AbstractUrlHandlerMapping.class);

      boolean inContext = false;

      for (AbstractUrlHandlerMapping handlerMapping : matchingBeans.values()) {

         inContext = handlerMapping.getHandlerMap().containsKey(servlet);
         if (inContext) {
            break;
         }
      }

      return inContext;
   }

   /**
    * Retourne si le VI est valide en s'appuyant sur le schéma XSD
    * <code>sae-anais.xsd</code><br>
    * <br>
    * Le jeton est décodé en base 64 en appelant la méthode
    * {@link Base64Decode#decode(String)}<br>
    * Le résultat décodé est validé par l'appel de la méthode
    * {@link ValidateService#isValidate(String)}<br>
    * 
    * @param samlResponse
    *           VI du jeton d'authentification en base 64
    * @return true si le jeton n'est pas conforme faux sinon
    */
   public final boolean isValidateVI(String samlResponse) {

      String decodeSaml = Base64Decode.decode(samlResponse);
      return validateService.isValidate(decodeSaml);

   }
}
