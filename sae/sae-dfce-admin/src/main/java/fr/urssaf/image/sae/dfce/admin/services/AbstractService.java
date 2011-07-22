package fr.urssaf.image.sae.dfce.admin.services;

import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;

/**
 * Cette classe contient les éléments communs des différentes services.
 * 
 * @author akenore
 * 
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractService {

	
	/**
	 * 
	 * @param baseId
	 *            : L'identifiant de la base.
	 * @return Une nouvelle instance de la  base sae.
	 */
	public final  Base getBaseDfce(final String baseId) {
		return ToolkitFactory.getInstance().createBase(baseId);
	}
}
