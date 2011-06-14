package fr.urssaf.image.sae.storage.model.connection;

/**
 * Classe concrète contenant les caractéristiques de l’utilisateur mit à
 * disposition pour se connecter à la base de stockage. <BR />
 * 
 * <li>
 * Attribut login Représente le login de l’utilisateur</li>
 * 
 * <li>
 * Attribut password Représente le mot de passe de l’utilisateur</li>
 */
public class StorageUser {

   private String login;

   private String password;

   /**
    * Retourne le login de l’utilisateur
    * 
    * @return L'identifiant de login
    */
   public final String getLogin() {
      return login;
   }

   /**
    * Initialise le login de l’utilisateur
    * 
    * @param login : 
    *           Le login de l'utilisateur
    */
   public final void setLogin(final String login) {
      this.login = login;
   }

   /**
    * Retourne le mot de passe de l’utilisateur
    * 
    * @return Le mot de passe
    */
   public final String getPassword() {
      return password;
   }

   /**
    * Initialise le mot de passe de l’utilisateur
    * 
    * @param password : 
    *           Le mot de passe de l'utilisateur
    */
   public final void setPassword(final String password) {
      this.password = password;
   }

   /**
    * Constructeur
    * 
    * @param login : 
    *           Le login de l'utilisateur
    * @param password : 
    *           Le mot de passe de l'utilisateur
    */
   public StorageUser(final String login, final String password) {
      this.login = login;
      this.password = password;
   }
}
