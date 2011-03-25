package fr.urssaf.image.sae.saml.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public final class KeyStoreFactory {

   protected static KeyStore createKeystore() throws KeyStoreException,
         NoSuchAlgorithmException, CertificateException, IOException {

      return createKeystore("src/test/resources/Portail_Image.p12",
            "hiUnk6O3QnRN");

   }

   protected static KeyStore createKeystore(String file, String password)
         throws KeyStoreException, NoSuchAlgorithmException,
         CertificateException, IOException {

      KeyStore keystore = KeyStore.getInstance("PKCS12");

      FileInputStream in = new FileInputStream(file);
      try {
         keystore.load(in, password.toCharArray());

      } finally {
         in.close();
      }

      return keystore;

   }
}
