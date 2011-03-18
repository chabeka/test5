package fr.urssaf.image.sae.saml.component;

import org.junit.Test;

import fr.urssaf.image.sae.saml.component.SignatureFactory;

public class SignatureFactoryTest {

   @Test(expected = IllegalArgumentException.class)
   public void signtaureFactoryFailure() {

      new SignatureFactory(null);
   }

}
