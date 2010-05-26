package fr.urssaf.image.commons.controller.spring.exemple.controller.table;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


import fr.urssaf.image.commons.controller.spring.exemple.controller.base.AbstractExempleController;
import fr.urssaf.image.commons.controller.spring.exemple.controller.form.FormController;
import fr.urssaf.image.commons.controller.spring.exemple.formulaire.TableFormulaire;
import fr.urssaf.image.commons.controller.spring.exemple.service.DocumentService;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.Type;
import fr.urssaf.image.commons.controller.spring.formulaire.type.DateType;
import fr.urssaf.image.commons.controller.spring.servlet.support.annotation.Validation;

@Controller
public class TableController extends AbstractExempleController<TableFormulaire> {

	@Autowired
	private FormController formController;

	@Autowired
	private DocumentService documentService;

	public TableController() {
		super(TableFormulaire.class);
	}

	@Override
	protected ModelAndView getDefaultView(HttpServletRequest request,
			HttpServletResponse reponse) {

		if(this.getFormulaire(request, reponse).getDocuments() == null){
			return this.populate(request, reponse);
		}
		
		return this.defaultView();
	}

	public ModelAndView populate(HttpServletRequest request,
			HttpServletResponse reponse) {

		this.getFormulaire(request, reponse).initDocuments(
				documentService.allDocuments());

		return this.defaultView();
	}

	@Validation
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse reponse) {

		TableFormulaire formulaire = this.getFormulaire(request, reponse);

		documentService.update(formulaire);

		return populate(request,reponse);
	}

	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse reponse) {

		formController.getFormulaire(request, reponse).init();

		return new ModelAndView("formForward");
	}
	
	@Override
	protected void completeTypeFactory(ClassForm<TableFormulaire> classForm,
			HttpServletRequest request) {
		
		Type<Date> type = new Type<Date>("closeDates", new DateType("yyyy-MM-dd"));

		classForm.getTypeFactory().addTypeFormulaire(type);

	}

	private ModelAndView defaultView() {
		return new ModelAndView("tableView");
		
	
	}

}
