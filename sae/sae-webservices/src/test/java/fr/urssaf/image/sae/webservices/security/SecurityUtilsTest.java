package fr.urssaf.image.sae.webservices.security;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Test;
import org.springframework.core.io.InputStreamResource;

public class SecurityUtilsTest {

   @Test
   public void createKeyStore_success() throws GeneralSecurityException,
         IOException {

      InputStreamResource input = new InputStreamResource(new FileInputStream(
            "src/main/resources/security/Portail_Image.p12"));

      assertNotNull(SecurityUtils.createKeyStore(input, "hiUnk6O3QnRN"));
   }

   @Test
   public void createCRL_success() throws GeneralSecurityException, IOException {

      InputStreamResource input = new InputStreamResource(new FileInputStream(
            "src/main/resources/security/CRL/Pseudo_ACOSS.crl"));

      assertNotNull(SecurityUtils.createCRL(input));
   }

}
