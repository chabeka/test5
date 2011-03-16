package fr.urssaf.image.sae.saml.service;

import java.security.KeyStore;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.saml.component.SAMLFactory;
import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.util.PrintUtil;

/**
 * Classe de création de jeton SAML 2.0<br>
 * <br>
 * la classe s'appuie sur le framework <a
 * href="https://spaces.internet2.edu/display/OpenSAML/Home" />OpenSAML</a>
 * 
 * 
 */
public class SamlAssertionCreationService {

   private final MarshallerFactory marshallerFactory;

   /**
    * Configuration de la libraire OpenSAML
    */
   public SamlAssertionCreationService() {

      try {
         DefaultBootstrap.bootstrap();
      } catch (ConfigurationException e) {
         throw new IllegalStateException(e);
      }

      marshallerFactory = Configuration.getMarshallerFactory();

   }

   /**
    * Génération d'une assertion SAML 2.0 signée électroniquement, pour être
    * utilisée dans le cadre de l'authentification aux services web du SAE
    * 
    * @param assertionParams
    *           Les paramètres de génération de l'assertion SAML
    * @param keyStore
    *           La clé privée et sa chaîne de certification pour la signature de
    *           l'assertion SAML
    * @return L'assertion SAML 2.0 signée électroniquement
    */
   public final String genererAssertion(SamlAssertionParams assertionParams,
         KeyStore keyStore) {

      DateTime systemDate = new DateTime();

      // ASSERTION
      DateTime issueInstant = assertionParams.getCommonsParams()
            .getIssueInstant() == null ? systemDate : new DateTime(
            assertionParams.getCommonsParams().getIssueInstant());

      String identifiant = assertionParams.getCommonsParams().getId()
            .toString();

      Assertion assertion = SAMLFactory.createAssertion(identifiant,
            issueInstant);

      // ISSUER
      Issuer issuer = SAMLFactory.createIssuer(assertionParams
            .getCommonsParams().getIssuer());
      assertion.setIssuer(issuer);

      // SUBJECT
      DateTime notOnOrAfter = new DateTime(assertionParams.getCommonsParams()
            .getNotOnOrAfter());

      Subject subject = SAMLFactory.createSubject(assertionParams
            .getSubjectId2(), assertionParams.getSubjectFormat2()
            .toASCIIString(), notOnOrAfter, assertionParams.getRecipient()
            .toASCIIString());
      assertion.setSubject(subject);

      // CONDITION

      DateTime notBefore = new DateTime(assertionParams.getCommonsParams()
            .getNotOnBefore());

      Conditions conditions = SAMLFactory.createConditions(notBefore,
            notOnOrAfter, assertionParams.getCommonsParams().getAudience()
                  .toASCIIString());
      assertion.setConditions(conditions);

      // AUTHNSTATEMENT

      DateTime authnInstant = assertionParams.getCommonsParams()
            .getIssueInstant() == null ? systemDate : new DateTime(
            assertionParams.getCommonsParams().getAuthnInstant());

      AuthnStatement authnStatement = SAMLFactory.createAuthnStatement(
            authnInstant, identifiant, assertionParams.getMethodAuthn2()
                  .toASCIIString());
      assertion.getAuthnStatements().add(authnStatement);

      // ATTRIBUTESTATEMENT

      AttributeStatement attrStatement = SAMLFactory
            .createAttributeStatementPAGM(assertionParams.getCommonsParams()
                  .getPagm());
      assertion.getAttributeStatements().add(attrStatement);

      Marshaller marshaller = marshallerFactory.getMarshaller(assertion);
      try {
         Element element = marshaller.marshall(assertion);
         return PrintUtil.print(element);

      } catch (MarshallingException e) {
         throw new IllegalStateException(e);
      }

   }

}
