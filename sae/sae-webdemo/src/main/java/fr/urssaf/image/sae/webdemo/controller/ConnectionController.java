package fr.urssaf.image.sae.webdemo.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.sae.webdemo.form.ConnectionForm;

@Controller
@RequestMapping(value = "/connection")
public class ConnectionController {

   @RequestMapping(method = RequestMethod.POST)
   protected final String connect(@Valid ConnectionForm connectionForm,
         BindingResult result, HttpServletResponse response) {

      String servlet;

      if (result.hasErrors()) {
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         servlet = "erreur403_viko.html";
      } else {

         servlet = connectionForm.getRelayState();

      }

      return this.defaultView(servlet);
   }

   protected final String defaultView(String servlet) {
      return "forward:" + servlet;
   }
}
