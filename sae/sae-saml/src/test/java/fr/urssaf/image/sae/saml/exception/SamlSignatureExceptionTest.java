package fr.urssaf.image.sae.saml.exception;

import org.junit.Test;

public class SamlSignatureExceptionTest {

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void samlSignatureException() {

      new SamlSignatureException(new IllegalArgumentException("test"));
   }
}
