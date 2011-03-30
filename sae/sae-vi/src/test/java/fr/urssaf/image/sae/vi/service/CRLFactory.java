package fr.urssaf.image.sae.vi.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;

/**
 * outil de création de {@link X509CRL}
 * 
 * 
 */
public final class CRLFactory {

   private CRLFactory() {

   }

   /**
    * 
    * @param crl
    *           fichier du crl
    * @return crl correspondant au fichier
    * @throws CRLException
    *            exception sur les crl
    * @throws IOException
    *            exception sur le fichier
    */
   public static X509CRL createCRL(String crl) throws CRLException, IOException {

      InputStream inStream = new FileInputStream(crl);
      try {
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         return (X509CRL) cf.generateCRL(inStream);
      } catch (CertificateException e) {
         throw new IllegalStateException(e);
      } finally {
         inStream.close();
      }
   }
}
