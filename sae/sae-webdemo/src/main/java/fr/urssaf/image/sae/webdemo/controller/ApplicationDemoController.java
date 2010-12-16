package fr.urssaf.image.sae.webdemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Servlet d'entrée de l'application web demo<br>
 * <br>
 * <code>http://[serveur]/webdemo/applicationDemo.html</code>
 * 
 */
@Controller
@RequestMapping(value = "/applicationDemo")
public class ApplicationDemoController {

   public static final String SERVLET = "connection.html";

   /**
    * Méthode Post
    * 
    * @return forward vers le POST de {@link ConnectionController}
    */
   @RequestMapping(method = RequestMethod.POST)
   protected final String doPost() {
      return "forward:" + SERVLET;
   }

   /**
    * Méthode GET
    * 
    * @return redirect vers le servlet paramétré dans <code>welcome-file</code>
    *         du web.xml
    */
   @RequestMapping(method = RequestMethod.GET)
   protected final String doGet(HttpSession session) {
      return "redirect:";
   }

}
