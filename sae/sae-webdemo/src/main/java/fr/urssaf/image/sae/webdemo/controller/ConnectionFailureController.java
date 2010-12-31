package fr.urssaf.image.sae.webdemo.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur appelé quand l'utilisateur n'est pas authentifié
 * <code>/connectionFailure.html</code><br>
 * Le contôleur renvoie la vue <code>error/erreur403_pasauthentifie</code> avec
 * un code status <b><code>403</code></b><br>
 * La session devient invalide
 * 
 * @see HttpSession#invalidate()
 * @deprecated le contrôleur est devenu obsolète avec SPRING SECURITY
 */
@Controller
@RequestMapping(value = "/connectionFailure")
public class ConnectionFailureController {

   /**
    * @param session
    * @param response
    * @return
    */
   @RequestMapping(method = RequestMethod.GET)
   protected final String exception(HttpSession session,
         HttpServletResponse response) {

      session.invalidate();

      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return "error/erreur403_pasauthentifie";
   }

}
