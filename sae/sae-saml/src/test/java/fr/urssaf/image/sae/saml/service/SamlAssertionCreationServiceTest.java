package fr.urssaf.image.sae.saml.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.params.SamlCommonsParams;

public class SamlAssertionCreationServiceTest {

   private static SamlAssertionCreationService service;

   private static final Logger LOG = Logger
         .getLogger(SamlAssertionCreationServiceTest.class);

   @BeforeClass
   public static void beforeClass() {
      service = new SamlAssertionCreationService();
   }

   private String assertion;

   @Before
   public void before() throws URISyntaxException {

      SamlAssertionParams params = new SamlAssertionParams();

      URI subjectFormat2 = new URI(
            "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
      params.setSubjectFormat2(subjectFormat2);
      params.setSubjectId2("subjectId2");
      URI methodAuthn2 = new URI(
            "urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
      params.setMethodAuthn2(methodAuthn2);
      URI recipient = new URI("urn:interops:sp:73282932000074:test");
      params.setRecipient(recipient);

      SamlCommonsParams commonsParams = new SamlCommonsParams();
      URI audience = new URI("http://rniam.cnav.fr");
      commonsParams.setAudience(audience);
      Date authnInstant = new Date();
      commonsParams.setAuthnInstant(authnInstant);
      UUID id = UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6");
      commonsParams.setId(id);
      Date issueInstant = new Date();
      commonsParams.setIssueInstant(issueInstant);
      commonsParams.setIssuer("urn:interops:73282932000074:idp:test:version");
      Date notOnBefore = new Date();
      commonsParams.setNotOnBefore(notOnBefore);
      Date notOnOrAfter = new Date();
      commonsParams.setNotOnOrAfter(notOnOrAfter);
      commonsParams.setPagm(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
      params.setCommonsParams(commonsParams);

      assertion = service.genererAssertion(params, null);
   }

   @Test
   public void genererAssertion() {

      LOG.debug("\n" + assertion);
   }

   @Test
   public void validate() throws ValidationException, XMLParserException,
         UnmarshallingException {

      SAMLObject assertionObject = getXMLObjectFromRessource(assertion);

      String validatorId = "saml2-core-schema-validator";
      ValidatorSuite validatorSuite = Configuration
            .getValidatorSuite(validatorId);

      validatorSuite.validate(assertionObject);

   }

   private SAMLObject getXMLObjectFromRessource(String saml)
         throws XMLParserException, UnmarshallingException {

      // Get parser pool manager
      BasicParserPool ppMgr = new BasicParserPool();
      ppMgr.setNamespaceAware(true);

      // Parse file
      InputStream in = new ByteArrayInputStream(saml.getBytes());
      Document documentXml = ppMgr.parse(in);
      Element rootElement = documentXml.getDocumentElement();

      // Get apropriate unmarshaller
      UnmarshallerFactory unmarshallerFactory = Configuration
            .getUnmarshallerFactory();
      Unmarshaller unmarshaller = unmarshallerFactory
            .getUnmarshaller(rootElement);

      // Unmarshall using the document root element
      SAMLObject obj = (SAMLObject) unmarshaller.unmarshall(rootElement);

      return obj;

   }
}
