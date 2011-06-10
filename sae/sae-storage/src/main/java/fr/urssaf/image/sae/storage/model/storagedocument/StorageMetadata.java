package fr.urssaf.image.sae.storage.model.storagedocument;

/**
 * Classe concrète représentant la métadonnée</BR>
 * 
 * <li>
 * Attribut code : Le code de la métadonnée</li> <li>
 * Attribut value : La valeur de la métadonnée</li>
 */
public class StorageMetadata {

   private String code;

   private Object value;

   /**
    * Retourne le code de la métadonnée
    * 
    * @return Code de la métadonnée
    */
   public final String getCode() {
      return code;
   }

   /**
    * Initialise le code de la métadonnée
    * 
    * @param code
    *           Le code de la métadonnée
    */
   public final void setCode(final String code) {
      this.code = code;
   }

   /**
    * Retourne la valeur de la métadonnée
    * 
    * @return La valeur de la Métta donnée
    */
   public final Object getValue() {
      return value;
   }

   /**
    * Initialise la valeur de la métadonnée
    * 
    * @param value
    *           La valeur de la métadonnée
    */
   public final void setValue(final Object value) {
      this.value = value;
   }

   /**
    * Constructeur
    * 
    * @param code
    *           Le code de la Métadonnée
    * @param value
    *           La valeur de la Métadonnée
    */
   public StorageMetadata(final String code, final Object value) {
      this.code = code;
      this.value = value;
   }
}
