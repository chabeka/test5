package fr.urssaf.image.commons.controller.spring.exemple.controller.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


import fr.urssaf.image.commons.controller.spring.exemple.controller.base.AbstractExempleController;
import fr.urssaf.image.commons.controller.spring.exemple.formulaire.FormFormulaire;
import fr.urssaf.image.commons.controller.spring.exemple.service.DocumentService;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.Type;
import fr.urssaf.image.commons.controller.spring.formulaire.type.DateType;
import fr.urssaf.image.commons.controller.spring.servlet.support.annotation.Validation;

@Controller
public class FormController extends AbstractExempleController<FormFormulaire> {

	@Autowired
	private DocumentService documentService;

	public FormController() {
		super(FormFormulaire.class);
	}

	@Validation
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse reponse) {

		FormFormulaire formulaire = this.getFormulaire(request, reponse);

		documentService.save(formulaire);

		return new ModelAndView("tableForward", "methode", "populate");

	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse reponse) {

		return new ModelAndView("tableForward");
	}

	@Override
	protected void completeTypeFactory(ClassForm<FormFormulaire> classForm,
			HttpServletRequest request) {

		Type<Date> type = new Type<Date>("closeDate",
				new DateType("yyyy-MM-dd"));

		classForm.getTypeFactory().addTypeFormulaire(type);

	}

	@Override
	protected ModelAndView getDefaultView(HttpServletRequest request,
			HttpServletResponse reponse) {
		return defaultView();
	}

	private ModelAndView defaultView() {
		return new ModelAndView("formView");
	}

}
