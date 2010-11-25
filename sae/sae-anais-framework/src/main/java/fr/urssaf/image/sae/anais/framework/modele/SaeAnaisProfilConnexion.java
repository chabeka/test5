package fr.urssaf.image.sae.anais.framework.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Profil de connexion à ANAIS pour une application donnée
 * 
 * 
 */
public class SaeAnaisProfilConnexion {

   private String compteApplicatifDn;

   private String compteApplicatifPassword;

   private String codeApplication;

   private SaeAnaisEnumCodesEnvironnement codeEnvironnement;

   private List<SaeAnaisAdresseServeur> serveurs = new ArrayList<SaeAnaisAdresseServeur>();

   /**
    * 
    * @return Le DN du compte applicatif utilisé pour se connecter à l’annuaire
    */
   public String getCompteApplicatifDn() {
      return compteApplicatifDn;
   }

   /**
    * 
    * @param compteApplicatifDn
    *           Le DN du compte applicatif utilisé pour se connecter à
    *           l’annuaire
    */
   public void setCompteApplicatifDn(String compteApplicatifDn) {
      this.compteApplicatifDn = compteApplicatifDn;
   }

   /**
    * 
    * @return Le mot de passe du compte applicatif
    */
   public String getCompteApplicatifPassword() {
      return compteApplicatifPassword;
   }

   /**
    * 
    * @param compteApplicatifPassword
    *           Le mot de passe du compte applicatif
    */
   public void setCompteApplicatifPassword(String compteApplicatifPassword) {
      this.compteApplicatifPassword = compteApplicatifPassword;
   }

   /**
    * 
    * @return Le code de l’application utilisé pour se connecter à l’annuaire
    */
   public String getCodeApplication() {
      return codeApplication;
   }

   /**
    * 
    * @param codeApplication
    *           Le code de l’application utilisé pour se connecter à l’annuaire
    */
   public void setCodeApplication(String codeApplication) {
      this.codeApplication = codeApplication;
   }

   /**
    * @see SaeAnaisAdresseServeur
    * 
    * @return Le code de l’environnement
    */
   public SaeAnaisEnumCodesEnvironnement getCodeEnvironnement() {
      return codeEnvironnement;
   }

   /**
    * @see SaeAnaisAdresseServeur
    * 
    * @param codeEnvironnement
    *           Le code de l’environnement
    */
   public void setCodeEnvironnement(
         SaeAnaisEnumCodesEnvironnement codeEnvironnement) {
      this.codeEnvironnement = codeEnvironnement;
   }

   /**
    * 
    * @see SaeAnaisAdresseServeur
    * 
    * @param serveurs
    *           La liste des adresses des serveurs ANAIS
    * 
    */
   public void setServeurs(List<SaeAnaisAdresseServeur> serveurs) {
      this.serveurs = serveurs;
   }

   /**
    * @see SaeAnaisAdresseServeur
    * 
    * @return La liste des adresses des serveurs ANAIS
    */
   public List<SaeAnaisAdresseServeur> getServeurs() {
      return serveurs;
   }

}
