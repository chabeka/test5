package fr.urssaf.image.commons.controller.spring3.exemple.controller.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.urssaf.image.commons.controller.spring3.exemple.controller.base.AbstractExempleController;
import fr.urssaf.image.commons.controller.spring3.exemple.formulaire.FormFormulaire;
import fr.urssaf.image.commons.controller.spring3.exemple.service.DocumentService;

@Controller
@RequestMapping(value = "/form")
public class FormController extends AbstractExempleController{

	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String getDefaultView(Model model) {
		FormFormulaire formFormulaire = new FormFormulaire();
		model.addAttribute(formFormulaire);
		return this.defaultView();
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String save(@Valid FormFormulaire formFormulaire, BindingResult result) {

		if (result.hasErrors()) {
			return defaultView();
		}

		documentService.save(formFormulaire);
		return "redirect:/table.do";

	}

	@RequestMapping(value="table",method = RequestMethod.GET)
	protected String table() {

		return "redirect:/table.do";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "closeDate",
				new CustomDateEditor(dateFormat, true));
	}

	

	public String defaultView() {
		return "form/formView";
	}

}
