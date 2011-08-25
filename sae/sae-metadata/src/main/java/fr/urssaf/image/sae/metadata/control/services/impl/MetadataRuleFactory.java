package fr.urssaf.image.sae.metadata.control.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.metadata.rules.MetadataValueIsRequiredRule;
import fr.urssaf.image.sae.metadata.rules.MetadataIsArchivableRule;
import fr.urssaf.image.sae.metadata.rules.MetadataIsConsultableRule;
import fr.urssaf.image.sae.metadata.rules.MetadataIsSearchableRule;
import fr.urssaf.image.sae.metadata.rules.UntypedMetadataExistingRule;
import fr.urssaf.image.sae.metadata.rules.UntypedMetadataValueLengthRule;
import fr.urssaf.image.sae.metadata.rules.UntypedMetadataValueTypeRule;

/**
 * classe qui fournit des instances de règles
 * 
 * @author projet
 * 
 */
@Component
@Qualifier("ruleFactory")
public class MetadataRuleFactory {
	@Autowired
	private MetadataValueIsRequiredRule requiredRule;
	
	@Autowired
	private MetadataIsArchivableRule archivableRule;
	@Autowired
	private MetadataIsSearchableRule searchableRule;
	@Autowired
	private MetadataIsConsultableRule consultableRule;
	@Autowired
	private UntypedMetadataValueTypeRule valueTypeRule;

	@Autowired
	private UntypedMetadataExistingRule existingRule;
	@Autowired
	private UntypedMetadataValueLengthRule valueLengthRule;

	/**
	 * @return Une instance de la règle {@link MetadataExistingRule}
	 */
	public final UntypedMetadataExistingRule getExistingRule() {
		return existingRule;
	}

	/**
	 * @param existingRule
	 *            : Une instance de la règle {@link UntypedMetadataExistingRule}
	 */
	public final void setExistingRule(
			final UntypedMetadataExistingRule existingRule) {
		this.existingRule = existingRule;
	}

	/**
	 * @return Une instance de la règle {@link UntypedMetadataExistingRule}
	 */
	public final MetadataIsArchivableRule getArchivableRule() {
		return archivableRule;
	}

	/**
	 * @param archivableRule
	 *            : Une instance de la règle {@link MetadataIsArchivableRule}
	 */
	public final void setArchivableRule(
			final MetadataIsArchivableRule archivableRule) {
		this.archivableRule = archivableRule;
	}

	/**
	 * @return Une instance de la règle {@link MetadataIsSearchableRule}
	 */
	public final MetadataIsSearchableRule getSearchableRule() {
		return searchableRule;
	}

	/**
	 * @param searchableRule
	 *            : Une instance de la règle {@link MetadataIsSearchableRule}
	 * 
	 */
	public final void setSearchableRule(
			final MetadataIsSearchableRule searchableRule) {
		this.searchableRule = searchableRule;
	}

	/**
	 * @return Une instance de la règle {@link MetadataIsConsultableRule}
	 */
	public final MetadataIsConsultableRule getConsultableRule() {
		return consultableRule;
	}

	/**
	 * @param consultableRule
	 *            : Une instance de la règle {@link MetadataIsConsultableRule}
	 * 
	 */
	public final void setConsultableRule(
			final MetadataIsConsultableRule consultableRule) {
		this.consultableRule = consultableRule;
	}

	/**
	 * @return Une instance de la règle {@link MetadataValueLengthRule}
	 */
	public final UntypedMetadataValueLengthRule getValueLengthRule() {
		return valueLengthRule;
	}

	/**
	 * @param valueLengthRule
	 *            : Une instance de la règle
	 *            {@link UntypedMetadataValueLengthRule}
	 * 
	 */
	public final void setValueLengthRule(
			final UntypedMetadataValueLengthRule valueLengthRule) {
		this.valueLengthRule = valueLengthRule;
	}

	/**
	 * @return Une instance de la règle {@link UntypedMetadataValueTypeRule}
	 */
	public final UntypedMetadataValueTypeRule getValueTypeRule() {
		return valueTypeRule;
	}

	/**
	 * @param uValueTypeRule
	 *            : Une instance de la règle
	 *            {@link UntypedMetadataValueTypeRule}
	 */
	public final void setuValueTypeRule(
			final UntypedMetadataValueTypeRule uValueTypeRule) {
		this.valueTypeRule = uValueTypeRule;
	}

	/**
	 * @param valueTypeRule
	 *            : Une instance de la règle
	 *            {@link UntypedMetadataValueTypeRule}.
	 */
	public final void setValueTypeRule(final
			UntypedMetadataValueTypeRule valueTypeRule) {
		this.valueTypeRule = valueTypeRule;
	}

	/**
	 * @param requiredRule Une instance de la règle
	 *            {@link MetadataValueIsRequiredRule}.
	 */
	public final void setRequiredRule(final MetadataValueIsRequiredRule requiredRule) {
		this.requiredRule = requiredRule;
	}

	/**
	 * @return Une instance de la règle
	 *            {@link MetadataValueIsRequiredRule}.
	 */
	public final MetadataValueIsRequiredRule getRequiredRule() {
		return requiredRule;
	}
}
