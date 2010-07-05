package fr.urssaf.image.commons.util.string;


/**
 * Fonctions de manipulation de chaînes de caractères 
 *
 */
public final class StringUtil {

   
	private StringUtil(){
		
	}
	
	
	/**
	 * Teste si une chaîne de caractères est vide ou non. Vide signifie null, vide, ou composée uniquement d'espaces
	 * @param value la chaîne de caractères à tester
	 * @return true si la chaîne de caractères est vide, false dans le cas contraire
	 */
	public static boolean isEmpty(String value)
   {
      boolean result;
      if (value==null)
      {
         result = true;
      }
      else
      {
         result = value.trim().length()==0;
      }
      return result;
   }
	
	
	/**
	 * Test si une chaîne de caractères n'est pas vide (vide signifie null, chaîne vide ou uniquement des espaces)
	 * @param value la chaîne de caractères à tester
	 * @return true si la chaîne n'est pas vide, false sinon
	 */
	public static boolean notEmpty(String value){
		return !StringUtil.isEmpty(value);
	}
	
			
	/**
	 * Fait un trim de la valeur passée en paramètre, en traitant le cas de la valeur null 
	 * @param value la chaîne de caractères qu'il faut "trimmer", ou null
	 * @return la chaîne de caractères "trimmée", ou null si value=null
	 */
	public static String trim(String value) {
		String result;
		if (value==null)
		{
		   result = null;
		}
		else
		{
		   result = value.trim(); 
		}
		return result;
	}
}
