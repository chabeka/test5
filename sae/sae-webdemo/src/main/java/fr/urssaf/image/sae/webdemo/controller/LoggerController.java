package fr.urssaf.image.sae.webdemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.urssaf.image.sae.webdemo.modele.Log;
import fr.urssaf.image.sae.webdemo.service.dao.LogDAO;

/**
 * Contrôleur de présentation des traces à destination du service d'exploitation<br>
 * 
 * 
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
   public LoggerController(@Qualifier("logDAO") LogDAO logDAO) {

      if (logDAO == null) {
         throw new IllegalStateException("'logDAO' is required");
      }

      this.logDAO = logDAO;
   }

   @RequestMapping(method = RequestMethod.GET)
   protected final String main(HttpServletResponse response) {

      return "registre_exploitation";
   }

   @RequestMapping(value = "/search", method = RequestMethod.POST, params = {
         "start", "limit", "sort", "dir" })
   protected final @ResponseBody
   Map<String, ? extends Object> search(@RequestParam int start,
         @RequestParam int limit, @RequestParam String sort,
         @RequestParam String dir) {

      Map<String, List<Log>> search = new HashMap<String, List<Log>>();

      search.put("logs", logDAO.find());

      return search;
   }

}
