package fr.urssaf.image.sae.webdemo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fr.urssaf.image.sae.webdemo.controller.ApplicationDemoController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionFailureController;

/**
 * Classe héritant de {@link HandlerInterceptorAdapter}<br>
 * Pour tous les servlets
 * <ul>
 * <li>Si {@link ConnectionController.SAE_JETON} est présent en Session alors
 * l'utilisateur est connecté</li>
 * <li>Sinon l'utilisateur n'est pas connecté et la classe renvoie la vue
 * <code>erreur403_pasauthentifie.html</code></li>
 * <li>Les servlets :
 * <ul>
 * <li>'<code>/applicationDemo.html</code>'</li>
 * <li>'<code>/connection.html</code>'</li>
 * <li>'<code>/pasauthentifie.html</code>'</li>
 * </ul>
 * sont exemptés</li>
 * </ul>
 * 
 * 
 */
public class ConnectionInterceptor extends HandlerInterceptorAdapter {

   @Override
   @SuppressWarnings("PMD.SignatureDeclareThrowsException")
   public final boolean preHandle(HttpServletRequest request,
         HttpServletResponse response, Object handler) throws Exception {

      boolean execution = false;

      if (handler instanceof ConnectionController) {

         execution = true;

      }

      else if (handler instanceof ApplicationDemoController) {

         execution = true;
      }

      else if (handler instanceof ConnectionFailureController) {

         execution = true;
      }

      else {

         execution = error403(request, response);
      }

      return execution;
   }

   private boolean error403(HttpServletRequest request,
         HttpServletResponse response) throws IOException {

      boolean execution = false;

      if (request.getSession().getAttribute(ConnectionController.SAE_JETON) == null) {

         response.sendRedirect("connectionFailure.html");

      } else {

         execution = true;
      }

      return execution;
   }

}
