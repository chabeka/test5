package fr.urssaf.image.sae.anais.framework.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de paramétrage d'un profil ANAIS (Developpement/Validation/Production)<br>
 * 
 */
public class SaeAnaisProfilConnexion {

   private String cptDn;

   private String password;

   private String codeApplication;

   private SaeAnaisEnumCodesEnvironnement codeEnvironnement;

   private List<SaeAnaisAdresseServeur> serveurs = new ArrayList<SaeAnaisAdresseServeur>();

   /**
    * 
    * @return Le DN du compte applicatif utilisé pour se connecter à l'annuaire
    */
   public final String getCompteApplicatifDn() {
      return cptDn;
   }

   /**
    * 
    * @param cptDn
    *           Le DN du compte applicatif utilisé pour se connecter à
    *           l'annuaire
    */
   public final void setCompteApplicatifDn(String cptDn) {
      this.cptDn = cptDn;
   }

   /**
    * 
    * @return Le mot de passe du compte applicatif
    */
   public final String getCompteApplicatifPassword() {
      return password;
   }

   /**
    * 
    * @param password
    *           Le mot de passe du compte applicatif
    */
   public final void setCompteApplicatifPassword(String password) {
      this.password = password;
   }

   /**
    * 
    * @return Le code de l’application utilisé pour se connecter à l'annuaire
    */
   public final String getCodeApplication() {
      return codeApplication;
   }

   /**
    * 
    * @param codeApplication
    *           Le code de l'application utilisé pour se connecter à l'annuaire
    */
   public final void setCodeApplication(String codeApplication) {
      this.codeApplication = codeApplication;
   }

   /**
    * @see SaeAnaisAdresseServeur
    * 
    * @return Le code de l'environnement
    */
   public final SaeAnaisEnumCodesEnvironnement getCodeEnvironnement() {
      return codeEnvironnement;
   }

   /**
    * @see SaeAnaisAdresseServeur
    * 
    * @param codeEnvironnement
    *           Le code de l'environnement
    */
   public final void setCodeEnvironnement(
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
   public final void setServeurs(List<SaeAnaisAdresseServeur> serveurs) {
      this.serveurs = serveurs;
   }

   /**
    * @see SaeAnaisAdresseServeur
    * 
    * @return La liste des adresses des serveurs ANAIS
    */
   public final List<SaeAnaisAdresseServeur> getServeurs() {
      return serveurs;
   }

}
