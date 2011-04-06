package fr.urssaf.image.sae.saml.service;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.List;

import org.opensaml.saml2.core.Assertion;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.saml.exception.SamlFormatException;
import fr.urssaf.image.sae.saml.exception.SamlSignatureException;
import fr.urssaf.image.sae.saml.opensaml.SamlAssertionService;
import fr.urssaf.image.sae.saml.opensaml.service.SamlXML;
import fr.urssaf.image.sae.saml.util.SecurityUtil;

/**
 * Vérification technique d'une assertion SAML 2.0 signée électroniquement<br>
 * <br>
 * Le recours à cette classe nécessite une instanciation au préalable de
 * {@link fr.urssaf.image.sae.saml.opensaml.service.SamlConfiguration}
 * 
 */
public class SamlAssertionVerificationService {

   private final SamlAssertionService assertionService;

   /**
    * instanciation de {@link SamlAssertionService} pour la validation de la
    * signature
    */
   public SamlAssertionVerificationService() {

      assertionService = new SamlAssertionService();

   }

   /**
    * Méthode de vérification du corps et de la signature d'un jeton SAML
    * <ol>
    * <li>vérification du corps :
    * {@link SamlAssertionService#validate(Assertion)}</li>
    * <li>vérification de la signature :
    * {@link SamlAssertionService#validate(Assertion, X509Certificate)}</li>
    * </ol>
    * 
    * @param assertionSaml
    *           L'assertion SAML à vérifier
    * @param keystore
    *           Les certificats des autorités de certification qui sont
    *           reconnues pour être autorisées à délivrer des certificats de
    *           signature de VI, ainsi que leur chaîne de certification
    * @param alias
    *           alias du certificat
    * @param crl
    *           Les CRL
    * @throws SamlFormatException
    *            Lorsque le format de l’assertion est incorrecte par rapport
    *            au(x) schéma(s) XSD
    * @throws SamlSignatureException
    *            Lorsque la signature électronique de l'assertion n'est pas
    *            valide
    */
   public final void verifierAssertion(Element assertionSaml,
         KeyStore keystore, String alias, List<X509CRL> crl)
         throws SamlFormatException, SamlSignatureException {

      try {

         Assertion assertion = (Assertion) SamlXML.unmarshaller(assertionSaml);

         assertionService.validate(assertion);

         X509Certificate x509Certificate = SecurityUtil.loadX509Certificate(
               keystore, alias);

         assertionService.validate(assertion, x509Certificate);

      } catch (KeyStoreException e) {
         throw new SamlSignatureException(e);
      }

   }

}
