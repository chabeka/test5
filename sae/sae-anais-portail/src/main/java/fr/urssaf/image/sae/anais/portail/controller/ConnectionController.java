package fr.urssaf.image.sae.anais.portail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/connection")
public class ConnectionController {

   @RequestMapping(method = RequestMethod.GET)
   protected String getDefaultView(Model model) {

      return this.defaultView();
   }

   protected String defaultView() {
      return "connection";
   }

}
