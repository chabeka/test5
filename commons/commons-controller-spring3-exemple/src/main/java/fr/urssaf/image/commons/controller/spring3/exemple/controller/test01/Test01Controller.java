package fr.urssaf.image.commons.controller.spring3.exemple.controller.test01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.commons.controller.spring3.exemple.formulaire.Test01Formulaire;



@Controller
@RequestMapping(value = "/test01")
public class Test01Controller {

   @RequestMapping(method = RequestMethod.GET)
   protected String getDefaultView(Model model)
   {
      Test01Formulaire test01Formulaire = new Test01Formulaire();
      model.addAttribute(test01Formulaire);
      return "test01";
   }

   @RequestMapping(method = RequestMethod.POST)
   protected String action(Test01Formulaire formFormulaire)
   {
      formFormulaire.setReponse("Réponse de l'action !");
      return "test01";
   }
  
}
