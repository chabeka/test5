package fr.urssaf.image.sae.saml.exception;

import org.junit.Test;

public class SamlFormatExceptionTest {

   @Test
   public void samlFormatException() {

      new SamlFormatException(new IllegalArgumentException("test"));
   }
}
