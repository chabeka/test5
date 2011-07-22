/**
 * 
 */
package fr.urssaf.image.sae.dfce.admin.model;

import net.docubase.toolkit.model.base.Base.DocumentCreationDateConfiguration;
import net.docubase.toolkit.model.base.Base.DocumentOverlayFormConfiguration;
import net.docubase.toolkit.model.base.Base.DocumentOwnerType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Classe permettant de désérialiser les données de la base documentaire.<BR />
 * elle contient les s :
 * <ul>
 * <li>
 * baseId : Le nom de la base</li>
 * <li>baseDescription : Le descriptif de la base</li>
 * <li>documentCreationDateConfiguration : Permet dedDéclare une date de
 * création disponible mais optionnelle</li>
 * <li>
 * documentOverlayFormConfiguration :Permet ou bien rend obligatoire
 * l'utilisation de fond de page</li>
 * <li>documentOwnerDefaultType : Permet de préciser le type de propriétaire des
 * documents par défaut</li>
 * <li>
 * documentOwnerModify :indique si on peut modifier le propriétaire du document</li>
 * <li>documentTitleMask :Masque de titre. C'est encore maintenu en DFCE, même
 * si maintenant on peut remonter les valeurs de catégorie dans les listes de
 * solution sans avoir besoin de cet artifice.</li>
 * <li>documentTitleMaxSize : Le maximun de lettre contenu dans le titre du
 * document</li>
 * <li>documentTitleModify : Indique si le titre du document est modifiable</li>
 * <li>documentTitleSeparator : Le séparateur de titre</li>
 * <li>categories :Les catégories</li>
 * </ul>
 * 
 * @author akenore
 * 
 */
@XStreamAlias("base")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SaeBase {
	private String baseId;
	private String baseDescription;
	@SuppressWarnings("PMD.LongVariable")
	@XStreamAlias("documentCreationDateConfiguration")
	private String docCreationDateConf;
	@SuppressWarnings("PMD.LongVariable")
	@XStreamAlias("documentOverlayFormConfiguration")
	private String docOverConf;
	@SuppressWarnings("PMD.LongVariable")
	private String documentOwnerDefaultType;
	@SuppressWarnings("PMD.LongVariable")
	private boolean documentOwnerModify;
	private String documentTitleMask;
	@SuppressWarnings("PMD.LongVariable")
	private int documentTitleMaxSize;
	@SuppressWarnings("PMD.LongVariable")
	private boolean documentTitleModify;
	@SuppressWarnings("PMD.LongVariable")
	private String documentTitleSeparator;
	@XStreamAlias("categories")
	private SaeCategories saeCategories;

	/**
	 * @return La date de création
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final String getDocCreationDateConf() {
		return docCreationDateConf;
	}

	/**
	 * @return Le fond de page du document
	 */
	public final String getDocOverConf() {
		return docOverConf;
	}

	/**
	 * @param docCreationDateConf
	 *            : La date de creation du document
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDocCreationDateConf(final String docCreationDateConf) {
		this.docCreationDateConf = docCreationDateConf;
	}

	/**
	 * @return La date de creation du document
	 */
	public final String getDocumentOverlayFormConfiguration() {
		return docOverConf;
	}

	/**
	 * @param docOverConf
	 *            : Le fond de page du document
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDocOverConf(final String docOverConf) {
		this.docOverConf = docOverConf;
	}

	/**
	 * @return Le fond de page du document
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final String getDocumentOwnerDefaultType() {
		return documentOwnerDefaultType;
	}

	/**
	 * @param documentOwnerDefaultType
	 *            Le type de document
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDocumentOwnerDefaultType(
			final String documentOwnerDefaultType) {
		this.documentOwnerDefaultType = documentOwnerDefaultType;
	}

	/**
	 * @return Les categories
	 */
	public final SaeCategories getSaeCategories() {
		return saeCategories;
	}

	/**
	 * @param seaCategories
	 *            : Les categories
	 */
	public final void setSaeCategories(final SaeCategories seaCategories) {
		this.saeCategories = seaCategories;
	}

	/**
	 * 
	 * @return La date de création disponible mais optionnelle
	 */
	public final DocumentCreationDateConfiguration documentCreationDateConfiguration() {
		return DocumentCreationDateConfiguration.valueOf(docCreationDateConf
				.trim());
	}

	/**
	 * 
	 * @return La valeur qui rend obligatoire l'utilisation de fond de page
	 */
	public final DocumentOverlayFormConfiguration documentOverlayFormConfiguration() {
		return DocumentOverlayFormConfiguration.valueOf(docOverConf.trim());

	}

	/**
	 * 
	 * @return Le type de propriétaire des documents par défaut
	 */
	public final DocumentOwnerType documentOwnerType() {
		return DocumentOwnerType.valueOf(documentOwnerDefaultType.trim());
	}

	/**
	 * @param baseId
	 *            : Le libellé de la base
	 */
	public final void setBaseId(final String baseId) {
		this.baseId = baseId;
	}

	/**
	 * @return Le libellé de la base
	 */
	public final String getBaseId() {
		return baseId;
	}

	/**
	 * @param baseDescription
	 *            : Le descriptif de la base
	 */
	public final void setBaseDescription(final String baseDescription) {
		this.baseDescription = baseDescription;
	}

	/**
	 * @return Le descriptif de la base
	 */
	public final String getBaseDescription() {
		return baseDescription;
	}

	/**
	 * @param documentOwnerModify
	 *            : Indique si on peut modifier le propriétaire du document
	 * */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDocumentOwnerModify(final boolean documentOwnerModify) {
		this.documentOwnerModify = documentOwnerModify;
	}

	/**
	 * @return Le boolean qui indique si on peut modifier le propriétaire du
	 *         document
	 */
	public final boolean isDocumentOwnerModify() {
		return documentOwnerModify;
	}

	/**
	 * @param docTitleMask
	 *            : Le masque de titre
	 */
	public final void setDocumentTitleMask(final String docTitleMask) {
		this.documentTitleMask = docTitleMask;
	}

	/**
	 * @return Le masque de titre
	 */
	public final String getDocumentTitleMask() {
		return documentTitleMask;
	}

	/**
	 * @param docTitleMaxSize
	 *            : La taille maximum du titre
	 */
	public final void setDocumentTitleMaxSize(final int docTitleMaxSize) {
		this.documentTitleMaxSize = docTitleMaxSize;
	}

	/**
	 * @return La taille maximum du titre
	 */
	public final int getDocumentTitleMaxSize() {
		return documentTitleMaxSize;
	}

	/**
	 * @param docTitleModify
	 *            : Indique si le titre est modifiable
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDocumentTitleModify(final boolean docTitleModify) {
		this.documentTitleModify = docTitleModify;
	}

	/**
	 * @return Le boolean qui indique si le titre est modifiable
	 */
	public final boolean isDocumentTitleModify() {
		return documentTitleModify;
	}

	/**
	 * @param docTitleSeparator
	 *            : Le séparateur de titre
	 * 
	 */
	@SuppressWarnings("PMD.LongVariable")
	public final void setDocumentTitleSeparator(final String docTitleSeparator) {
		this.documentTitleSeparator = docTitleSeparator;
	}

	/**
	 * @return Le séparateur de titre
	 */
	public final String getDocumentTitleSeparator() {
		return documentTitleSeparator;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);
		toStringBuilder.append("BaseId", baseId);
		toStringBuilder.append("BaseDescription", baseDescription);
		toStringBuilder.append("DocumentCreationDateConfiguration",
				docCreationDateConf);
		toStringBuilder.append("DocumentOverlayFormConfiguration", docOverConf);
		toStringBuilder.append("documentOwnerDefaultType",
				documentOwnerDefaultType);
		toStringBuilder.append("documentOwnerModify ", documentOwnerModify);
		toStringBuilder.append("documentTitleModify", documentTitleModify);
		toStringBuilder.append("documentTitleMaxSize", documentTitleMaxSize);
		toStringBuilder
				.append("documentTitleSeparator", documentTitleSeparator);
		if (saeCategories != null) {
			toStringBuilder.append("saeCategories", saeCategories.toString());
		}
		return toStringBuilder.toString();
	}

}
