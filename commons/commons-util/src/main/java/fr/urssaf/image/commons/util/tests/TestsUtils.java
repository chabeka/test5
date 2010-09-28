package fr.urssaf.image.commons.util.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;

import fr.urssaf.image.commons.util.exceptions.TestConstructeurPriveException;

/**
 * Fonctions utilitaires pour les tests unitaires
 *
 */
public final class TestsUtils {

   
   /**
    * Constructeur privé, pour empêcher l'instantiation de la classe
    */
   private TestsUtils() {
      
   }
   
   
   /**
    * Renvoie un nom de fichier unique <br>
    * La construction est la suivante : date/heure + nombre aléatoire<br>
    * <br>
    * Il existe également une fonction de l'API Java qui permet de <b><u>CREER</u></b>
    * un fichier temporaire : {@link java.io.File#createTempFile(String, String)}
    * 
    * @return le nom de fichier unique
    * @see TestsUtils#getTemporaryFileName(String)
    * @see TestsUtils#getTemporaryFileName(String,String)
    */
   public static String getTemporaryFileName()
   {
      return getTemporaryFileName(null,null);
   }
   
   
   /**
    * Renvoie un nom de fichier unique <br>
    * La construction est la suivante : prefixe + date/heure + nombre aléatoire<br>
    * <br>
    * Il existe également une fonction de l'API Java qui permet de <b><u>CREER</u></b>
    * un fichier temporaire : {@link java.io.File#createTempFile(String, String)}
    * 
    * @param prefixe un préfixe éventuel (ou null)
    * @return le nom de fichier unique
    * @see TestsUtils#getTemporaryFileName
    * @see TestsUtils#getTemporaryFileName(String,String)
    */
   public static String getTemporaryFileName(String prefixe)
   {
      return getTemporaryFileName(prefixe,null);
   }
   
   
   /**
    * Renvoie un nom de fichier unique <br>
    * La construction est la suivante : prefixe + date/heure + nombre aléatoire + suffixe<br>
    * <br>
    * Il existe également une fonction de l'API Java qui permet de <b><u>CREER</u></b>
    * un fichier temporaire : {@link java.io.File#createTempFile(String, String)}
    * 
    * @param prefixe un préfixe éventuel (ou null)
    * @param suffixe un suffixe éventuel (ou null)
    * @return le nom de fichier unique
    * @see TestsUtils#getTemporaryFileName
    * @see TestsUtils#getTemporaryFileName(String)
    */
   public static String getTemporaryFileName(
         String prefixe,
         String suffixe)
   {
      
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
   
   
   /**
    * Teste le constructeur privé sans arguments de la classe passée en paramètre, 
    * afin de dépolluer le code coverage des constructeurs privés non testés<br>
    * <br>
    * Exemple d'utilisation :<br>
    * <pre>
    *    Boolean result = TestsUtils.testConstructeurPriveSansArgument(LaClasseATester.class);
    *    assertTrue("Le constructeur privé n'a pas été trouvé",result);
    * </pre>
    * 
    * @param classe la classe dont on veut tester le constructeur privé sans argument
    * @return true si un constructeur privé sans argument a été trouvé et testé, 
    *         false si aucun constructeur privé sans argument n'a été trouvé 
    * @throws TestConstructeurPriveException en cas de problème lors du test 
    */
   @SuppressWarnings("PMD.JUnit4TestShouldUseTestAnnotation")
   public static Boolean testConstructeurPriveSansArgument(final Class<?> classe) throws TestConstructeurPriveException { 
      Boolean result = false;
      final Constructor<?>[] constructeurs = classe.getDeclaredConstructors();
      try {
         for(final Constructor<?> constructeur : constructeurs) {
            if ((
                  Modifier.isPrivate(constructeur.getModifiers())) && // constructeur privé 
                  (constructeur.getParameterTypes().length==0))       // constructeur sans argument
            {
               constructeur.setAccessible(true); 
               final Object objet = constructeur.newInstance((Object[])null);
               Assert.assertNotNull(objet);
               result = true;
               break;
            }
         }
      }
      catch (Exception ex) {
         throw new TestConstructeurPriveException(ex);
      }
      return result;
   }
   
}
