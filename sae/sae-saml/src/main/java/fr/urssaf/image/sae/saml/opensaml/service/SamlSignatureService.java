package fr.urssaf.image.sae.saml.opensaml.service;

import java.security.cert.Certificate;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.validation.ValidationException;

/**
 * Classe de service pour la signature des jetons SAM 2.0L<br>
 * <br>
 * Le recours à cette classe nécessite une instanciation au préalable de
 * {@link SamlConfiguration}
 * 
 * 
 */
public class SamlSignatureService {

   /**
    * Signature du jeton SAML<br>
    * <br>
    * La méthode instancie un objet {@link Signature} attaché ensuite à l'objet
    * {@link Assertion}<br>
    * <br>
    * algo de signature : RSAwithSHA1<br>
    * algo de hachage : SHA1<br>
    * algo de canonicalisation XML : Canonicalisation XML exclusive<br>
    * <br>
    * tutorial pour l'implémentation d'une signature dans un jeton SAML <a href="https://spaces.internet2.edu/display/OpenSAML/OSTwoUserManJavaDSIG#OSTwoUserManJavaDSIG-SigningExamples"
    * />exemple de code</a><br>
    * <br>
    * 
    * <pre>
    * exemple d'utilisation de cette méthode:
    * 
    * BasicX509Credential x509Credential = new BasicX509Credential();
    * x509Credential.setEntityCertificate(x509Certificate);
    * x509Credential.setPrivateKey(privatekey);
    * x509Credential.setEntityCertificateChain(certs);
    * 
    * this.sign(assertion, x509Credential);
    * </pre>
    * 
    * @param assertion
    *           jeton SAML à signer
    * @param credential
    *           contient les différents certificats nécessaire à la signature
    */
   public final void sign(Assertion assertion, X509Credential credential) {

      Signature signature = SamlXML.create(Signature.DEFAULT_ELEMENT_NAME);

      signature.setSigningCredential(credential);
      // la signature est "RSAwithSHA1"
      signature
            .setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);

      // la canonicalisation XML est "Canonicalisation XML exclusive"
      signature
            .setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

      signature.setKeyInfo(getKeyInfo(credential));

      assertion.setSignature(signature);

      try {
         Configuration.getMarshallerFactory().getMarshaller(assertion)
               .marshall(assertion);
      } catch (MarshallingException e) {
         throw new IllegalStateException(e);
      }

      try {
         Signer.signObject(signature);
      } catch (SignatureException e) {
         throw new IllegalStateException(e);
      }

   }

   private KeyInfo getKeyInfo(X509Credential credential) {

      KeyInfo keyInfo = null;

      if (CollectionUtils.isNotEmpty(credential.getEntityCertificateChain())) {

         keyInfo = SamlXML.create(KeyInfo.DEFAULT_ELEMENT_NAME);

         X509Data x509Data = SamlXML.create(X509Data.DEFAULT_ELEMENT_NAME);

         for (Certificate cert : credential.getEntityCertificateChain()) {

            X509Certificate x509Cert = SamlXML
                  .create(X509Certificate.DEFAULT_ELEMENT_NAME);

            x509Cert.setValue(StringUtils.newStringUtf8(Base64.encodeBase64(
                  cert.getPublicKey().getEncoded(), false)));
            x509Data.getX509Certificates().add(x509Cert);

         }

         keyInfo.getX509Datas().add(x509Data);

      }

      return keyInfo;
   }

   /**
    * méthode de validation de la signature d'un jeton SAML<br>
    * <br>
    * tutorial pour la validation d'une signature <a href="https://spaces.internet2.edu/display/OpenSAML/OSTwoUserManJavaDSIG#OSTwoUserManJavaDSIG-VerifyingaSignaturewithaCredential"
    * />tutorial</a>
    * 
    * <pre>
    * exemple d'utilisation : 
    * 
    * //parsing d'un jeton SAML à partir d'une chaine de caractères
    * Element element = XMLUtils.parse(assertionSaml);
    * 
    * //récupération d'un objet Assertion
    * Assertion assertion = (Assertion) SamlXML.unmarshaller(element);
    * 
    * //récupération de l'objet x509Certificate pour de validation 
    * //à partir d'un keystore par exemple
    * X509Certificate x509Certificate = SecurityUtil.loadX509Certificate(
    *                keystore, alias);
    * //vérification de la signature
    * this.validate(assertion, x509Certificate);
    * </pre>
    * 
    * @param signature
    *           signature du jeton SAML
    * @param credential
    *           contient les différents certificats nécessaire à la validation
    *           de cette signature
    * @throws ValidationException
    *            la signature n'est pas valide
    */
   public final void validate(Signature signature, X509Credential credential)
         throws ValidationException {

      SignatureValidator validator = new SignatureValidator(credential);
      SAMLSignatureProfileValidator profilvalidator = new SAMLSignatureProfileValidator();

      profilvalidator.validate(signature);
      validator.validate(signature);

   }

}
