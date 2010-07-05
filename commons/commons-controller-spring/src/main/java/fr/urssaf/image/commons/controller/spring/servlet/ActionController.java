package fr.urssaf.image.commons.controller.spring.servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.servlet.AbstractMyController.CONTROLLER;
import fr.urssaf.image.commons.controller.spring.servlet.support.annotation.Validation;
import fr.urssaf.image.commons.util.logger.Log;

public class ActionController<F extends MyFormulaire> {

	private static final Logger log = Logger.getLogger(ActionController.class);

	public AbstractMyController<F> controller;

	private Validation validation;

	public ActionController(AbstractMyController<F> controller) {
		this.controller = controller;
		validation = this.controller.getClass().getAnnotation(Validation.class);
	}
	
	@SuppressWarnings("unchecked")
	protected ModelAndView view(HttpServletRequest request,
			HttpServletResponse reponse) {

		// choix de l'action
		String methode = request.getParameter(CONTROLLER.action.getName());
		if (methode != null) {
			return this.action(request, reponse, methode);
		} else {
			String ACTION_VIEW = CONTROLLER.action.getName() + ":";
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {

				String parameter = (String) parameters.nextElement();
				if (parameter.startsWith(ACTION_VIEW)) {

					String action = parameter.substring(ACTION_VIEW.length());
					return this.action(request, reponse,
							action);

				}
			}
		}

		// vue du formulaire: page par défaut du controleur
		return this.controller.getDefaultView(request, reponse);

	}

	private ModelAndView action(HttpServletRequest request,
			HttpServletResponse reponse, String action) {

		try {

			Method methode = this.controller.getClass().getMethod(
					action,
					new Class[] { HttpServletRequest.class,
							HttpServletResponse.class });

			Validation validation = this.getValidation(methode);

			if (validation != null
					&& !isValid(
							this.controller.getFormulaire(request, reponse),
							validation)) {

				if (StringUtils.isNotBlank(validation.action())) {
					return this.action(request, reponse, validation.action());
				}

				return this.controller.getDefaultView(request, reponse);

			}

			ModelAndView view = (ModelAndView) methode.invoke(this.controller,
					new Object[] { request, reponse });

			return view;

		} catch (SecurityException e) {
			Log.throwException(log, e);
		} catch (NoSuchMethodException e) {
			Log.exception(log, e);
			log.warn("l'action '" + action + "' n'est pas prise en compte");
			return this.controller.getDefaultView(request, reponse);
		} catch (IllegalArgumentException e) {
			Log.throwException(log, e);
		} catch (IllegalAccessException e) {
			Log.throwException(log, e);
		} catch (InvocationTargetException e) {
			Log.throwException(log, e.getTargetException());
		}

		return null;

	}

	private Validation getValidation(Method methode) {

		if (methode.getAnnotation(Validation.class) != null) {

			return methode.getAnnotation(Validation.class);

		}

		return this.validation;
	}

	private boolean isValid(F formulaire, Validation validation) {

		if (validation.fields().length > 0 || validation.rules().length > 0) {

			boolean isValid = true;

			for (String field : validation.fields()) {

				isValid = isValid & formulaire.isValid(field);
			}

			for (String rule : validation.rules()) {
				isValid = isValid & formulaire.isValidRule(rule);
			}

			return isValid;
		}

		return formulaire.isValid();
	}
}
