package fr.urssaf.image.sae.webdemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.urssaf.image.sae.webdemo.form.LoggerForm;
import fr.urssaf.image.sae.webdemo.resource.Dir;
import fr.urssaf.image.sae.webdemo.service.dao.LogDAO;

/**
 * Contrôleur de présentation des traces à destination du service d'exploitation<br>
 * <br>
 * La page de ce contrôleur est <code>registre_exploitation</code><br>
 * <br>
 * servlets prise en compte
 * <ul>
 * <li><code>/registre_exploitation</code> en GET : affichage de l'écran</li>
 * <li><code>/registre_exploitation/search</code> en POST : mise à jour du
 * tableau des traces</li>
 * <li><code>/registre_exploitation/filter</code> en POST : filtrage du tableau
 * des traces</li>
 * </ul>
 */
@Controller
@RequestMapping(value = "/registre_exploitation")
public class LoggerController {

   protected static final Logger LOG = Logger.getLogger(LoggerController.class);

   private static final String FORM_FAILURE_MSG = "error.form_failure_msg";

   private final LogDAO logDAO;

   @Autowired
   private MessageSource messageSource;

   /**
    * Initialisation de la variable <code>logDAO<code><br>
    * <br>
    * <code>logDAO<code> ne peut pas être null
    * 
    * @see LogDAO
    * @param logDAO
    *           dao des traces
    */
   @Autowired
   public LoggerController(LogDAO logDAO) {

      if (logDAO == null) {
         throw new IllegalStateException("'logDAO' is required");
      }

      this.logDAO = logDAO;
   }

   /**
    * Action GET principale pour l'affichage de la vue
    * <code>registre_exploitation</code><br>
    * Ne fait que renvoyer la vue
    * 
    * @return la vue principale de l'écran de présentation des traces
    */
   @RequestMapping(method = RequestMethod.GET)
   protected final String main() {

      return "registre_exploitation";
   }

   /**
    * Action POST en AJAX pour le filtrage du tableau des traces d'exploitations<br>
    * <br>
    * le formulaire comporte erreurs
    * <ul>
    * <li><code>success</code> : false</li>
    * <li><code>errorMessage</code> : code du message 'error.form_failure_msg'</li>
    * <li><code>errors</code> : liste des erreurs de type [field,message de
    * l'erreur]</li>
    * </ul>
    * Le formulaire ne comporte aucune erreur
    * <ul>
    * <li><code>success</code> : false</li>
    * </ul>
    * 
    * @param form
    *           formulaire pour l'écran des traces
    * @param result
    *           résultats du formulaire
    * @param request
    *           requête HTTP
    * @param response
    *           réponse HTTP
    * @return
    */
   @RequestMapping(value = "/filter", method = RequestMethod.POST)
   protected final @ResponseBody
   Map<String, ? extends Object> filter(@Valid LoggerForm form,
         BindingResult result, HttpServletRequest request,
         HttpServletResponse response) {

      form.validate(result);

      Map<String, Object> filter = new HashMap<String, Object>();
      if (result.hasErrors()) {

         filter.put("success", false);
         filter.put("errorMessage", messageSource.getMessage(FORM_FAILURE_MSG,
               new String[0], request.getLocale()));
         filter.put("errors", validationMessages(result.getFieldErrors(),
               request));

      } else {

         filter.put("success", true);
      }

      return filter;
   }

   private Map<String, String> validationMessages(List<FieldError> errors,
         HttpServletRequest request) {
      Map<String, String> failureMessages = new HashMap<String, String>();
      for (FieldError error : errors) {

         String message = messageSource.getMessage(error, request.getLocale());
         failureMessages.put(error.getField(), message);

      }
      return failureMessages;
   }

   /**
    * Action POST en AJAX pour l'affichage du tableau des traces d'exploitation<br>
    * <br>
    * L'action appelle le service {@link LogDAO} pour récuperer la liste des
    * traces<br>
    * <br>
    * Paramètres renvoyés dans le {@link ResponseBody}
    * <ul>
    * <li><code>logs</code> : liste des traces</li>
    * <li><code>totalCount</code> : nombre de traces</li>
    * </ul>
    * 
    * @param start
    *           numéro de page du tableau
    * @param limit
    *           nombre maximum de résultats par page
    * @param sort
    *           nom de la colonne triée
    * @param dir
    *           ordre du tri {DESC/ASC)
    * @return corps de la réponse
    */
   @RequestMapping(value = "/search", method = RequestMethod.POST)
   protected final @ResponseBody
   Map<String, ? extends Object> search(@RequestParam int start,
         @RequestParam int limit, @RequestParam String sort,
         @RequestParam Dir dir) {

      Map<String, Object> search = new HashMap<String, Object>();

      search.put("logs", logDAO.find(start, limit, sort, dir));
      search.put("totalCount", logDAO.count());

      return search;
   }

}
