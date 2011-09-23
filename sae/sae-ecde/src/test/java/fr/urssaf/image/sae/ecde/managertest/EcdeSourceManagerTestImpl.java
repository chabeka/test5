/**
 * 
 */
package fr.urssaf.image.sae.ecde.managertest;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSources;

/**
 * Classe Test du Manager.
 * 
 */
public class EcdeSourceManagerTestImpl implements EcdeSourceManagerTest {

   /**
    * liste ecdeSources
    */
   private EcdeSources sources;
   
   
   // Recupération repertoire temp
   private static final String REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();
   // nom du répertoire creer dans le repertoire temp de l'os
   private static final String ECDE = getTemporaryFileName("ecde", null); 
   // declaration d'un répertoire dans le repertoire temp de l'os
   private static final String REPERTOIR = FilenameUtils.concat(REPERTORY, ECDE);
   
   private static final String ECDE_LYON = "ecde.cer69.recouv";
   private static final String ECDE_HOST = "ecde.hoth.recouv";
   
   private static final String FILE_LYON = "/ecde/ecde_lyon/";
   private static final String FILE_HOST = "/ecde/ecde_host/";
   
   private static final File LYON = new File(FILE_LYON);
   private static final File HOST = new File(FILE_HOST);
   /**
    * {@inheritDoc}
    */
   @Override
   public final EcdeSources load() throws EcdeBadFileException, IOException {

         // Désérialisation des objets EcdeSource via Xstream
         StaxDriver staxDriver = new StaxDriver();
         XStream xstream = new XStream(staxDriver);
         xstream.alias("source", EcdeSource.class);
         EcdeSource[] ecdes =  new EcdeSource[] {};
         xstream.alias("sources", ecdes.getClass());
         EcdeSources ecdeSources = new EcdeSources();
         
         
         File rep = new File(REPERTOIR);
         
         EcdeSource ecde1 = new EcdeSource(ECDE_HOST, HOST);
         EcdeSource ecde2 = new EcdeSource(ECDE_LYON, LYON);
         EcdeSource ecde3 = new EcdeSource("ecde.testunit.recouv", rep);
         
         EcdeSource[] source = new EcdeSource[]{ecde1, ecde2, ecde3};
         ecdeSources.setSources(source);
         
         // creation d'un repertoire dans le rep temp de l'os
         FileUtils.forceMkdir(rep);
         //nettoyage du repertoire present dans le rep temp de l'os
         FileUtils.cleanDirectory(rep);
         
         return ecdeSources;
   }
   
   /**
    * @return the sources
    */
   public final EcdeSources getSources() {
      return sources;
   }

   /**
    * @param sources the sources to set
    */
   public final void setSources(EcdeSources sources) {
      this.sources = sources;
   }
   
   
   private static String getTemporaryFileName(String prefixe,String suffixe) {
      
      // NB : Il n'est pas possible de tester unitairement l'intégralité du résultat de cette
      // méthode car elle contient un calcul de nombre aléatoire ainsi qu'une date
      // correspondant à "maintenant"
      
      // Création de l'objet résultat
      StringBuffer nomFicTemp = new StringBuffer();
      
      // 1ère partie du nom : le préfixe
      if (prefixe!=null) {
         nomFicTemp.append(prefixe);
      }
      
      // 2ème partie du nom : la date de maintenant, de l'année à la milli-secondes  
      final Date dMaintenant = new Date();
      final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS",Locale.FRENCH);
      nomFicTemp.append(dateFormat.format(dMaintenant));
      
      // 3ème partie du nom : un nombre aléatoire
      // L'algorithme utilisé est emprunté à java.io.File.createTempFile 
      final SecureRandom random = new SecureRandom();
      long nextLong = random.nextLong();
      if (nextLong == Long.MIN_VALUE) {
          nextLong = 0;      // corner case
      } else {
          nextLong = Math.abs(nextLong);
      }
      nomFicTemp.append('_');
      nomFicTemp.append(nextLong);
      
      // Dernière partie du nom : le suffixe
      if (suffixe!=null) {
         nomFicTemp.append(suffixe);
      }
      
      // Renvoie du résultat
      return nomFicTemp.toString();
      
   }
   
}
