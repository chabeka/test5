package fr.urssaf.image.sae.integration.ihmweb.service.ecde;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import fr.urssaf.image.sae.integration.ihmweb.config.TestConfig;
import fr.urssaf.image.sae.integration.ihmweb.exception.IntegrationRuntimeException;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.resultats.ResultatsType;
import fr.urssaf.image.sae.integration.ihmweb.modele.somres.sommaire.SommaireType;
import fr.urssaf.image.sae.integration.ihmweb.utils.ChecksumUtils;
import fr.urssaf.image.sae.integration.ihmweb.utils.JAXBUtils;


/**
 * Service de manipulation de l'ECDE
 */
@Service
public class EcdeService {

   
   @Autowired
   private TestConfig testConfig;
   
   
   /**
    * Convertit une URL ECDE en un chemin de fichier<br>
    * La conversion est faite à LA RACHE
    * 
    * @param urlEcde l'URL ECDE
    * 
    * @return le chemin de fichier correspondant
    */
   public final String convertUrlEcdeToPath(String urlEcde) {
      
      if (StringUtils.isBlank(urlEcde)) {
         throw new IntegrationRuntimeException("Impossible de convertir l'URL ECDE en chemin de fichier : l'URL ECDE est vide");
      }

//      if (StringUtils.startsWithIgnoreCase(urlEcde, "ecde://ecde.cer69.recouv/")) {
//         
//         StringBuffer sBuf = new StringBuffer();
//         
//         sBuf.append("Y:\\");
//         sBuf.append(StringUtils.removeStart(urlEcde, "ecde://ecde.cer69.recouv/"));
//         
//         return sBuf.toString();
//         
//      } else {
//         throw new IntegrationRuntimeException("Impossible de convertir l'URL ECDE '" + urlEcde + "' en chemin de fichier.");
//      }
      
      
      String dnsEcde = testConfig.getDnsEcde();
      String debutUrlEcde = "ecde://" + dnsEcde + "/";
      
      if (StringUtils.startsWithIgnoreCase(urlEcde, debutUrlEcde)) {

         StringBuffer sBuf = new StringBuffer();

         sBuf.append("Y:\\");
         sBuf.append(StringUtils.removeStart(urlEcde,debutUrlEcde));

         return sBuf.toString();

      } else {
         throw new IntegrationRuntimeException(
               "Impossible de convertir l'URL ECDE '" + urlEcde
                     + "' en chemin de fichier.");
      }
 
      
   }
   
   
   /**
    * Calcul le SHA-1 du fichier de l'ECDE dont l'URL ECDE est passé en paramètre
    * 
    * @param urlEcde l'URL ECDE
    * 
    * @return le SHA-1 du fichier, sous la forme d'une chaîne de caractères hexa
    */
   public final String sha1(String urlEcde) {
      
      String cheminFichier = convertUrlEcdeToPath(urlEcde);
      
      return ChecksumUtils.sha1(cheminFichier);
      
   }
   
   
   /**
    * Charge un fichier resultats.xml et renvoie l'objet le réprésentant
    * 
    * @param cheminFichierResultats le chemin du fichier resultats.xml
    * @return l'objet représentant le fichier resultats.xml
    */
   public final ResultatsType chargeResultatsXml(String cheminFichierResultats) {
      
      try {
      
         ClassPathResource xsdResultats = new ClassPathResource("xsd_som_res/resultats.xsd");
         
         File xsdFile = xsdResultats.getFile();
      
         Class<ResultatsType> docClass = ResultatsType.class;
         
         FileInputStream input = new FileInputStream(cheminFichierResultats);
         
         return JAXBUtils.unmarshal(docClass, input, xsdFile, null);
      
      } catch (IOException e) {
         throw new IntegrationRuntimeException(e);
      } catch (JAXBException e) {
         throw new IntegrationRuntimeException(e);
      } catch (SAXException e) {
         throw new IntegrationRuntimeException(e);
      }
   }
   
   
   /**
    * Charge un fichier sommaire.xml dans un objet SommaireType
    * 
    * @param urlEcdeSommaire l'URL ECDE du sommaire
    * @return l'objet représentant le sommaire.xml
    */
   public final SommaireType chargeSommaireXml(
         String urlEcdeSommaire) {
      
      try {
         
         ClassPathResource xsdResultats = new ClassPathResource("xsd_som_res/sommaire.xsd");
         
         File xsdFile = xsdResultats.getFile();
      
         Class<SommaireType> docClass = SommaireType.class;
         
         String cheminFichierSommaire = convertUrlEcdeToPath(urlEcdeSommaire);
         
         FileInputStream input = new FileInputStream(cheminFichierSommaire);
         
         return JAXBUtils.unmarshal(docClass, input, xsdFile, null);
      
      } catch (IOException e) {
         throw new IntegrationRuntimeException(e);
      } catch (JAXBException e) {
         throw new IntegrationRuntimeException(e);
      } catch (SAXException e) {
         throw new IntegrationRuntimeException(e);
      }
      
   }
   
}
