package fr.urssaf.image.sae.saml.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.opensaml.saml2.core.Assertion;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.saml.component.SAMLConfiguration;
import fr.urssaf.image.sae.saml.data.ObjectFactory;
import fr.urssaf.image.sae.saml.data.SamlAssertionData;

/**
 * Classe d'extraction des jetons SAML 2.0<br>
 * <br>
 * 
 * @see ObjectFactory
 */
public class SamlAssertionExtractionService {

   private final SamlAssertionService assertionService;

   private final DocumentBuilder builder;

   /**
    * Configuration de la libraire OpenSAML {@link SAMLConfiguration#init()}<br>
    * initialisation de {@link SamlAssertionService} pour la transformation d'un
    * flux en objet {@link Assertion}<br>
    */
   public SamlAssertionExtractionService() {

      SAMLConfiguration.init();

      assertionService = new SamlAssertionService();

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      try {
         builder = factory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException(e);
      }
   }

   /**
    * Extraction des données du vecteur d'identification depuis une assertion
    * SAML 2.0
    * 
    * @param assertionSaml
    *           L'assertion SAML 2.0 à vérifier
    * @return Les données du vecteur d'identification présentes dans l'assertion
    *         SAML
    */
   public final SamlAssertionData extraitDonnees(String assertionSaml) {

      try {

         InputStream input = new ByteArrayInputStream(assertionSaml.getBytes());
         Element element = builder.parse(input).getDocumentElement();
         Assertion assertion = (Assertion) assertionService
               .unmarshaller(element);

         return ObjectFactory.createSamlAssertionData(assertion);

      } catch (SAXException e) {
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }

   }
}
