package fr.urssaf.image.sae.storage.dfce.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * Classe utilitaire de manipulation des heures
 * 
 * 
 */
public final class TimeUtils {

   private TimeUtils() {

   }

   private static final String TIME_FORMAT = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

   private static final Pattern TIME_PATTERN;

   static {

      TIME_PATTERN = Pattern.compile(TIME_FORMAT);

   }

   /**
    * 
    * Vérification que l'heure est au format HH:mm:ss<br>
    * <br>
    * Le code est récupéré de <a
    * href="http://www.mkyong.com/regular-expressions/how-to-validate
    * -time-in-24-hours-format-with-regular-expression/">lien</a>
    * 
    * 
    * @param time
    *           heure pour qui on veut vérifier le format
    * @return <code>true</code> si <code>time</code> est au format HH:mm:ss faux
    *         sinon
    */
   public static boolean isValidate(String time) {

      Assert.notNull(time);

      Matcher matcher = TIME_PATTERN.matcher(time);
      return matcher.matches();

   }

}
