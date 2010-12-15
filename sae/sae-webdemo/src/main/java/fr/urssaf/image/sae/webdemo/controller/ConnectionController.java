package fr.urssaf.image.sae.webdemo.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.commons.util.base64.Base64Decode;
import fr.urssaf.image.sae.vi.exception.VIException;
import fr.urssaf.image.sae.vi.service.VIService;
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

   private final VIService viService = new VIService();

   /**
    * action pour la connection en POST<br>
    * <br>
    * le POST comporte deux paramètres obligatoires
    * <ul>
    * <li>RelayState : URL du service demandé commençant par '/' </li>
    * <li>SAMLResponse : VI codé en base 64</li>
    * </ul>
    * Gestion des vues ordonnées:
    * <ol>
    * <li>RelayState & SAMLResponse non renseignés : <code>erreur403_viko.html<code></li>
    * <li>RelayState n'existe pas en tant que service : <code>erreur404_serviceinexistant.html<code></li>
    * <li>VI incorrecte : <code>erreur403_viformatko.html</code></li>
    * <li>Authentifcation réussie : valeur de <code>relayState</code></li>
    * </ol>
    * @param connectionForm
    *           formulaire de la connection
    * @param result erreurs sur les paramètres de la requête
    * @param response status de la réponse
    * @param model paramètres renvoyés
    * @return view de la connection
    */
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

      String servlet = null;

      if (connection.isValidateService(relayState)) {

         String decodeSaml = Base64Decode.decode(samlResponse);
         try {
            
            viService.readVI(decodeSaml);
         
            // on enleve le '/' devant le servlet
            servlet = this.defaultView(relayState.substring(1));
         
         } catch (VIException e) {
            
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            servlet = this.errorView("erreur403_viformatko.html");
         }

        

      } else {

         // response.setStatus(HttpServletResponse.SC_FOUND);
         // TODO initialiser le status de la réponse avec
         // HttpServletResponse.SC_FOUND
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         model.addAttribute("service", relayState);
         servlet = this.errorView("erreur404_serviceinexistant.html");

      }

      return servlet;
   }
}
