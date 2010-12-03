package fr.urssaf.image.sae.anais.portail.form;

/**
 * Classe de formulaire correspondant à l'authentification auprès d'ANAIS :
 * <code>connection/form.jsp </code><br>
 * <br>
 * 
 * <pre>
 * &lt;form:form method="post" modelAttribute="connectionForm" name="form_cirti">
 * 
 * ...
 * 
 * &lt;/form:form>
 * </pre>
 * 
 * 
 */
public class ConnectionForm {

   private String userLogin;

   private String userPassword;

   /**
    * Retourne le contenu de la balise &lt;form:input path='userLogin'
    * id="login"/>
    * 
    * @return Identifiant
    */
   public final String getUserLogin() {
      return userLogin;
   }

   /**
    * Initialise le contenu de la balise &lt;form:input path='userLogin'
    * id="login"/>
    * 
    * @param userLogin
    *           Identifiant
    */
   public final void setUserLogin(String userLogin) {
      this.userLogin = userLogin;
   }

   /**
    * Retourne le contenu de la balise &lt;form:password path='userPassword'
    * id="password"/>
    * 
    * @return Mot de passe
    */
   public final String getUserPassword() {
      return userPassword;
   }

   /**
    * Initialise le contenu de la balise &lt;form:password path='userPassword'
    * id="password"/>
    * 
    * @param userPassword
    *           Mot de passe
    */
   public final void setUserPassword(String userPassword) {
      this.userPassword = userPassword;
   }

}
