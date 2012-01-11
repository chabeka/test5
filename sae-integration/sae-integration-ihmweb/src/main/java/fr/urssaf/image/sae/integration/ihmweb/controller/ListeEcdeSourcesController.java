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

import fr.urssaf.image.sae.integration.ihmweb.formulaire.ListeEcdeSourcesFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.ecde.EcdeSource;
import fr.urssaf.image.sae.integration.ihmweb.modele.ecde.EcdeSources;
import fr.urssaf.image.sae.integration.ihmweb.service.ecde.file.EcdeSourceManager;

/**
 * Controller permettant l'affichage, la modification, la création et la
 * suppression des mappings
 * 
 */
@Controller
@RequestMapping(value = "listeEcdeSources")
public class ListeEcdeSourcesController {

   @Autowired
   private EcdeSourceManager ecdeSourceManager;

   @Autowired
   private EcdeSources ecdeSources;

   @RequestMapping(method = RequestMethod.GET)
   public String viewListeEcdeSources(Model model) {

      ListeEcdeSourcesFormulaire form = new ListeEcdeSourcesFormulaire();
      // try {
      // EcdeSources ecdeSources = ecdeSourceManager.load();
      form.setEcdeSources(ecdeSources);

      // } catch (Exception e) {
      // e.printStackTrace();
      // }

      model.addAttribute("formulaire", form);

      return "listeEcdeSources";

   }

   @RequestMapping(method = RequestMethod.POST, params = { "action=add" })
   public String saveListeEcdeSources(Model model,
         ListeEcdeSourcesFormulaire form) {

      List<EcdeSource> listEcde = form.getEcdeSources().getSources();
      listEcde.add(form.getSource());

      form.setSource(new EcdeSource());

      model.addAttribute("formulaire", form);
      return "listeEcdeSources";
   }

   @RequestMapping(method = RequestMethod.POST, params = { "action=delete" })
   public String deleteListeEcdeSources(Model model,
         ListeEcdeSourcesFormulaire form, Integer idSup) {

      List<EcdeSource> listEcde = form.getEcdeSources().getSources();
      listEcde.remove(idSup.intValue());

      model.addAttribute("formulaire", form);

      return "listeEcdeSources";
   }

   @RequestMapping(method = RequestMethod.POST, params = { "action=generate" })
   public String generateListeEcdeSources(Model model,
         ListeEcdeSourcesFormulaire form, BindingResult errors)
         throws Exception {

      ecdeSourceManager.generate(form.getEcdeSources().getSources());

      ecdeSources.setSources(form.getEcdeSources().getSources());

      model.addAttribute("formulaire", form);

      return "listeEcdeSources";
   }
}
