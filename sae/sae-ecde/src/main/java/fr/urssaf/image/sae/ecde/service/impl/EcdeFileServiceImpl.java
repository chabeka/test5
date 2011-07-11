package fr.urssaf.image.sae.ecde.service.impl;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;

/**
 * Service de manipulation des URL ECDE et des chemins de fichiers.
 * 
 * {@link EcdeFileService}
 * 
 */

@Service
public class EcdeFileServiceImpl implements EcdeFileService {

   /**
    * Recupération des Constantes
    */
   public static final String ECDE = "ecde";
   public static final String DOCUMENTS = "documents";
   public static final String EXPR_REG = "ecde://.*/.*/(19|20)[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])/.*/documents/.+";
   
   
   /**
    * LOGGER
    */
   public static final Logger LOG = Logger.getLogger(EcdeFileServiceImpl.class);
   
   @Autowired
   private MessageSource messageSource;
   
   /**
    * Implémentation de la méthode convertFileToURI
    * 
    * @param ecdeFile
    *           fichier ecde
    * @param sources
    *           liste des ecdes
    *           
    * @throws EcdeBadFileException Mauvais chemin de fichier
    * 
    * @return URI uri converti
    * 
    * 
    */
   @Override
   public final URI convertFileToURI(File ecdeFile, EcdeSource... sources)
         throws EcdeBadFileException {
      
      // debutFichier recuperer a partir de ecdeFile
      String debutFichier = ""; 
      // boolean pour signaler que le debut du fichier est bien trouvé dans sources
      boolean trouve = false;
      // curseur afin de determiner le debut du fichier afin de recup ce qui nous interesse
      int curseur = -1;
      // fin fichier
      String finFichier = "";
      // recupération host
      String host = "";
      //valeur retournée
      URI uriRetournee = null;
      
      // parcours pour recuperer le debut du fichier
      String str[] = ecdeFile.getPath().split("\\\\");
      for (String variable : str) {
         if ( !"".equals(variable) ) {
            if (curseur <=1) {
               debutFichier = debutFichier.concat("/").concat(variable);
            } 
            else {
               finFichier = finFichier.concat("/").concat(variable);
            }
         } 
         curseur ++;
      }
      debutFichier = debutFichier.concat("/");
      File debutFichierFile = new File(debutFichier);
      
      // Une fois le debut du fichier recuperer on le compare a sources pour recuperer le host correspondant 
      for (EcdeSource ecdeSource : sources) {
         if ( debutFichierFile.getPath().equals(ecdeSource.getBasePath().toString()) ) {
            //concordance entre uri et ecdesource donné en paramètre
            host = ecdeSource.getHost().toString();
            trouve = true;
         }
      }
      
      // levée d'exception car aucune correspondance
      if ( !trouve ){
         throw new EcdeBadFileException(recupererMessage("ecdeBadFileException.message", ecdeFile));
      }
      
      // Construire le chemin absolu du fichier
      try {
         uriRetournee = new URI("ecde", host, finFichier, null);
      } catch (URISyntaxException e) {
         LOG.debug(e.getMessage());
      }
      return uriRetournee;
   }

   /**
    * Implémentation de la méthode convertURIToFile
    * 
    * @param ecdeURL
    *           url a convertir
    * @param sources
    *           liste des ecdes
    *           
    * @throws EcdeBadURLException mauvaise url 
    * @throws EcdeBadURLFormatException mauvais format d'url
    * 
    * @return File file converti
    * 
    * 
    * */
   @Override
   public final File convertURIToFile(URI ecdeURL, EcdeSource... sources)
         throws EcdeBadURLException, EcdeBadURLFormatException {

      // basePath recuperer a partir de ecdeSource
      String basePath = "";
      
      // boolean pour signaler que authority de l'uri bien trouvé dans sources
      boolean trouve = false;

      // Il faut commencer par vérifier que le ecdeURL respecte le format URL ECDE
      // ecde://ecde.cer69.recouv/numeroCS/dateTraitement/idTraitement/documents/nom_du_fichier
      if ( ! ecdeURL.toString().matches(EXPR_REG) ) {
         throw new EcdeBadURLFormatException(recupererMessage("ecdeBadUrlFormatException.message", ecdeURL));
      }
      
      // il faut maintenant venir parcourir la liste sources afin de recuperer l'ECDE correspondant
      // Parcours donc de la liste sources
      for (EcdeSource ecdeSource : sources) {
         if ( ecdeURL.getAuthority().equals(ecdeSource.getHost()) ) {
             //concordance entre uri et ecdesource donné en paramètre
            basePath = ecdeSource.getBasePath().toString();
            trouve = true;
         }
      }
      
      // levée d'exception car uri non trouve dans sources
      if ( !trouve ){
         throw new EcdeBadURLException(recupererMessage("ecdeBadUrlException.message", ecdeURL));
      }

      // Construire le chemin absolu du fichier
      return new File(basePath + ecdeURL.getPath());
      
   }
   
   // recupere les messages d erreur en affichant aussi l'url en question
   private String recupererMessage(String message, URI ecdeURL) {
      Object[] param = new Object[] {ecdeURL};
      return messageSource.getMessage(message, param, Locale.FRENCH);
   }
   
   // recupere les messages d erreur en affichant aussi le fichier en question
   private String recupererMessage(String message, File ecdeFile) {
      Object[] param = new Object[] {ecdeFile};
      return messageSource.getMessage(message, param, Locale.FRENCH);
   }
 

}
