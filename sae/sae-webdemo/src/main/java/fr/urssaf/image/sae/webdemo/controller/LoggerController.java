package fr.urssaf.image.sae.webdemo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur de présentation des traces à destination du service d'exploitation<br>
 * 
 * 
 */
@Controller
@RequestMapping(value = "/registre_exploitation")
public class LoggerController {

   @RequestMapping(method = RequestMethod.GET)
   protected final String main(HttpServletResponse response) {

      return "registre_exploitation";
   }

}
