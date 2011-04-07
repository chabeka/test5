package fr.urssaf.image.sae.saml.testutils;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.xml.signature.Signature;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.saml.opensaml.SamlXML;
import fr.urssaf.image.sae.saml.util.XMLUtils;


/**
 * Méthodes utilitaires pour les tests unitaires
 */
public final class TuUtils {

   
   private TuUtils() {
      
   }
   
   /**
    * Chargement d'un objet Signature depuis un fichier de ressource
    * 
    * @param fichierRessource
    *    Le chemin du fichier de ressource<br>
    *    exemple : "src/test/resources/signature/signature01.xml"
    *    
    * @return l'objet Signature
    */
   @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
   public static Signature loadSignature(String fichierRessource) {
      try {
         File file = new File(fichierRessource);
         String signatureStr = FileUtils.readFileToString(file, CharEncoding.UTF_8);
         Element element = XMLUtils.parse(signatureStr);
         return (Signature) SamlXML.unmarshaller(element);
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (SAXException e) {
         throw new RuntimeException(e);
      }
   }
   
   
   /**
    * Chargement d'un objet Assertion depuis un fichier de ressource
    * 
    * @param fichierRessource
    *    fichierRessource Le chemin du fichier de ressource<br>
    *    exemple : "src/test/resources/assertion/assertion01.xml"
    *    
    * @return l'objet Assertion
    */
   @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
   public static Assertion getAssertion(String fichierRessource) {
      try {
         File file = new File(fichierRessource);
         String assertionSaml = FileUtils.readFileToString(file, CharEncoding.UTF_8);
         Element element = XMLUtils.parse(assertionSaml);
         return (Assertion) SamlXML.unmarshaller(element);
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (SAXException e) {
         throw new RuntimeException(e);
      }
   }
   
   
   /**
    * Charge un fichier de ressources vers un objet Element
    * 
    * @param fichierRessource le chemin du fichier de ressource (ex. : "src/test/resources/toto.xml")
    * @return l'objet Element
    * @throws SAXException en cas d'erreur de parsing du fichier de ressource
    * @throws IOException si le fichier de ressource indiqué n'est pas trouvé
    */
   public static Element loadResourceFileToElement(
         String fichierRessource) 
      throws 
         SAXException, 
         IOException {

      File file = new File(fichierRessource);
      return XMLUtils.parse(FileUtils.readFileToString(file, CharEncoding.UTF_8));

   }
   
   
   /**
    * Fait un log.debug sur une assertion SAML typée en Element
    * 
    * @param logger le logger à utiliser
    * @param assertion l'assertion SAML
    */
   public static void debugAssertion(
         Logger logger,
         Element assertion) {
      
      Transformer transformer = XMLUtils.initTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount", "4");

      logger.debug("\n" + XMLUtils.print(assertion, CharEncoding.UTF_8, transformer));
      
   }
   
}
