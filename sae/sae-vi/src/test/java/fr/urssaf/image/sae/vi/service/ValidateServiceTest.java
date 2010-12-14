package fr.urssaf.image.sae.vi.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("PMD")
public class ValidateServiceTest {

   private ValidateService service;

   @Before
   public void before() throws URISyntaxException {

      service = new ValidateService();
   }

   @Test
   public void validate_success() throws IOException, JAXBException {

      File file = new File("src/test/resources/ctd_2_rights.xml");
      String xml = FileUtils.readFileToString(file, "UTF-8");

      assertTrue("le fichier 'ctd_2_rights.xml' est invalide", service
            .isValidate(xml));
   }

   @Test
   public void validate_failure() throws IOException, JAXBException {

      File file = new File("src/test/resources/ctd_0_right.xml");
      String xml = FileUtils.readFileToString(file, "UTF-8");

      assertFalse("le fichier 'ctd_0_right.xml' est valide", service
            .isValidate(xml));
   }
}
