package fr.urssaf.image.sae.ecde.exception;

import org.apache.log4j.Logger;


/**
 * 
 * Exception levée lorsqu'un chemin de fichier d'un ECDE n'appartient à aucun ECDE connu
 * 
 * @see EcdeGeneralException
 * */

public class EcdeBadFileException extends EcdeGeneralException {

   private static final long serialVersionUID = 1L;
   
   //logger
   public static final Logger LOG = Logger.getLogger(EcdeBadFileException.class);
   
   /**
    * Constructor
    * 
    * @param message cause
    */
   public EcdeBadFileException(String message) {
      super(message);
   }

}
