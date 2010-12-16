package fr.urssaf.image.sae.webdemo.controller;

import java.beans.PropertyEditorSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import fr.urssaf.image.commons.util.base64.Base64Decode;
import fr.urssaf.image.sae.vi.exception.VIException;
import fr.urssaf.image.sae.vi.schema.SaeJetonAuthentificationType;
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

   public static final String SAE_JETON = "SaeJetonAuthentification";

   private final ConnectionService connection;

   private final VIService viService;

   /**
    * Initialisation de la variable <code>connection<code> et instanciation d'un {@link VIService}<br>
    * <br>
    * <code>connection<code> ne peut pas être null
    * 
    * @see ConnectionService
    * @param connection
    *           service de connection
    */
   @Autowired
   public ConnectionController(@Qualifier("connectionService") ConnectionService connection) {

      if (connection == null) {
         throw new IllegalStateException("'connectionService' is required");
      }

      this.connection = connection;
      this.viService = new VIService();
   }

   /**
    * action pour la connection en POST<br>
    * <br>
    * le POST comporte deux paramètres obligatoires
    * <ul>
    * <li>RelayState : URL du service demandé commençant par '/'</li>
    * <li>SAMLResponse : VI codé en base 64</li>
    * </ul>
    * <code>SAMLResponse</code> est décodé automatiquement de la base64<br>
    * <br>
    * Gestion des vues ordonnées:
    * <ol>
    * <li>RelayState & SAMLResponse non renseignés :
    * <code>erreur403_viko.html<code></li>
    * <li>RelayState n'existe pas en tant que service :
    * <code>erreur404_serviceinexistant.html<code></li>
    * <li>VI incorrecte : <code>erreur403_viformatko.html</code></li>
    * <li>Authentifcation réussie : valeur de <code>relayState</code></li>
    * </ol>
    * 
    * @param connectionForm
    *           formulaire de la connection
    * @param result
    *           erreurs sur les paramètres de la requête
    * @param response
    *           status de la réponse
    * @param model
    *           paramètres renvoyés
    * @return view de la connection
    */
   @RequestMapping(method = RequestMethod.POST)
   protected final String connect(@Valid ConnectionForm connectionForm,
         BindingResult result, HttpServletRequest request,
         HttpServletResponse response, Model model) {

      String servlet = null;

      if (result.hasErrors()) {

         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         servlet = this.errorView("erreur403_viko.html");

      } else {

         if (connection.isValidateService(connectionForm.getRelayState())) {

            try {

               this.createSession(connectionForm.getSAMLResponse(), request);

               // on enleve le '/' devant le servlet
               servlet = this.defaultView(connectionForm.getRelayState()
                     .substring(1));

            } catch (VIException e) {

               response.setStatus(HttpServletResponse.SC_FORBIDDEN);
               servlet = this.errorView("erreur403_viformatko.html");
            }

         } else {

            // response.setStatus(HttpServletResponse.SC_FOUND);
            // TODO initialiser le status de la réponse avec
            // HttpServletResponse.SC_FOUND
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            model.addAttribute("service", connectionForm.getRelayState());
            servlet = this.errorView("erreur404_serviceinexistant.html");

         }
      }

      return servlet;
   }

   protected final String errorView(String servlet) {
      return "forward:" + servlet;
   }

   protected final String defaultView(String servlet) {
      return "redirect:" + servlet;
   }

   private void createSession(String samlResponse, HttpServletRequest request)
         throws VIException {

      // lecture du jeton
      SaeJetonAuthentificationType jeton = viService.readVI(samlResponse);

      // invalidation de la session
      request.getSession().invalidate();

      // creation d'un objet SaeJetonAuthentification en session;
      WebUtils.setSessionAttribute(request, SAE_JETON, jeton);

   }

   @InitBinder
   protected final void decodageBase64(WebDataBinder binder,
         HttpServletRequest request) {

      binder.registerCustomEditor(String.class, "SAMLResponse",
            new DecodageBase64());
   }

   private static class DecodageBase64 extends PropertyEditorSupport {

      @Override
      public void setAsText(String text) {
         if (StringUtils.hasText(text)) {
            this.setValue(Base64Decode.decode(text));

         } else {

            setValue(null);
         }

      }

   }

}
