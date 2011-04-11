package fr.urssaf.image.sae.saml.params;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.List;


/**
 * Eléments permettant de vérifier la signature d'une assertion SAML
 */
public class SamlSignatureVerifParams {
   
   
   private List<X509Certificate> certifsACRacine;
   
   private List<X509CRL> crls;

   /**
    * Les certificats des AC racine
    * 
    * @return Les certificats des AC racine
    */
   public final List<X509Certificate> getCertifsACRacine() {
      return certifsACRacine;
   }

   /**
    * Les certificats des AC racine
    * 
    * @param lstCertifACRacine Les certificats des AC racine
    */
   public final void setCertifsACRacine(List<X509Certificate> certifsACRacine) {
      this.certifsACRacine = certifsACRacine;
   }

   /**
    * Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    * signature d'assertion SAML
    * 
    * @return  Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    *          signature d'assertion SAML
    */
   public final List<X509CRL> getCrls() {
      return crls;
   }

   /**
    * Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    * signature d'assertion SAML
    * 
    * @param crls 
    *    Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    *    signature d'assertion SAML
    */
   public final void setCrls(List<X509CRL> crls) {
      this.crls = crls;
   }

}
