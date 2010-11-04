package fr.urssaf.image.commons.util.net;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * Classe de configuration de https
 * 
 */
public class HttpsConfiguration {

   /**
    * Méthode d'initialisation HttpsURLConnection.setDefaultSSLSocketFactory
    * avec le protocole SSL
    * 
    * @throws KeyManagementException
    *            exeption sur
    *            {@link javax.net.ssl.SSLContext#init(javax.net.ssl.KeyManager[], TrustManager[], java.security.SecureRandom)}
    * @throws NoSuchAlgorithmException
    *            exeption sur
    *            {@link javax.net.ssl.SSLContext#getInstance(String)}
    */
   public final void initDefaultSSLSocketFactory() throws KeyManagementException,
         NoSuchAlgorithmException {

      SSLContext ctx = SSLContext.getInstance("SSL");
      ctx.init(null, new TrustManager[] { new X509TrustManager() {

         @Override
         public void checkClientTrusted(X509Certificate[] arg0, String arg1)
               throws CertificateException {
            // aucune implémentation
         }

         @Override
         public void checkServerTrusted(X509Certificate[] arg0, String arg1)
               throws CertificateException {
            // aucune implémentation
         }

         @Override
         public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
         }
      } }, null);

      HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

   }

   /**
    * Méthode d'initialisation HttpsURLConnection.setDefaultHostnameVerifier
    * pour que aucune vérification n'ait lieu
    */
   public final void initDefaultHostnameVerifier() {

      HostnameVerifier hostCheck = new HostnameVerifier() {
         @Override
         public boolean verify(String urlHostName, SSLSession session) {
            return true;
         }
      };
      HttpsURLConnection.setDefaultHostnameVerifier(hostCheck);

   }
}
