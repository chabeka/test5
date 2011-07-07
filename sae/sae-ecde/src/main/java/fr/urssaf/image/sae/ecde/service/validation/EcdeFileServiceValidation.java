package fr.urssaf.image.sae.ecde.service.validation;

import java.io.File;
import java.net.URI;
import java.util.Locale;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;

/**
 * Classe EcdeFileServiceValidation
 * 
 * Classe de validation des arguments en entrée des implémentations du service
 * EcdeFileServiceValidation *
 * 
 */

@Aspect
public class EcdeFileServiceValidation {

   private static final String NONRENSEIGNE = "java.lang.ecdeFileNonRenseigne.IllegalArgumentException";
   
   private static final String PARAMCONVERTFILE = "execution(* fr.urssaf.image.sae.ecde.service.EcdeFileService.convertFileToURI(*,*))" +
   		                                         "&& args(ecdeFile,sources)";
   
   private static final String PARAMCONVERTURI = "execution(* fr.urssaf.image.sae.ecde.service.EcdeFileService.convertURIToFile(*,*))" +
                                           "&& args(ecdeURL,sources)";
   
   
   // Recupération du contexte pour les fichiers properties
   private final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-sae-ecde.xml");
   private final MessageSource messageSource = (MessageSource) context.getBean("messageSource");

   
   /**
    * Methode permettant de venir verifier si les paramétres d'entree de la methode convertFileToURI de l'interface
    * service.EcdeFileService sont bien correct.
    * 
    * @param ecdeFile fichier a convertir
    * @param sources liste des ecde
    */
   @Before(PARAMCONVERTFILE)
   public final void convertFileToURI(File ecdeFile, EcdeSource... sources) {
      
      // curseur pour parcourir la liste ecdeSource afin de recuperer l'index
      int curseur = 0;
      
      if(ecdeFile.isFile() || ecdeFile.exists()) {
         String message = messageSource.getMessage("java.lang.ecdeFile.IllegalArgumentException", null, Locale.FRENCH);
         throw new IllegalArgumentException(message);
      }
      
      if(sources == null) {
         String message = messageSource.getMessage("java.lang.ecdeFileNotExist.IllegalArgumentException", null, Locale.FRENCH);
         throw new IllegalArgumentException(message);
      }
      
      for(EcdeSource variable : sources){
        // Si l'attribut Host de l'ECDE n'est pas renseigné
        if (  variable.getHost() == null || "".equals(variable.getHost()) 
              ||
              variable.getBasePath().exists() || variable.getBasePath() == null) {
           
           throw new IllegalArgumentException(recupMessageNonRenseigneException("Host", curseur));
        }
        // Si l'attribut basePath de l'ECDE n'est pas renseigné
        if (  variable.getBasePath().exists() || variable.getBasePath() == null  ) {
           
           throw new IllegalArgumentException(recupMessageNonRenseigneException("Base Path", curseur));
        }
        curseur ++;
      }
      
   }
   
   //recuper les message
   private String recupMessageNonRenseigneException(String valeur, int curseur) {
      return messageSource.getMessage(NONRENSEIGNE, new Object[] {valeur, curseur}, Locale.FRENCH);
   }
   
   
   /**
    * Methode permettant de venir verifier si les paramétres d'entree de la methode convertURIToFile de l'interface
    * service.EcdeFileService sont bien correct.
    * 
    * @param ecdeURL url a convertir
    * @param sources liste des ecde
    */
   @Before(PARAMCONVERTURI)
   public final void convertURIToFile(URI ecdeURL, EcdeSource... sources) {
      
      // curseur pour parcourir la liste ecdeSource afin de recuperer l'index
      int curseur = 0;
      
      if(ecdeURL == null || "".equals(ecdeURL.toString())) {
         String message = messageSource.getMessage("java.lang.ecdeUrl.IllegalArgumentException", null, Locale.FRENCH);
         throw new IllegalArgumentException(message);
      }
      
      if(sources == null) {
         String message = messageSource.getMessage("java.lang.ecdeFileNotExist.IllegalArgumentException", null, Locale.FRENCH);
         throw new IllegalArgumentException(message);
      }
      
      for(EcdeSource variable : sources){
        curseur ++;
        // Si l'attribut Host de l'ECDE n'est pas renseigné
        if (  variable.getHost() == null || "".equals(variable.getHost())  ) {
           throw new IllegalArgumentException(recupMessageNonRenseigneException("Host", curseur));
        }
        // Si l'attribut basePath de l'ECDE n'est pas renseigné
        if (  variable.getBasePath().exists() || variable.getBasePath() == null  ) {
           throw new IllegalArgumentException(recupMessageNonRenseigneException("Base Path", curseur));
        }
      }
      
   }

}
