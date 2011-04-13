package fr.urssaf.image.sae.webservice.client.demo.component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import fr.urssaf.image.sae.webservice.client.demo.util.ResourceUtils;

/**
 * Configuration d'un keyStore par défaut à partir d'un .p12<br>
 * <br>
 * <ul>
 * <li><b>p12</b>: Portail_Image.p12</li>
 * <li><b>password</b>: hiUnk6O3QnRN</li>
 * </ul>
 * 
 * 
 */
public final class DefaultKeystore {

   private static final String P12 = "Portail_Image.p12";

   private final KeyStore keystore;

   private final String alias;

   private final String password;

   private DefaultKeystore() {

      password = "hiUnk6O3QnRN";
      try {
         keystore = KeyStore.getInstance("PKCS12");
         InputStream inputStream = ResourceUtils.loadResource(this, P12);
         try {
            keystore.load(inputStream, password.toCharArray());

         } finally {
            inputStream.close();
         }

         alias = keystore.aliases().nextElement();

      } catch (KeyStoreException e) {
         throw new IllegalStateException(e);
      } catch (FileNotFoundException e) {
         throw new IllegalStateException(e);
      } catch (NoSuchAlgorithmException e) {
         throw new IllegalStateException(e);
      } catch (CertificateException e) {
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   private static final DefaultKeystore INSTANCE = new DefaultKeystore();

   /**
    * 
    * @return instance du keystore
    */
   public static DefaultKeystore getInstance() {

      return INSTANCE;
   }

   /**
    * 
    * @return alias de la clé publique
    */
   public String getAlias() {
      return this.alias;
   }

   /**
    * 
    * @return keystore par défaut
    */
   public KeyStore getKeystore() {
      return this.keystore;
   }

   /**
    * 
    * @return mot de passe de la clé privée
    */
   public String getPassword() {
      return this.password;
   }
}
