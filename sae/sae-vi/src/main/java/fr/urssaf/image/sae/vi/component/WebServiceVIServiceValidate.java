package fr.urssaf.image.sae.vi.component;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import fr.urssaf.image.sae.saml.util.ListUtils;

/**
 * La classe implémenté en AOP permet de vérifier les arguments des méthodes
 * dans {@link fr.urssaf.image.sae.vi.service.WebServiceVIService}<br>
 * 
 * 
 */
@Aspect
public class WebServiceVIServiceValidate {

   private static final String SERVICE_PACKAGE = "fr.urssaf.image.sae.vi.service.WebServiceVIService";

   private static final String WRITE_METHODE = "execution(public * "
         + SERVICE_PACKAGE + ".creerVIpourServiceWeb(..))";

   private static final String ARG_EMPTY = "Le paramètre [${0}] n'est pas renseigné alors qu'il est obligatoire";

   private static final int INDEX_3 = 3;

   private static final int INDEX_4 = 4;

   private static final int INDEX_5 = 5;

   /**
    * Vérification des paramètres d'entrée de la méthode
    * {@link fr.urssaf.image.sae.vi.service.WebServiceVIService#creerVIpourServiceWeb}
    * <br>
    * <ul>
    * <li>pagm : doit avoir au moins un droit renseigné</li>
    * <li>issuer : doit être renseigné</li>
    * <li>keystore : doit être renseigné</li>
    * <li>alias : doit être renseigné</li>
    * <li>password: doit être renseigné</li>
    * </ul>
    * 
    * @param joinPoint
    *           point de jointure de la méthode
    */
   @Before(WRITE_METHODE)
   public final void creerVIpourServiceWeb(JoinPoint joinPoint) {

      // récupération des paramétres
      @SuppressWarnings("unchecked")
      List<String> pagm = (List<String>) joinPoint.getArgs()[0];
      String issuer = (String) joinPoint.getArgs()[1];
      KeyStore keystore = (KeyStore) joinPoint.getArgs()[INDEX_3];
      String alias = (String) joinPoint.getArgs()[INDEX_4];
      String password = (String) joinPoint.getArgs()[INDEX_5];

      // issuer not null
      notNullValidate(issuer, "issuer", ARG_EMPTY);
      // PAGM not null
      // on filtre les pagms
      if (CollectionUtils.isEmpty(ListUtils.filter(pagm))) {

         throw new IllegalArgumentException(
               "Il faut spécifier au moins un PAGM");
      }
      // keystore not null
      notNullValidate(keystore, "keystore", ARG_EMPTY);
      // alias
      notNullValidate(alias, "alias", ARG_EMPTY);
      // password
      notNullValidate(password, "password", ARG_EMPTY);

   }

   private void notNullValidate(Object obj, String name, String message) {

      if (obj == null) {

         Map<String, String> args = new HashMap<String, String>();
         args.put("0", name);

         throw new IllegalArgumentException(StrSubstitutor.replace(message,
               args));
      }

   }

   private void notNullValidate(String obj, String name, String message) {

      if (!StringUtils.isNotBlank(obj)) {

         Map<String, String> args = new HashMap<String, String>();
         args.put("0", name);

         throw new IllegalArgumentException(StrSubstitutor.replace(message,
               args));
      }

   }
}
