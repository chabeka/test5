package fr.urssaf.image.sae.webservices.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;

import org.springframework.core.io.Resource;

/**
 * Classe utilitaires pour java.security
 * 
 * 
 */
public final class SecurityUtils {

   private SecurityUtils() {

   }

   /**
    * 
    * @param keystore
    *           chemin du p12
    * @param password
    *           mot de passe de la clée privée
    * @return instance de keystore de type 'PKCS12'
    * @throws GeneralSecurityException
    *            exception levée par le fichier ou le mot de passe
    * @throws IOException
    *            exception levée par le fichier ou le mot de passe
    */
   public static KeyStore createKeyStore(Resource keystore, String password)
         throws GeneralSecurityException, IOException {

      InputStream input = keystore.getInputStream();

      try {
         return createKeyStore(input, password, "PKCS12");
      } finally {
         input.close();
      }

   }

   /**
    * 
    * @param input
    *           chemin du certificat
    * @param password
    *           mot de passe de la clé privée
    * @param type
    *           type de keystore
    * @return instance de keystore
    * @throws IOException
    *            exception levée par le fichier ou le mot de passe
    * @throws GeneralSecurityException
    *            exception sur le crl
    */
   public static KeyStore createKeyStore(InputStream input, String password,
         String type) throws IOException, GeneralSecurityException {

      KeyStore keystore = KeyStore.getInstance(type);

      keystore.load(input, password.toCharArray());

      return keystore;

   }

   /**
    * 
    * @param crl
    *           fichier du crl
    * @return instance de CRL correspondant au fichier
    * @throws GeneralSecurityException
    *            exception sur le crl
    * @throws IOException
    *            exception sur le crl
    */
   public static X509CRL createCRL(Resource crl)
         throws GeneralSecurityException, IOException {

      InputStream input = crl.getInputStream();

      try {
         return createCRL(input);
      } finally {
         input.close();
      }

   }

   /**
    * 
    * @param crl
    *           fichier du cr
    * @return instance de CRL correspondant au fichier
    * @throws GeneralSecurityException
    *            exception sur le crl
    */
   public static X509CRL createCRL(InputStream crl)
         throws GeneralSecurityException {

      CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
      return (X509CRL) certFactory.generateCRL(crl);

   }

}
