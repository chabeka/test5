package fr.urssaf.image.sae.saml.service;

import java.util.Arrays;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.validation.ValidationException;

import fr.urssaf.image.sae.saml.component.SAMLConfiguration;
import fr.urssaf.image.sae.saml.component.SAMLFactory;

public class SamlAssertionServiceTest {

   private static SamlAssertionService service;

   @BeforeClass
   public static void beforeClass() {

      SAMLConfiguration.init();

      service = new SamlAssertionService();
   }

   @Test
   public void validate_success() throws ValidationException {

      Assertion samlObject = SAMLFactory.createAssertion("identifiant",
            new DateTime());

      // ISSUER
      Issuer issuer = SAMLFactory.createIssuer("value");
      samlObject.setIssuer(issuer);

      // SUBJECT
      Subject subject = SAMLFactory.createSubject("ubjectId2",
            "subjectFormat2", new DateTime(), "recipient");
      samlObject.setSubject(subject);

      // CONDITION

      Conditions conditions = SAMLFactory.createConditions(new DateTime(),
            new DateTime(), "audience");
      samlObject.setConditions(conditions);

      // AUTHNSTATEMENT

      AuthnStatement authnStatement = SAMLFactory.createAuthnStatement(
            new DateTime(), "identifiant", "methodAuthn");
      samlObject.getAuthnStatements().add(authnStatement);

      // ATTRIBUTESTATEMENT

      AttributeStatement attrStatement = SAMLFactory
            .createAttributeStatementPAGM(Arrays.asList("PAGM1", "PAGM2"));
      samlObject.getAttributeStatements().add(attrStatement);

      service.validate(samlObject);
   }

   @Test(expected = ValidationException.class)
   public void validate_failure_required_issue() throws ValidationException {

      Assertion samlObject = SAMLFactory.createAssertion("identifiant",
            new DateTime());

      service.validate(samlObject);

   }

   @Test
   public void validate_failure_required_subject() throws ValidationException {

      Assertion samlObject = SAMLFactory.createAssertion("identifiant",
            new DateTime());

      // ISSUER
      Issuer issuer = SAMLFactory.createIssuer("value");
      samlObject.setIssuer(issuer);

      // SUBJECT
      Subject subject = SAMLFactory.createSubject("ubjectId2",
            "subjectFormat2", new DateTime(), "recipient");
      samlObject.setSubject(subject);

      service.validate(samlObject);

   }

   @Test
   public void marshaller_success() {

      Assertion samlObject = SAMLFactory.createAssertion("identifiant",
            new DateTime());

      service.marshaller(samlObject);
      
      
   }
}
