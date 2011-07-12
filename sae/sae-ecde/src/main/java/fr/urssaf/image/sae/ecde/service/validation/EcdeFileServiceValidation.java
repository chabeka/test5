package fr.urssaf.image.sae.ecde.service.validation;

import java.io.File;
import java.net.URI;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
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
   
   private static final String ECDECLASS = "fr.urssaf.image.sae.ecde.service.EcdeFileService.";

   private static final String NONRENSEIGNE = "ecdeFileAttributNonRenseigne";
   
   private static final String PARAMCONVERTFILE = "execution(* "+ECDECLASS+"convertFileToURI(*,*))" +
   		                                         "&& args(ecdeFile,sources)";
   
   private static final String PARAMCONVERTURI = "execution(java.io.File "+ECDECLASS+"convertURIToFile(*,*))" +
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
      if(ecdeFile == null || ecdeFile.getPath().lastIndexOf("..") != -1) { // verifier que ecdeFile ne contient pas de ../
         throw new IllegalArgumentException(recupererMessage("ecdeFile.nonRenseigne"));
      }
      if(sources == null || org.apache.commons.lang.ArrayUtils.isEmpty(sources)) {
         throw new IllegalArgumentException(recupererMessage("ecdeFileNotExist"));
      }
      for(EcdeSource variable : sources){
        verifierEcdeSource(variable, curseur);
        curseur ++;
      }
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
      if(ecdeURL == null || ecdeURL.getPath().lastIndexOf("..") != -1) { // verifier que ecdeURL ne contient pas de ../
         throw new IllegalArgumentException(recupererMessage("ecdeUrl.nonRenseigne"));
      }
      if(sources == null || org.apache.commons.lang.ArrayUtils.isEmpty(sources)) {
         throw new IllegalArgumentException(recupererMessage("ecdeFileNotExist"));
      }
      
      for(EcdeSource variable : sources){
        verifierEcdeSource(variable, curseur);
        curseur ++;
      }
   }
   
   // verifier les parametres de EcdeSources, renvoie une exception 
   private void verifierEcdeSource(EcdeSource variable, int curseur){
      // Si l'attribut Host de l'ECDE n'est pas renseigné
      if (  StringUtils.isBlank(variable.getHost())  ) {
         throw new IllegalArgumentException(recupMessageNonRenseigneException("Host", curseur));
      }
      // Si l'attribut basePath de l'ECDE n'est pas renseigné
      if ( variable.getBasePath() == null || StringUtils.isBlank(variable.getBasePath().getAbsolutePath())  ) {
         throw new IllegalArgumentException(recupMessageNonRenseigneException("Base Path", curseur));
      }
   }
   
   // recupere les messages d erreur
   private String recupererMessage(String message) {
      return messageSource.getMessage(message, null, Locale.FRENCH);
   }
   
   //recupere les messages avec attribut non renseigne
   private String recupMessageNonRenseigneException(String valeur, int curseur) {
      return messageSource.getMessage(NONRENSEIGNE, new Object[] {valeur, curseur}, Locale.FRENCH);
   }

}
