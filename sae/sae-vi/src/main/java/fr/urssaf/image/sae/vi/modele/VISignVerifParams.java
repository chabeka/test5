package fr.urssaf.image.sae.vi.modele;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.List;


/**
 * Eléments permettant de vérifier la signature d'un VI
 */
public class VISignVerifParams {

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
    * @param certifsACRacine Les certificats des AC racine
    */
   public final void setCertifsACRacine(List<X509Certificate> certifsACRacine) {
      this.certifsACRacine = certifsACRacine;
   }

   /**
    * Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    * signature de VI
    * 
    * @return  Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    *          signature de VI
    */
   public final List<X509CRL> getCrls() {
      return crls;
   }

   /**
    * Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    * signature de VI
    * 
    * @param crls 
    *    Les CRL de toutes les AC impliquées dans la délivrance des certificats de
    *    signature de VI
    */
   public final void setCrls(List<X509CRL> crls) {
      this.crls = crls;
   }
   
}
