package fr.urssaf.image.commons.controller.spring.exemple.formulaire;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import fr.urssaf.image.commons.controller.spring.exemple.annotation.ValidRule;
import fr.urssaf.image.commons.controller.spring.exemple.modele.Document;
import fr.urssaf.image.commons.controller.spring.exemple.modele.Etat;
import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.AbstractRuleForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotEmpty;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotNull;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Past;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Range;

public class TableFormulaire extends MyFormulaire {

	private List<Document> documents;

	private Map<Integer, String> titres = new HashMap<Integer, String>();

	private Map<Integer, Date> openDates = new HashMap<Integer, Date>();

	private Map<Integer, Date> closeDates = new HashMap<Integer, Date>();

	private Map<Integer, Etat> etats = new HashMap<Integer, Etat>();

	private Map<Integer, Integer> levels = new HashMap<Integer, Integer>();

	private Map<Integer, Boolean> flags = new HashMap<Integer, Boolean>();

	private Map<Integer, Set<Etat>> etatss = new HashMap<Integer, Set<Etat>>();

	private Map<Integer, String> comments = new HashMap<Integer, String>();

	public List<Document> getDocuments() {
		return documents;
	}

	public void initDocuments(List<Document> documents) {
		this.documents = documents;

		// initialisation des saisies
		this.titres.clear();
		this.openDates.clear();
		this.closeDates.clear();
		this.etats.clear();
		this.levels.clear();
		this.flags.clear();
		this.etatss.clear();
		this.comments.clear();
		for (Document document : documents) {
			this.titres.put(document.getId(), document.getTitre());
			this.openDates.put(document.getId(), document.getOpenDate());
			this.closeDates.put(document.getId(), document.getCloseDate());
			this.etats.put(document.getId(), document.getEtat());
			this.levels.put(document.getId(), document.getLevel());
			this.flags.put(document.getId(), document.getFlag());
			this.etatss.put(document.getId(), document.getEtats());
			this.comments.put(document.getId(), document.getComment());
		}
		this.getException().clearException();

	}

	@NotEmpty
	public Map<Integer, String> getTitres() {
		return titres;
	}

	public void setTitres(Map<Integer, String> titres) {
		this.titres = titres;
	}

	@NotNull
	@Past
	@ValidRule(rules = { "validDate" })
	public Map<Integer, Date> getOpenDates() {
		return openDates;
	}

	public void setOpenDates(Map<Integer, Date> openDates) {
		this.openDates = openDates;
	}

	@NotNull
	@Past
	@ValidRule(rules = { "validDate" })
	public Map<Integer, Date> getCloseDates() {
		return closeDates;
	}

	public void setCloseDates(Map<Integer, Date> closeDates) {
		this.closeDates = closeDates;
	}

	public Map<Integer, Etat> getEtats() {
		return etats;
	}

	public void setEtats(Map<Integer, Etat> etats) {
		this.etats = etats;
	}

	@NotNull
	@Range(min = 1, max = 3, format = "#")
	public Map<Integer, Integer> getLevels() {
		return levels;
	}

	public void setLevels(Map<Integer, Integer> levels) {
		this.levels = levels;
	}

	public Map<Integer, Boolean> getFlags() {
		return flags;
	}

	public void setFlags(Map<Integer, Boolean> flags) {
		this.flags = flags;
	}

	public void setEtatss(Map<Integer, Set<Etat>> etatss) {
		this.etatss = etatss;
	}

	public Map<Integer, Set<Etat>> getEtatss() {
		return etatss;
	}

	@NotEmpty
	public Map<Integer, String> getComments() {
		return comments;
	}

	public void setComments(Map<Integer, String> comments) {
		this.comments = comments;
	}

	@Rule(exception = "rule.validDate")
	public Map<String, RuleException> validDate() {

		Map<String, RuleException> ruleExceptions = new HashMap<String, RuleException>();
		for (Integer id : this.closeDates.keySet()) {

			Date closeDate = this.closeDates.get(id);
			Date openDate = this.openDates.get(id);
			AbstractRuleForm dateRule = new RuleFormUtil.DateRule(closeDate,
					openDate);

			ruleExceptions.put(Integer.toString(id), dateRule
					.getRuleException());

		}

		return ruleExceptions;

	}

}
