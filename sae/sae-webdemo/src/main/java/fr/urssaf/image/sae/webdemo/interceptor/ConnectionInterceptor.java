package fr.urssaf.image.sae.webdemo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import fr.urssaf.image.sae.webdemo.controller.ApplicationDemoController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionController;

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
 * <li>'<code>/erreur403_viko.html</code>'</li>
 * <li>'<code>/erreur404_serviceinexistant.html</code>'</li>
 * <li>'<code>/erreur403_viformatko.html</code>'</li>
 * <li>'<code>/erreur403_pasauthentifie.html</code>'</li>
 * </ul>
 * sont exemptés</li>
 * </ul>
 * 
 * 
 */
public class ConnectionInterceptor extends HandlerInterceptorAdapter {

   @Override
   public final boolean preHandle(HttpServletRequest request,
         HttpServletResponse response, Object handler) {

      boolean execution = false;

      if (handler instanceof ConnectionController) {

         execution = true;

      }

      else if (handler instanceof ApplicationDemoController) {

         execution = true;
      }

      else if (request.getServletPath().startsWith("/accueil.html")) {

         execution = error403(request, response);

      }

      // TODO trouver un autre critère d'exclusion pour l'authentification que
      // ParameterizableViewController
      else if (handler instanceof ParameterizableViewController) {

         // TODO mettre en place un intercepteur pour le statut des reponses
         // erreurs
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         execution = true;
      }

      else {

         execution = error403(request, response);
      }

      return execution;
   }

   @SuppressWarnings("PMD.EmptyCatchBlock")
   private boolean error403(HttpServletRequest request,
         HttpServletResponse response) {

      boolean execution = false;

      if (request.getSession().getAttribute(ConnectionController.SAE_JETON) == null) {

         try {

            response.sendRedirect("erreur403_pasauthentifie.html");
         } catch (IOException e) {

         }

      } else {

         execution = true;
      }

      return execution;
   }

}
