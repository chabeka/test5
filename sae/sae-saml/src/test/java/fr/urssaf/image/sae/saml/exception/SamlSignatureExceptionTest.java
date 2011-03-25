package fr.urssaf.image.sae.saml.exception;

import org.junit.Test;

public class SamlSignatureExceptionTest {

   @Test
   public void samlSignatureException() {

      new SamlSignatureException(new IllegalArgumentException("test"));
   }
}
