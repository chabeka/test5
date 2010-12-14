package fr.urssaf.image.sae.vi.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import fr.urssaf.image.commons.xml.XSDValidator;
import fr.urssaf.image.commons.xml.XSDValidator.SAXParseExceptionType;

/**
 * Classe de validation du VI<br>
 * <br>
 * Exemple d'instanciation de la classe<br>
 * <pre>
 * String xsdPath = this.getClass().getResource(&quot;/xsd/sae-anais.xsd&quot;).toURI()
 *       .getPath();
 * service = new ValidateService(xsdPath);
 * </pre>
 * 
 */
public class ValidateService {

   private static final Logger LOG = Logger.getLogger(ValidateService.class);

   private final String xsdPath;

   /**
    * initialisation du xsd pour la validation des VI<br>
    * <br>
    * Ce chemin doit être absolu<br>
    * 
    * @param xsdPath
    *           chemin du fichier xsd
    */
   //TODO Instancier le fichier xsd directement du jar
   public ValidateService(String xsdPath) {
      this.xsdPath = xsdPath;
      LOG.debug("path du xsd pour le VI:" + xsdPath);

   }

   /**
    * Retourne si le VI est valide en s'appuyant sur le schéma XSD
    * <code>sae-anais.xsd</code><br>
    * <br>
    * La validation se fait en appelant la méthode
    * {@link XSDValidator#validXMLStringWithSAX(String, String)}<br>
    * <br>
    * Si le jeton comporte la moindre {@link SAXParseExceptionType} ou lève une
    * exception alors le jeton est considéré comme invalide
    * 
    * @param xml
    *           VI du jeton d'authentification
    * @return true si le jeton n'est pas conforme faux sinon
    */
   @SuppressWarnings("PMD.EmptyCatchBlock")
   public final boolean isValidate(String xml) {

      boolean validate = false;

      try {
         List<SAXParseExceptionType> exceptions = XSDValidator
               .validXMLStringWithSAX(xml, xsdPath);
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
