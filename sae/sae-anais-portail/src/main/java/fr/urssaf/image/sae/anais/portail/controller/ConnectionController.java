package fr.urssaf.image.sae.anais.portail.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException;
import fr.urssaf.image.sae.anais.portail.configuration.SuccessConfiguration;
import fr.urssaf.image.sae.anais.portail.form.ConnectionForm;
import fr.urssaf.image.sae.anais.portail.service.ConnectionService;

/**
 * Classe de manipulation de la servlet <code>/connection.html</code>
 * 
 * 
 */
@Controller
@RequestMapping(value = "/connection")
public class ConnectionController {

   @Autowired
   private ConnectionService connectionService;

   private SuccessConfiguration configuration;

   /**
    * initialisation de la configuration de l'application web du SAE<br>
    * <br>
    * initialisation obligatoire avant d'appeller la méthode
    * {@link #successServlet()}
    * 
    * @param configuration
    *           configuration d'une application web
    */
   @Autowired
   public final void setConfiguration(
         @Qualifier("configurationSuccess") SuccessConfiguration configuration) {
      this.configuration = configuration;
   }

   /**
    * Action pour l'affichage par défaut en GET<br>
    * Initialisation du formulaire
    * 
    * <pre>
    * &lt;form:form method="post" modelAttribute="connectionForm" name="form_cirti">
    * </pre>
    * 
    * @param model
    *           contenu du formulaire
    * @return {@link #defaultView()}
    */
   @RequestMapping(method = RequestMethod.GET)
   protected final String getDefaultView(Model model) {

      ConnectionForm connectionForm = new ConnectionForm();
      model.addAttribute(connectionForm);

      return this.defaultView();
   }

   /**
    * Soumission du formulaire
    * 
    * <pre>
    * &lt;form:form method="post" modelAttribute="connectionForm" name="form_cirti">
    * </pre>
    * 
    * La méthode appelle {@link ConnectionService#connect} avec les paramètres
    * du formulaire <br>
    * <br>
    * Le formulaire est validé
    * 
    * @param connectionForm
    *           {@link ConnectionForm}
    * @param result
    *           erreurs du formulaire
    * @param model
    *           attribut du formulaire
    * @return <ul>
    *         <li>erreurs sur le formulaire: {@link #defaultView()}</i>
    *         <li>échec {@link SaeAnaisApiException} : {@link #failuretView()}</li>
    *         <li>succès : {@link #successServlet()}</li>
    *         <ul>
    */
   @RequestMapping(method = RequestMethod.POST)
   protected final String connect(@Valid ConnectionForm connectionForm,
         BindingResult result, Model model, HttpSession session) {

      String view;
      if (result.hasErrors()) {
         view = failureView();
      } else {

         try {

            session.setAttribute("SAMLResponse", connectionService.connect(
                  connectionForm.getUserLogin(), connectionForm
                        .getUserPassword()));
            session.setAttribute("RelayState", configuration.getService());
            view = successServlet();
         } catch (SaeAnaisApiException e) {
            model.addAttribute("failure", e.getMessage());
            view = failureView();
         }

      }

      return view;

   }

   /**
    * Vue par défaut de la connexion
    * 
    * @return <code>connection/connection.jsp</code>
    */
   protected final String defaultView() {
      return "connection/connection";
   }

   /**
    * Vue pour l'échec de la connexion
    * 
    * @return <code>connection/connection_failure.jsp</code>
    */
   protected final String failureView() {
      return "connection/connection_failure";
   }

   /**
    * Servlet en cas de succcès de la connexion<br>
    * <br>
    * l'URL est configuré : <br>
    * <code>"redirect:" + configuration.getUrl()</code>
    * 
    * @see SuccessConfiguration
    * @return URL de l'application web du SAE
    */
   protected final String successServlet() {
      return "redirect:" + configuration.getUrl();
   }

}
