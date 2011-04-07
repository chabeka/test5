package fr.urssaf.image.sae.saml.opensaml.signature;

import java.security.KeyStore;
import java.security.cert.X509CRL;
import java.util.List;

import org.opensaml.saml2.core.Assertion;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.validation.ValidationException;

import fr.urssaf.image.sae.saml.exception.signature.keyinfo.SamlKeyInfoException;
import fr.urssaf.image.sae.saml.exception.signature.keyinfo.SamlX509ConvertException;
import fr.urssaf.image.sae.saml.exception.signature.validate.SamlAutoSignedCertificateException;
import fr.urssaf.image.sae.saml.exception.signature.validate.SamlSignatureCryptoException;
import fr.urssaf.image.sae.saml.exception.signature.validate.SamlSignatureKeyInfoException;
import fr.urssaf.image.sae.saml.exception.signature.validate.SamlSignatureNonConformeAuProfilException;
import fr.urssaf.image.sae.saml.exception.signature.validate.SamlSignatureNotFoundException;
import fr.urssaf.image.sae.saml.exception.signature.validate.SamlSignatureValidateException;


/**
 * Classe de service pour la vérification de la signature XML d'une assertion SAML 2.0
 *
 */
public class SamlSignatureValidateService {
   
   
   /**
    * 
    * Vérification de la signature d'une assertion SAML
    * 
    * @param assertion
    *           assertion SAML contenant la signature à vérifier
    * @param keystore
    *           Les certificats des autorités de certification qui sont
    *           reconnues pour être autorisées à délivrer des certificats de
    *           signature de VI, ainsi que leur chaîne de certification
    * @param crl
    *           Les CRL
    * @throws SamlSignatureValidateException
    *            la signature est invalide
    */
   public final void verifierSignature(
         Assertion assertion,
         KeyStore keystore,
         List<X509CRL> crl)
      throws SamlSignatureValidateException {

      // Récupération de la signature de l'assertion
      Signature signature = assertion.getSignature();
      if (signature==null) {
         throw new SamlSignatureNotFoundException();
      }
      
      // Récupération de la clé publique dont la clé privée a servi pour
      //  la signature.
      // En tant que règle de gestion, cette clé publique est le premier
      //  certificat X509 trouvé dans la partie <KeyInfo> de la signature
      X509Certificate publicKeyOpenSaml;
      try {
         publicKeyOpenSaml = SamlSignatureUtils.getPublicKey(assertion.getSignature());
      } catch (SamlKeyInfoException e) {
         throw new SamlSignatureKeyInfoException(e);
      }
      
      // Vérifications sur le certificat lui-même
      java.security.cert.X509Certificate publicKeyNatif;
      try {
         publicKeyNatif = SamlSignatureUtils.convertX509(publicKeyOpenSaml);
      } catch (SamlX509ConvertException e) {
         throw new SamlSignatureValidateException(e);
      }
    
      // Vérifications sur le certificat lui-même
      verifierCertificatClePublique(publicKeyNatif);
      
      // Vérifications de la partie cryptographie de la signature
      verifierCryptographie(signature,publicKeyNatif);
      
      // Vérifications de la confiance dans le certificat de signature
      verifierConfiance();
      
   }
   
   
   
   private void verifierCertificatClePublique(
         java.security.cert.X509Certificate certificat) 
      throws 
         SamlSignatureValidateException {
      
      // Regarde s'il s'agit d'un certificat auto-signé.
      // Si oui, on lève une exception, car ils ne sont pas autorisés
      if (certificat.getSubjectDN().equals(certificat.getIssuerDN())) {
         throw new SamlAutoSignedCertificateException();
      }
      
      // TODO : Vérifier la période de validité
      
   }
   
   
   private void verifierCryptographie(
         Signature signature,
         java.security.cert.X509Certificate certSignature)
      throws
         SamlSignatureValidateException {
      
      BasicX509Credential x509Credential = new BasicX509Credential();
      x509Credential.setEntityCertificate(certSignature);
      
      SAMLSignatureProfileValidator profilvalidator = new SAMLSignatureProfileValidator();
      try {
         profilvalidator.validate(signature);
      } catch (ValidationException e) {
         throw new SamlSignatureNonConformeAuProfilException(e);
      }
      
      SignatureValidator validator = new SignatureValidator(x509Credential);
      try {
         validator.validate(signature);
      } catch (ValidationException e) {
         throw new SamlSignatureCryptoException(e);
      }
      
   }
   
   
   private void verifierConfiance() {
      // TODO : Implémenter la confiance 
   }
   
}
