package fr.urssaf.image.commons.controller.spring3.jndi.exemple.jndi;

/**
 * Bean permettant d'insérer TOUS les paramètres JNDI
 */
public class MiseEnPlaceJndi {

   /**
    * Constructeur par défaut
    */
   public MiseEnPlaceJndi()
   {
      JndiSupport.insereTous();
   }
   
}
