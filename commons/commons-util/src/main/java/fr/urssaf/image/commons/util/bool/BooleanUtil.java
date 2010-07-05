package fr.urssaf.image.commons.util.bool;


/**
 * Fonctions utilitaires pour la manipulation de booléens
 * 
 */
public final class BooleanUtil {

   
	private BooleanUtil() {

	}

	
	/**
	 * Renvoie la valeur primitive booléenne à partir d'un objet Boolean.
	 * @param bool l'objet Boolean
	 * @return la valeur primitive booléenne (false si bool vaut null)
	 */
	public static boolean getBool(Boolean bool) {
		boolean result;
	   if (bool==null)
	   {
	      result = false;
	   }
	   else
	   {
	      result = bool.booleanValue();
	   }
		return result;
	}
	
}
