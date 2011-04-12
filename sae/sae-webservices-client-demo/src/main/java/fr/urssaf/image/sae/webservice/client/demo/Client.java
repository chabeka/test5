package fr.urssaf.image.sae.webservice.client.demo;

import org.apache.commons.lang.ArrayUtils;

import fr.urssaf.image.sae.webservice.client.demo.service.PingService;

/**
 * Principale classe executable de client demo<br>
 * 
 * 
 */
public final class Client {

   private Client() {

   }

   /**
    * Méthode executable<br>
    * Redirection vers les autres méthodes executables de client demo<br>
    * <br>
    * paramètres ordonnés:
    * <ul>
    * <li>arg[0]: action
    * <ul>
    * <li>ping</li>
    * </ul>
    * </li>
    * </ul>
    * 
    * 
    * @param args
    *           arguments obligatoires
    */
   public static void main(String[] args) {

      if (ArrayUtils.isEmpty(args)) {
         throw new IllegalArgumentException("action required");
      }

      String[] newArgs = (String[]) ArrayUtils.subarray(args, 1, args.length);

      if ("ping".equals(args[0])) {

         PingService.main(newArgs);

      } else {
         throw new IllegalArgumentException("Unknown action defined: "
               + args[0]);
      }

   }

}
