package fr.urssaf.image.sae.webdemo.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.xml.sax.SAXException;

import fr.urssaf.image.commons.util.base64.Base64Decode;
import fr.urssaf.image.commons.util.resource.ResourceUtil;
import fr.urssaf.image.commons.xml.XSDValidator;
import fr.urssaf.image.commons.xml.XSDValidator.SAXParseExceptionType;

/**
 * Service de connexion de l'application demo
 * 
 * 
 */
@Service
public class ConnectionService {

   @Autowired
   private ApplicationContext context;

   private final String xsdPath;

   /**
    * Initialisation du chemin du fichier XSD <code>sae-anais.xsd</code><br>
    * Le fichier se trouve dans src/main/resources
    * 
    */
   public ConnectionService() {

      try {
         xsdPath = ResourceUtil.getResourceFullPath(this, "/sae-anais.xsd");
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
         System.out.println(handlerMapping.getHandlerMap());
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
    * {@link XSDValidator#validXMLStringWithSAX(String, String)}<br>
    * <br>
    * Si le jeton comporte la moindre {@link SAXParseExceptionType} ou lève une
    * exception alors le jeton est considéré comme invalide
    * 
    * @param samlResponse
    *           VI du jeton d'authentification en base 64
    * @return true si le jeton n'est pas conforme faux sinon
    */
   @SuppressWarnings("PMD")
   public final boolean isValidateVI(String samlResponse) {

      String decodeSaml = Base64Decode.decode(samlResponse);

      boolean validate = false;

      try {
         List<SAXParseExceptionType> exceptions = XSDValidator
               .validXMLStringWithSAX(decodeSaml, xsdPath);
         XSDValidator.afficher(exceptions);
         validate = exceptions.isEmpty();
      } catch (SAXException e) {
         // not implemented
      } catch (IOException e) {
         // not implemented
      } catch (ParserConfigurationException e) {
         // not implemented
      }

      return validate;

   }
}
