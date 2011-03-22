package fr.urssaf.image.sae.saml.data;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.opensaml.saml2.core.Assertion;

import fr.urssaf.image.sae.saml.component.SAMLReader;
import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.params.SamlCommonsParams;
import fr.urssaf.image.sae.saml.util.ConverterUtils;

/**
 * Factory pour l'instanciation des classes du package
 * {@link fr.urssaf.image.sae.saml.data}
 * 
 * 
 */
public final class ObjectFactory {

   private ObjectFactory() {

   }

   /**
    * Méthode d'instanciation de {@link SamlAssertionData}
    * 
    * @param assertion
    *           jeton SAML
    * @return nouvelle instance de {@link SamlAssertionData}
    */
   public static SamlAssertionData createSamlAssertionData(Assertion assertion) {

      SamlAssertionData data = new SamlAssertionData();

      // initialisation de SamlAssertionParams
      SamlAssertionParams assertionParams = new SamlAssertionParams();
      data.setAssertionParams(assertionParams);

      // METHOD AUTHN 2
      URI methodAuthn2 = ConverterUtils.uri(SAMLReader
            .loadAuthnContextClassRef(assertion));
      assertionParams.setMethodAuthn2(methodAuthn2);

      // RECIPIENT
      URI recipient = ConverterUtils.uri(SAMLReader.loadRecipient(assertion));
      assertionParams.setRecipient(recipient);

      // SUBJECT FORMAT 2
      URI subjectFormat2 = ConverterUtils.uri(SAMLReader
            .loadSubjectFormat(assertion));
      assertionParams.setSubjectFormat2(subjectFormat2);

      // SUBJECT ID 2
      String subjectId2 = SAMLReader.loadSubjectId(assertion);
      assertionParams.setSubjectId2(subjectId2);

      // initialisation de SamlCommonsParams
      SamlCommonsParams commonsParams = new SamlCommonsParams();
      assertionParams.setCommonsParams(commonsParams);

      // ISSUE INSTANT
      Date issueInstant = ConverterUtils.date(SAMLReader
            .loadIssueInstant(assertion));
      commonsParams.setIssueInstant(issueInstant);

      // ID
      UUID uuid = ConverterUtils.uuid(SAMLReader.loadID(assertion));
      commonsParams.setId(uuid);

      // ISSUER
      commonsParams.setIssuer(SAMLReader.loadIssuer(assertion));

      // AUDIENCE
      URI audience = ConverterUtils.uri(SAMLReader.loadAudience(assertion));
      commonsParams.setAudience(audience);

      // AUTHINSTANT
      Date authnInstant = ConverterUtils.date(SAMLReader
            .loadAuthInstant(assertion));
      commonsParams.setAuthnInstant(authnInstant);

      // NOT BEFORE
      Date notOnBefore = ConverterUtils.date(SAMLReader
            .loadNotBefore(assertion));
      commonsParams.setNotOnBefore(notOnBefore);

      // NOT ON OR AFTER
      Date notOnOrAfter = ConverterUtils.date(SAMLReader
            .loadNotOnOrAfter(assertion));
      commonsParams.setNotOnOrAfter(notOnOrAfter);

      // PAGM
      List<String> pagm = SAMLReader.loadPagm(assertion);
      commonsParams.setPagm(pagm);

      // initialisation de la clé publique
      X509Certificate publicKey = SAMLReader.loadPublicKey(assertion);
      data.setClePublique(publicKey);

      return data;
   }
}
