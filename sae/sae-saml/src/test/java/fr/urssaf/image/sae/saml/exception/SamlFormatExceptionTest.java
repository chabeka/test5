package fr.urssaf.image.sae.saml.exception;

import org.junit.Test;

public class SamlFormatExceptionTest {

   @Test
   @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
   public void samlFormatException() {

      new SamlFormatException(new IllegalArgumentException("test"));
   }
}
