package fr.urssaf.image.sae.webdemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.urssaf.image.sae.webdemo.resource.Dir;
import fr.urssaf.image.sae.webdemo.service.dao.LogDAO;

/**
 * Contrôleur de présentation des traces à destination du service d'exploitation<br>
 * <br>
 * La page de ce contrôleur est <code>registre_exploitation</code><br>
 * <br>
 * servlets prise en compte
 * <ul>
 * <li><code>/registre_exploitation</code> en GET : affichage de l'écran </li>
 * <li><code>/registre_exploitation/search</code> en POST : mise à jour du tableau des traces</li>
 * </ul>
 */
@Controller
@RequestMapping(value = "/registre_exploitation")
public class LoggerController {

   protected static final Logger LOG = Logger.getLogger(LoggerController.class);

   private final LogDAO logDAO;

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
    * Action GET principale pour l'affichage de la vue <code>registre_exploitation</code><br>
    * Ne fait que renvoyer la vue
    * 
    * @return la vue principale de l'écran de présentation des traces
    */
   @RequestMapping(method = RequestMethod.GET)
   protected final String main() {

      return "registre_exploitation";
   }

   /**
    * Action POST en AJAX pour l'affichage du tableau des traces d'exploitation<br>
    * <br>
    * L'action appelle le service {@link LogDAO} pour récuperer la liste des traces<br>
    * <br>
    * Paramètres renvoyés dans le {@link  ResponseBody}
    * <ul>
    * <li><code>logs</code> : liste des traces</li>
    * <li><code>totalCount</code> : nombre de traces</li>
    * </ul>
    * 
    * @param start numéro de page du tableau
    * @param limit nombre maximum de résultats par page
    * @param sort nom de la colonne triée
    * @param dir ordre du tri {DESC/ASC)
    * @return corps de la réponse
    */
   @RequestMapping(value = "/search", method = RequestMethod.POST, params = {
         "start", "limit", "sort", "dir" })
   protected final @ResponseBody
   Map<String, ? extends Object> search(@RequestParam int start,
         @RequestParam int limit, @RequestParam String sort,
         @RequestParam Dir dir) {

      Map<String,Object> search = new HashMap<String,Object>();

      search.put("logs", logDAO.find(start,limit,sort,dir));
      search.put("totalCount", logDAO.count());

      return search;
   }

}
