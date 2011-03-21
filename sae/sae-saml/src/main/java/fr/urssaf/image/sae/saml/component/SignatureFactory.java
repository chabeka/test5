package fr.urssaf.image.sae.saml.component;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;

/**
 * Fabrication des signatures pour les jetons SAML
 * 
 * 
 */
public class SignatureFactory {

   private static final Logger LOG = Logger.getLogger(SignatureFactory.class);

   private final KeyStore keystore;

   private final String alias;

   private final String password;

   /**
    * initialisation du keystore
    * 
    * @param keystore
    *           keystore
    */
   public SignatureFactory(KeyStore keystore) {
      if (keystore == null) {
         throw new IllegalArgumentException("'keystore' is required");
      }
      this.keystore = keystore;
      // TODO alias et le mot de passe doivent être dynamique
      this.alias = "1";
      this.password = "hiUnk6O3QnRN";
   }

   /**
    * la signature électronique respecte les contraintes suivantes :
    * <ul>
    * <li>l'algorithme de hachage est SHA1</li>
    * <li>la canonicalisation XML est "Canonicalisation XML exclusive"</li>
    * <li>la transformation est "Signature enveloppée"</li>
    * <li>la signature est "RSAwithSHA1"</li>
    * </ul>
    * 
    * @return signature électronique
    */
   public final Signature createSignature() {

      Credential credential = getSigningCredential();

      Signature signature = SAMLFactory.create(Signature.DEFAULT_ELEMENT_NAME);

      signature.setSigningCredential(credential);
      // la signature est "RSAwithSHA1"
      signature
            .setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);

      // la canonicalisation XML est "Canonicalisation XML exclusive"
      signature
            .setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

      signature.setKeyInfo(getKeyInfo());

      return signature;
   }

   private Credential getSigningCredential() {

      Credential credential = createBasicX509Credential();

      return credential;

   }

   private BasicX509Credential createBasicX509Credential() {

      BasicX509Credential credential = null;

      try {

         PrivateKey privatekey = (PrivateKey) keystore.getKey(alias, password
               .toCharArray());
         // X509Certificate cert = (X509Certificate) keystore
         // .getCertificate(alias);
         PublicKey publickey = keystore.getCertificate(alias).getPublicKey();

         LOG.debug("alias:" + alias);
         LOG.debug("private key:" + privatekey);
         LOG.debug("public key:" + publickey);

         credential = new BasicX509Credential();
         credential.setPublicKey(publickey);
         credential.setPrivateKey(privatekey);

         // credential.setEntityId(alias);
         // credential.setUsageType(UsageType.SIGNING);

      } catch (KeyStoreException e) {
         LOG.error(e.getMessage(), e);
      } catch (UnrecoverableKeyException e) {
         LOG.error(e.getMessage(), e);
      } catch (NoSuchAlgorithmException e) {
         LOG.error(e.getMessage(), e);
      }

      return credential;

   }

   private KeyInfo getKeyInfo() {

      KeyInfo keyInfo = SAMLFactory.create(KeyInfo.DEFAULT_ELEMENT_NAME);

      try {

         X509Data x509Data = SAMLFactory.create(X509Data.DEFAULT_ELEMENT_NAME);

         for (Certificate cert : keystore.getCertificateChain(alias)) {

            X509Certificate x509Cert = SAMLFactory
                  .create(X509Certificate.DEFAULT_ELEMENT_NAME);

            x509Cert.setValue(StringUtils.newStringUtf8(Base64.encodeBase64(
                  cert.getPublicKey().getEncoded(), false)));
            x509Data.getX509Certificates().add(x509Cert);

         }

         keyInfo.getX509Datas().add(x509Data);

      } catch (KeyStoreException e) {
         LOG.error(e.getMessage(), e);
      }

      return keyInfo;
   }
}
