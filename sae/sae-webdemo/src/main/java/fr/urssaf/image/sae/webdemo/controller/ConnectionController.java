package fr.urssaf.image.sae.webdemo.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.sae.webdemo.form.ConnectionForm;
import fr.urssaf.image.sae.webdemo.service.ConnectionService;

/**
 * Classe de manipulation de la servlet <code>/connection.html</code><br>
 * la classe ne possède qu'une méthode en POST {@link #connect}
 * 
 */
@Controller
@RequestMapping(value = "/connection")
public class ConnectionController {

   @Autowired
   private ConnectionService connection;

   /**
    * action pour la connection en POST<br>
    * 
    * @param connectionForm
    *           formulaire de la connection
    * @param result
    * @param response
    * @param model
    * @return
    */
   // TODO finir commentaire de l'action connect
   @RequestMapping(method = RequestMethod.POST)
   protected final String connect(@Valid ConnectionForm connectionForm,
         BindingResult result, HttpServletResponse response, Model model) {

      String servlet = null;

      if (result.hasErrors()) {

         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         servlet = this.errorView("erreur403_viko.html");

      } else {

         servlet = this.getServlet(connectionForm.getRelayState(),
               connectionForm.getSAMLResponse(), response, model);
      }

      return servlet;
   }

   protected final String errorView(String servlet) {
      return "forward:" + servlet;
   }

   protected final String defaultView(String servlet) {
      return "redirect:" + servlet;
   }

   private String getServlet(String relayState, String samlResponse,
         HttpServletResponse response, Model model) {

      boolean validateService = connection.isValidateService(relayState);

      boolean validateVI = connection.isValidateVI(samlResponse);

      String servlet = null;

      if (validateService && validateVI) {

         // on enleve le '/' devant le servlet
         servlet = this.defaultView(relayState.substring(1));

      } else {

         if (!validateService) {

            // response.setStatus(HttpServletResponse.SC_FOUND);
            // TODO initialiser le status de la réponse avec
            // HttpServletResponse.SC_FOUND
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            model.addAttribute("service", relayState);
            servlet = this.errorView("erreur404_serviceinexistant.html");

         }

         if (!validateVI) {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            model.addAttribute("service", relayState);
            servlet = this.errorView("erreur403_viformatko.html");

         }

      }

      return servlet;
   }
}
