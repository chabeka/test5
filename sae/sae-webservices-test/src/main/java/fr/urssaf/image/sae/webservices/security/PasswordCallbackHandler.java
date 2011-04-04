package fr.urssaf.image.sae.webservices.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Paramétrage du UsernameToken de WS-security<br>
 * WS-security est paramatré dans <code>META-INF/axis2.xml</code>
 * 
 * <pre>
 * &lt;parameter name="OutflowSecurity">
 *       &lt;action>
 *          &lt;items>UsernameToken&lt;/items>
 *          &lt;user>myuser&lt;/user>
 *          &lt;passwordCallbackClass>fr.urssaf.image.sae.webservices.security.PasswordCallbackHandler
 *          &lt;/passwordCallbackClass>
 *       &lt;/action>
 * &lt;/parameter>
 * 
 * </pre>
 * 
 * 
 */
public class PasswordCallbackHandler implements CallbackHandler {

   private final Map<String, String> passwords = new HashMap<String, String>();


   /**
    * instanciation d'un tableau associatif pour login/password<br>
    * <ul>
    * <li><code>myuser</code>:<code>mypassword</code></li>
    * </ul>
    * 
    */
   public PasswordCallbackHandler() {
      passwords.put("myuser", "mypassword");

   }

   @Override
   public final void handle(Callback[] callbacks) throws IOException,
         UnsupportedCallbackException {

      for (int i = 0; i < callbacks.length; i++) {

         if (callbacks[i] instanceof WSPasswordCallback) {
            WSPasswordCallback wsPassword = (WSPasswordCallback) callbacks[i];
            if (passwords.containsKey(wsPassword.getIdentifier())) {
               wsPassword
                     .setPassword(passwords.get(wsPassword.getIdentifier()));
               return;
            }

         }

      }
   }

}
