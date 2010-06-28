package fr.urssaf.image.commons.controller.spring3.exemple.formulaire;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import fr.urssaf.image.commons.controller.spring3.exemple.modele.Document;

public class TableFormulaire {

	private String test;

	private List<Document> documents;

	public void setTest(String test) {
		this.test = test;
	}

	public String getTest() {
		return test;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void initDocuments(List<Document> documents) {
		this.documents = documents;
		interneFormulaire.clear();

		for (Document document : documents) {
			
			FormFormulaire formulaire = new FormFormulaire();
			interneFormulaire.put(document.getId(), formulaire);
			formulaire.init(document);
			
		}
	}

	public Map<Integer, FormFormulaire> interneFormulaire = new HashMap<Integer, FormFormulaire>();

	@Valid
	public Map<Integer, FormFormulaire> getInterneFormulaire() {
		return this.interneFormulaire;
	}

	// @Rule(exception = "rule.validDate")
	// public Map<String, RuleException> validDate() {
	//
	// Map<String, RuleException> ruleExceptions = new HashMap<String,
	// RuleException>();
	// for (Integer id : this.closeDates.keySet()) {
	//
	// Date closeDate = this.closeDates.get(id);
	// Date openDate = this.openDates.get(id);
	// AbstractRuleForm dateRule = new RuleFormUtil.DateRule(closeDate,
	// openDate);
	//
	// ruleExceptions.put(Integer.toString(id), dateRule
	// .getRuleException());
	//
	// }
	//
	// return ruleExceptions;
	//
	// }

}
