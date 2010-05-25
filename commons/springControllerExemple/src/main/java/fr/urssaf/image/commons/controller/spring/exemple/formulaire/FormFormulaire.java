package fr.urssaf.image.commons.controller.spring.exemple.formulaire;

import java.util.Date;
import java.util.Set;


import fr.urssaf.image.commons.controller.spring.exemple.annotation.ValidRule;
import fr.urssaf.image.commons.controller.spring.exemple.modele.Etat;
import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Formulaire;
import fr.urssaf.image.commons.controller.spring.formulaire.support.annotation.Rule;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.AbstractRuleForm;
import fr.urssaf.image.commons.controller.spring.formulaire.support.rule.RuleException;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotEmpty;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.NotNull;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Past;
import fr.urssaf.image.commons.controller.spring.formulaire.validator.Range;
import fr.urssaf.image.commons.util.string.StringUtil;

public class FormFormulaire extends MyFormulaire {

	private String titre;

	private Date openDate;

	private Date closeDate;

	private Etat etat;

	private Integer level;

	private boolean flag;

	private Set<Etat> etats;

	public void init() {
		this.getException().clearException();
		this.titre = null;
		this.openDate = null;
		this.closeDate = null;
		this.etat = null;
		this.level = null;
		this.flag = false;
		this.etats = null;
		this.getInterneFormulaire().setComment(null);

	}

	@NotEmpty
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	@NotNull
	@Past
	@ValidRule(rules={"validDate"})
	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@NotNull
	@Past
	@ValidRule(rules={"validDate"})
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@NotNull
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public void setEtats(Set<Etat> etats) {
		this.etats = etats;
	}

	public Set<Etat> getEtats() {
		return etats;
	}

	@NotNull
	@Range(min = 1, max = 3, format = "#")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Rule(exception = "rule.validDate")
	public RuleException validDate() {
		AbstractRuleForm dateRule = new RuleFormUtil.DateRule(this.closeDate,
				this.openDate);

		return dateRule.getRuleException();

	}

	public InterneFormulaire interneFormulaire = new InterneFormulaire();

	@Formulaire
	public InterneFormulaire getInterneFormulaire() {
		return this.interneFormulaire;
	}

	public class InterneFormulaire extends MyFormulaire {

		private String comment;

		public void setComment(String comment) {
			this.comment = StringUtil.trim(comment);
		}

		@NotEmpty
		public String getComment() {
			return comment;
		}
	}

}
