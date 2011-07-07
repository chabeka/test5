package fr.urssaf.image.sae.ecde.service.utils;


/**
 * Classe regroupant les principales constantes
 * du projet SAE ECDE
 * 
 *
 */
public final class ConstantesECDE {
   
   /**
    * Constructeur par default
    * 
    */
   private ConstantesECDE(){
      
   }
   
   /**
    * constante ECDE pour le scheme d'une URL
    */
   public static final String ECDE = "ecde";
   
   /**
    * constante documents utilisé pour URL
    */
   public static final String DOCUMENTS = "documents";
   
   /**
    * constante pour le respect du format de l'url d'un ECDE
    */
   public static final String EXPR_REG = "ecde://.*/.*/(19|20)[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])/.*/documents/.+";
   
   
  
   
}
