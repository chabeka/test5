package fr.urssaf.image.commons.controller.spring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.form.ClassForm;
import fr.urssaf.image.commons.controller.spring.servlet.support.ExceptionController;
import fr.urssaf.image.commons.util.logger.Log;

public abstract class AbstractMyController<F extends MyFormulaire> implements
		Controller {

	private ClassForm<F> classForm;

	private String controller;

	protected static String exception = "exception";

	protected static String ruleException = "ruleException";

	protected static String formulaire = "formulaire";

	protected static String url = "controller";

	public static enum CONTROLLER {
		action("action"), reset("reset");

		CONTROLLER(String name) {
			this.name = name;
		}

		private String name;

		public String getName() {
			return this.name;
		}

	}

	private ActionController<F> actionController;

	protected static final Logger log = Logger
			.getLogger(AbstractMyController.class);

	private Class<? extends F> classe;

	private String nameFormulaire;

	public AbstractMyController(Class<? extends F> classe) {
		this.init(classe);
	}
	
	private void init(Class<? extends F> classe){
		
		this.classe = classe;
		this.classForm = new ClassForm<F>(classe);
		this.actionController = new ActionController<F>(this);
		this.nameFormulaire = this.getClass().getCanonicalName();
	}

	public String getController() {
		return this.controller;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse reponse) {

		if (this.controller == null) {
			this.controller = request.getServletPath().substring(1);
		}

		// traitement avant la récupération du formulaire
		this.preFormulaire(request, reponse);

		// récupération du formulaire
		F formulaire = this.getFormulaire(request, reponse);

		// traitement après la récupération du formulaire
		this.postFormulaire(request, reponse);

		// traitement des paramètres
		if (request.getParameterMap().size() > 0) {
			// remise à zero de certains champs du formulaire
			String[] reset = request.getParameterValues(CONTROLLER.reset.name);
			if (reset != null) {
				formulaire.reset(reset);
			}

			// remplissage du formulaire
			formulaire.populate(request.getParameterMap());
		}

		// traitement le remplissage du formulaire
		this.postPopulate(request, reponse);

		// retourne une vue
		try {
			return this.actionController.view(request, reponse);
		} finally {

			// paramètrage de la requête de retour
			ExceptionController exptController = new ExceptionController();
			request.setAttribute(AbstractMyController.ruleException,
					exptController.allRuleExceptions(formulaire));
			request.setAttribute(AbstractMyController.exception, exptController
					.getBeanException(formulaire));

			request.setAttribute(AbstractMyController.formulaire, formulaire);
			request.setAttribute(AbstractMyController.url, this.controller);

			// traitement après le retour de la vue
			this.postView(request, reponse);

		}
	}

	public final F getFormulaire(HttpServletRequest request,
			HttpServletResponse reponse) {

		// récupération du formulaire
		F formulaire = getFormulaire(request.getSession());
		if (formulaire == null) {
			formulaire = this.createFormulaire(request, reponse);
			formulaire.initClassForm(classForm);

			request.getSession().setAttribute(getNameFormulaire(), formulaire);

			// on ajoute les types à controler dans le formulaire
			completeTypeFactory(this.classForm, request);

		}

		return formulaire;

	}

	@SuppressWarnings("unchecked")
	private F getFormulaire(HttpSession session) {
		return (F) session.getAttribute(getNameFormulaire());
	}

	// methode pour récupérer un formulaire associé à un controleur
	protected F createFormulaire(HttpServletRequest request,
			HttpServletResponse reponse) {

		try {
			return classe.newInstance();
		} catch (InstantiationException e) {
			Log.throwException(log, e);
		} catch (IllegalAccessException e) {
			Log.throwException(log, e);
		}

		return null;
	}

	protected void setNameFormulaire(String nameFormulaire) {
		this.nameFormulaire = nameFormulaire;
	}

	public String getNameFormulaire() {
		return this.nameFormulaire;
	}

	protected void completeTypeFactory(ClassForm<F> classForm,
			HttpServletRequest request) {

	}

	protected void preFormulaire(HttpServletRequest request,
			HttpServletResponse reponse) {
	}

	protected void postFormulaire(HttpServletRequest request,
			HttpServletResponse reponse) {
	}

	protected void postPopulate(HttpServletRequest request,
			HttpServletResponse reponse) {
	}

	protected void postView(HttpServletRequest request,
			HttpServletResponse reponse) {
	}

	protected abstract ModelAndView getDefaultView(HttpServletRequest request,
			HttpServletResponse reponse);

}
