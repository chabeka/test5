/**
 * 
 */
package fr.urssaf.image.sae.integration.ihmweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.ListeCasDeTestFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.ecde.EcdeTest;
import fr.urssaf.image.sae.integration.ihmweb.modele.ecde.EcdeTests;
import fr.urssaf.image.sae.integration.ihmweb.service.ecde.file.EcdeTestInterface;

/**
 * 
 * Controller permettant la gestion des cas de test
 */
@Controller
@RequestMapping(value = "listeCasDeTest")
public class ListeCasDeTestController {

   @Autowired
   private EcdeTests ecdeTests;

   @Autowired
   private EcdeTestInterface ecdeTestInterface;

   @RequestMapping(method = RequestMethod.GET)
   public String getDefaultView(Model model) {

      ListeCasDeTestFormulaire testFormulaire = new ListeCasDeTestFormulaire();

      testFormulaire.setEcdeTests(ecdeTests);

      model.addAttribute("formulaire", testFormulaire);

      return "listeCasDeTest";
   }

   @RequestMapping(method = RequestMethod.POST, params = { "action=add" })
   public String saveListeEcdeSources(Model model, ListeCasDeTestFormulaire form) {

      List<EcdeTest> listEcde = form.getEcdeTests().getListTests();
      listEcde.add(form.getEcdeTest());

      form.setEcdeTest(new EcdeTest());

      model.addAttribute("formulaire", form);
      return "listeCasDeTest";
   }

   @RequestMapping(method = RequestMethod.POST, params = { "action=delete" })
   public String deleteListeEcdeSources(Model model,
         ListeCasDeTestFormulaire form, Integer idSup) {

      List<EcdeTest> listEcde = form.getEcdeTests().getListTests();
      listEcde.remove(idSup.intValue());

      model.addAttribute("formulaire", form);

      return "listeCasDeTest";
   }

   @RequestMapping(method = RequestMethod.POST, params = { "action=generate" })
   public String generateListeEcdeSources(Model model,
         ListeCasDeTestFormulaire form, BindingResult errors) throws Exception {

      ecdeTestInterface.generateFile(form.getEcdeTests().getListTests());

      ecdeTests.setListTests(form.getEcdeTests().getListTests());

      model.addAttribute("formulaire", form);

      return "listeCasDeTest";
   }
}
