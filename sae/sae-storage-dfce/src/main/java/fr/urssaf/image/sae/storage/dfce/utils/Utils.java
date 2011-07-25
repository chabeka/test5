package fr.urssaf.image.sae.storage.dfce.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import fr.urssaf.image.sae.storage.dfce.contants.Constants;

/**
 * Cette classe contient des méthodes utilitaires
 * 
 * @author akenore
 * 
 */
public final class Utils {
   /**
    * Simplifie l'écriture des boucles foreach quand l'argument peut être
    * {@code null}.
    * 
    * @param <T>
    *           le type des éléments
    * @param anIterable
    *           les éléments à parcourir
    * @return les éléments, ou une collection vide si l'argument était null
    */
   @SuppressWarnings("PMD.OnlyOneReturn")
   public static <T> Iterable<T> nullSafeIterable(final Iterable<T> anIterable) {
      if (anIterable == null) {
         return Collections.emptyList();
      } else {
         return anIterable;
      }
   }

   /**
    * Simplifie l'écriture des map
    * 
    * @param map
    *           le type des éléments
    * @param <K>
    *           : type
    * @param <V>
    *           : type
    * @return les éléments, ou une map vide si l'argument était null
    */
   @SuppressWarnings("PMD.OnlyOneReturn")
   public static <K, V> Map<K, V> nullSafeMap(final Map<K, V> map) {
      if (map == null) {
         return Collections.emptyMap();
      } else {
         return map;
      }
   }

   /** Cette classe n'est pas faite pour être instanciée. */
   private Utils() {
      assert false;
   }

   /**
    * Convertie une chaîne en Date
    * 
    * @param date
    *           : La chaîne à convertir.
    * @return une date à partir d'une chaîne.
    * @throws ParseException
    *            Exception lorsque le parsing de la chaîne ne se déroule pas
    *            bien.
    */
   public static Date formatStringToDate(final String date)
         throws ParseException {
      Date newDate = new Date();
      if (date != null) {
         SimpleDateFormat formatter = new SimpleDateFormat(
               Constants.DATE_PATTERN_FR, Constants.DEFAULT_LOCAL);
         formatter.setLenient(false);
         newDate = formatter.parse(date);
         if (formatter.parse(date, new ParsePosition(0)) == null) {
            formatter = new SimpleDateFormat(Constants.DATE_PATTERN_AN,
                  Constants.DEFAULT_LOCAL);
            newDate = formatter.parse(date);
         }
      }
      return newDate;
   }

   /**
    * Convertit une date en chaîne
    * 
    * @param date
    *           : La date à convertir
    * @return chaîne à partir d'une date
    * @throws ParseException
    *            Exception lorsque le parsing de la chaîne ne se déroule pas
    *            bien.
    */
   public static String formatDateToString(final Date date)
         throws ParseException {
      String newDate = Constants.BLANK;
      if (date != null) {
         final SimpleDateFormat formatter = new SimpleDateFormat(
               Constants.DATE_PATTERN_FR, Constants.DEFAULT_LOCAL);
         newDate = formatter.format(date);
      }
      return newDate;
   }
}
