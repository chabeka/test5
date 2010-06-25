package fr.urssaf.image.commons.controller.spring3.exemple.controller.base;



public abstract class AbstractExempleController {

	//@Autowired
	//private MessageSource messageSource;

//	@SuppressWarnings("unchecked")
//	public ModelAndView populateField(HttpServletRequest request,
//			HttpServletResponse reponse) {
//		String field = request.getParameter("field");
//		MyFormulaire formulaire = this.getFormulaire(request, reponse);
//
//		Map<String, List<String>> beans = new HashMap<String, List<String>>();
//		beans.put(field, new ArrayList<String>());
//
//		if (!formulaire.isValid(field)) {
//
//			BeanException beanException = new BeanException(formulaire
//					.getException(field));
//
//			for (FormulaireException exception : beanException
//					.getFormulaireExceptions()) {
//
//				String message = messageSource.getMessage(exception.getCode(),
//						exception.getParameters().toArray(), request
//								.getLocale());
//				beans.get(field).add(message);
//
//			}
//
//		}
//
//		BeanFormulaire beanFormulaire = formulaire.getBeanFormulaire(field);
//
//		ValidRule validRule = (ValidRule) beanFormulaire
//				.getAnnotation(ValidRule.class);
//		if (validRule != null) {
//
//			for (String rule : validRule.rules()) {
//				Object ruleException = formulaire.validRule(rule);
//
//				if (ruleException == null
//						|| !Map.class
//								.isAssignableFrom(ruleException.getClass())) {
//					populateRule(request, formulaire, rule, beans);
//				}
//
//				else if (Map.class.isAssignableFrom(ruleException.getClass())) {
//
//					Map<String, RuleException> ruleExceptions = (Map<String, RuleException>) ruleException;
//					for (String ruleIndex : ruleExceptions.keySet()) {
//						populateRule(request, formulaire, UtilForm.getRule(
//								rule, ruleIndex), beans);
//					}
//				}
//
//			}
//
//		}
//
//		return new ModelAndView("ajax", beans);
//	}
//
//	private void populateRule(HttpServletRequest request,
//			MyFormulaire formulaire, String ruleName,
//			Map<String, List<String>> beans) {
//
//		beans.put(ruleName, new ArrayList<String>());
//		if (formulaire.getRuleExceptions(ruleName) != null) {
//			for (RuleFormulaireException exception : formulaire
//					.getRuleExceptions(ruleName)) {
//				String message = messageSource.getMessage(exception
//						.getException().getCode(), exception.getException()
//						.getParameters().toArray(), request.getLocale());
//
//				beans.get(ruleName).add(message);
//			}
//		}
//	}
}
