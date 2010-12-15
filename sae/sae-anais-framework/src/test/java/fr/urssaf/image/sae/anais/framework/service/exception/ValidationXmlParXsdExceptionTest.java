package fr.urssaf.image.sae.anais.framework.service.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.sae.vi.exception.VIException;
import fr.urssaf.image.sae.vi.modele.DroitApplicatif;
import fr.urssaf.image.sae.vi.service.VIService;

public class ValidationXmlParXsdExceptionTest {

   private static final String FAIL_MESSAGE = "le test ne doit pas passer";

   private static final Logger LOG = Logger
         .getLogger(ValidationXmlParXsdExceptionTest.class);

   @Test
   public void validationXmlParXsdException() throws VIException,
         AucunDroitException {

      VIService service = new VIService();

      try {
         service.createVI(null, null, new ArrayList<DroitApplicatif>());
         fail(FAIL_MESSAGE);
      } catch (VIException e) {
         ValidationXmlParXsdException exception = new ValidationXmlParXsdException(
               e);

         assertEquals("sae-anais.xsd", exception.getNomDuSchemaXsd());
         assertEquals("VECTEUR IDENTIFICATION", exception.getNomDuDocumentXml());
         assertEquals(2, exception.getErreurs().length);
         
         LOG.debug(exception.getMessage());
      }

   }
}
