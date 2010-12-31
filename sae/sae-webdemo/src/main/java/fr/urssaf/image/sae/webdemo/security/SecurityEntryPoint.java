package fr.urssaf.image.sae.webdemo.security;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Classe d'entrée lorsque l'utilisateur n'est pas authentifié<br>
 * La classe hérite de {@link LoginUrlAuthenticationEntryPoint}<br>
 * Cette classe est paramétré dans le fichier
 * <code>ApplicationContext-security.xml</code>
 * 
 * <pre>
 * &lt;http use-expressions="true" entry-point-ref="authenticationEntryPoint">
 *       ...
 * &lt;/http>
 * ...
 * &lt;beans:bean id="authenticationEntryPoint"
 *       class="fr.urssaf.image.sae.webdemo.security.SecurityEntryPoint">
 * &lt;/beans:bean>
 * 
 * </pre>
 * 
 * 
 */
public class SecurityEntryPoint extends LoginUrlAuthenticationEntryPoint {

   /**
    * initialisation du point d'entrée pour l'authentification<br>
    * <ul>
    * <li>{@link LoginUrlAuthenticationEntryPoint#setLoginFormUrl} :
    * <code>/authentication.html</code></li>
    * <li>{@link LoginUrlAuthenticationEntryPoint#setUseForward} :
    * <code>true</code> pour conserver les paramètres de la requette HTTP</li>
    * </ul>
    * 
    * @see AuthenticationController
    */
   public SecurityEntryPoint() {
      super();
      setLoginFormUrl("/authentication.html");
      setUseForward(true);

   }
}
