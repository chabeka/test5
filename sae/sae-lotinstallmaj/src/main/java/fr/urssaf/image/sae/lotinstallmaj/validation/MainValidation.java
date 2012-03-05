package fr.urssaf.image.sae.lotinstallmaj.validation;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.urssaf.image.sae.lotinstallmaj.Main;
import fr.urssaf.image.sae.lotinstallmaj.exception.MajLotGeneralException;
import fr.urssaf.image.sae.lotinstallmaj.service.impl.MajLotServiceImpl;

/**
 * Classe de validation des arguments en entrée de la classe principale du JAR
 * executable
 * 
 */
@Aspect
public class MainValidation {

   // LOGGER
   private static final Logger LOG = LoggerFactory.getLogger(Main.class);

   private static final String MAIN_METHOD = "execution(void fr.urssaf.image.sae.lotinstallmaj.Main.main(*))"
         + "&& args(args)";

   /**
    * Methode permettant de venir verifier si les paramétres d'entree de la
    * methode main de classe principale MAIN sont bien corrects.
    * 
    * @param args
    *           chemin complet du fichier de configuration SAE
    *           operations a effectuée
    * @throws Exception 
    * 
    */
   @Before(MAIN_METHOD)
   public final void main(String[] args) throws Exception {

      if (args != null && args.length > 0 ) {
         String pathFile = args[0];

         // chemin du fichier de configuration
         checkPathConfig(args, pathFile);

         // nom de l'opération
         checkOperationName(args);
         
      } else {
         LOG
               .warn("Erreur : Il faut indiquer, en argument de la ligne de commande,"
                     + "le chemin complet du fichier de configuration du SAE.");

         throw new MajLotGeneralException(
               "Erreur: Argument de la ligne de commande mal renseigné ou null! Valeurs entrées : " + String.valueOf(args));
      }
   }

   /**
    * Vérification du chemin du fichier de configuration
    * 
    * @throws Exception
    */
   private void checkPathConfig(String[] args, String pathFile)
         throws MajLotGeneralException {

      File file = new File(pathFile);
      if (args.length == 0 || StringUtils.isBlank(pathFile) || !file.exists()) {

         LOG
               .warn("Erreur : Il faut indiquer, en argument de la ligne de commande,"
                     + "le chemin complet du fichier de configuration du SAE.");

         throw new MajLotGeneralException("Erreur : Il faut indiquer, en argument de la ligne de commande,"
                     + "le chemin complet du fichier de configuration du SAE.");
      }
   }
   
   /**
    * Vérification du nom de l'opération
    */
   private void checkOperationName(String[] args) throws MajLotGeneralException {
      
      if (args.length < 2 || StringUtils.isBlank(args[1])) {
         LOG
               .warn("Erreur : Il faut indiquer, en deuxieme argument de la ligne de commande,"
               + "le nom de l'opération à réaliser.");

         throw new MajLotGeneralException(
               "Erreur : Il faut indiquer, en deuxieme argument de la ligne de commande,"
               + "le nom de l'opération à réaliser.");
      }
      String operation = args[1];
      if (! MajLotServiceImpl.CODE_ACTIVITE.equalsIgnoreCase(operation)) {
         LOG
               .warn("Erreur : Opération inconnue : " + operation);

         throw new MajLotGeneralException(
               "Erreur : Opération inconnue : " + operation);
      }
      
      // TODO - Pour le moment l'opération durée de conservation demande délai cotisant n'est pas implémmentée
      // Attente du JIRA DFCE
      
   }
}
