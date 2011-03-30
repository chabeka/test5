package fr.urssaf.image.sae.vi.service;

import java.net.URI;
import java.security.KeyStore;
import java.security.cert.X509CRL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.exception.SamlFormatException;
import fr.urssaf.image.sae.saml.exception.SamlSignatureException;
import fr.urssaf.image.sae.saml.params.SamlAssertionParams;
import fr.urssaf.image.sae.saml.params.SamlCommonsParams;
import fr.urssaf.image.sae.saml.service.SamlAssertionCreationService;
import fr.urssaf.image.sae.saml.service.SamlAssertionExtractionService;
import fr.urssaf.image.sae.saml.service.SamlAssertionVerificationService;
import fr.urssaf.image.sae.saml.util.ConverterUtils;
import fr.urssaf.image.sae.vi.exception.VIAppliClientException;
import fr.urssaf.image.sae.vi.exception.VIFormatTechniqueException;
import fr.urssaf.image.sae.vi.exception.VIInvalideException;
import fr.urssaf.image.sae.vi.exception.VINivAuthException;
import fr.urssaf.image.sae.vi.exception.VIPagmIncorrectException;
import fr.urssaf.image.sae.vi.exception.VIServiceIncorrectException;
import fr.urssaf.image.sae.vi.exception.VISignatureException;
import fr.urssaf.image.sae.vi.modele.VIContenuExtrait;

/**
 * Classe de lecture et d'écriture du VI pour les web services<br>
 * <br>
 * Le VI est un jeton SAML 2.0 conforme aux <a
 * href="http://saml.xml.org/saml-specifications#samlv20"/>spécifications de
 * OASIS</a><br>
 * <br>
 * L'implémentation s'appuie sur les classes
 * <ul>
 * <li>{@link SamlAssertionCreationService}</li>
 * <li>{@link SamlAssertionExtractionService}</li>
 * <li>{@link SamlAssertionVerificationService}</li>
 * </ul>
 * <br>
 * <br>
 * Les paramètres d'entrées de chaque méthode sont vérifiés par AOP par la
 * classe {@link fr.urssaf.image.sae.vi.component.WebServiceVIServiceValidate}<br>
 * 
 */
public class WebServiceVIService {

   private final SamlAssertionCreationService createService;
   private final SamlAssertionExtractionService extractService;
   private final SamlAssertionVerificationService checkService;

   private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

   private static final URI METHOD_AUTH2 = ConverterUtils
         .uri("urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified");

   /**
    * instanciation de {@link SamlAssertionCreationService}<br>
    * instanciation de {@link SamlAssertionExtractionService}<br>
    * instanciation de {@link SamlAssertionVerificationService}<br>
    * 
    */
   public WebServiceVIService() {
      createService = new SamlAssertionCreationService();
      extractService = new SamlAssertionExtractionService();
      checkService = new SamlAssertionVerificationService();
   }

   /**
    * Génération d'un Vecteur d'Identification (VI) pour s'authentifier auprès
    * d'un service web du SAE. <br>
    * Le VI prend la forme d'une assertion SAML 2.0 signée électroniquement. <br>
    * La récupération des droits applicatifs doit être faite en amont.<br>
    * <br>
    * Paramètres obligatoires
    * <ul>
    * <li>pagm (au moins un droit)</li>
    * <li>issuer</li>
    * <li>keystore</li>
    * <li>alias</li>
    * <li>password</li>
    * </ul>
    * 
    * @param pagm
    *           Liste des droits applicatifs
    * @param issuer
    *           L'identifiant de l'application cliente
    * @param idUtilisateur
    *           L'identifiant de l'utilisateur
    * @param keystore
    *           Le certificat applicatif de l'application cliente, sa clé
    *           privée, et la chaîne de certification associée, pour la
    *           signature électronique du VI
    * @param alias
    *           L'alias de la clé privée du KeyStore
    * @param password
    *           mot du de la clé privée
    * @return Le Vecteur d'Identification
    */
   public final String creerVIpourServiceWeb(List<String> pagm, String issuer,
         String idUtilisateur, KeyStore keystore, String alias, String password) {

      Date systemDate = new Date();

      SamlAssertionParams assertionParams = new SamlAssertionParams();
      SamlCommonsParams commonsParams = new SamlCommonsParams();

      assertionParams.setCommonsParams(commonsParams);

      commonsParams.setIssuer(issuer);
      commonsParams.setNotOnOrAfter(DateUtils.addHours(systemDate, 1));
      commonsParams.setNotOnBefore(DateUtils.addHours(systemDate, -1));
      commonsParams.setAudience(ConverterUtils.uri("http://sae.urssaf.fr"));
      commonsParams.setPagm(pagm);

      String subjectId2 = StringUtils.isNotBlank(idUtilisateur) ? idUtilisateur
            : "NON_RENSEIGNE";
      assertionParams.setSubjectId2(subjectId2);
      assertionParams.setSubjectFormat2(ConverterUtils
            .uri("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
      assertionParams.setMethodAuthn2(METHOD_AUTH2);
      assertionParams.setRecipient(ConverterUtils.uri("urn:URSSAF"));

      return createService.genererAssertion(assertionParams, keystore, alias,
            password);

   }

   /**
    * Vérification d'un Vecteur d'Identification (VI) généré pour s'authentifier
    * auprès d'un service web du SAE<br>
    * <br>
    * Les vérification sont :
    * <ul>
    * <li>vérification de la structure XML de l'assertion et de sa signature
    * électronique</li>
    * <li>vérification des valeurs transmises dans l'assertion :
    * <ul>
    * <li>date systeme postérieure à notOnBefore</li>
    * <li>date systeme strictement antérieure à notOnOrAfte</li>
    * <li>serviceVise identique à audience</li>
    * <li>idAppliClient identique à issuer</li>
    * <li>methodAuthn2</li>
    * <li>PAGM</li>
    * </ul>
    * </li>
    * </ul>
    * 
    * @param identification
    *           Le Vecteur d'Identification à vérifier
    * @param serviceVise
    *           URI décrivant le service visé
    * @param idAppliClient
    *           Identifiant de l'application consommatrice du service, récupéré
    *           à partir du certificat client SSL (ignoré pour l'instant)
    * @param keystore
    *           Les certificats des autorités de certification qui sont
    *           reconnues pour être autorisées à délivrer des certificats de
    *           signature de VI, ainsi que leur chaîne de certification
    * @param alias
    *           L'alias de la clé privée du KeyStore
    * @param password
    *           mot du de la clé privée
    * @param crl
    *           Les CRL
    * @return Des valeurs extraits du VI qui peuvent être exploités pour mettre
    *         en place un contexte de sécurité basé sur l’authentification,
    *         et/ou pour de la traçabilité
    * @throws VIFormatTechniqueException
    *            Une erreur technique sur le format du VI a été détectée
    * @throws VISignatureException
    *            La signature électronique du VI est incorrecte
    * @throws VIInvalideException
    *            Le VI est invalide
    * @throws VIAppliClientException
    *            Le service visé ne correspond pas au service indiqué dans
    *            l'assertion
    * @throws VINivAuthException
    *            Le niveau d'authentification initial n'est pas conforme au
    *            contrat d'interopérabilité
    * @throws VIPagmIncorrectException
    *            Le ou les PAGM présents dans le VI sont invalides
    * @throws VIServiceIncorrectException
    *            Le service visé ne correspond pas au service indiqué dans
    *            l'assertion
    */
   public final VIContenuExtrait verifierVIdeServiceWeb(String identification,
         URI serviceVise, String idAppliClient, KeyStore keystore,
         String alias, String password, List<X509CRL> crl)
         throws VIFormatTechniqueException, VISignatureException,
         VIInvalideException, VIAppliClientException, VINivAuthException,
         VIPagmIncorrectException, VIServiceIncorrectException {

      // vérification du jeton SAML
      try {
         checkService.verifierAssertion(identification, keystore, alias, crl);
      } catch (SamlFormatException e) {
         throw new VIFormatTechniqueException(e);
      } catch (SamlSignatureException e) {
         throw new VISignatureException(e);
      }

      // extraction du jeton SAML
      SamlAssertionData data = extractService.extraitDonnees(identification);

      // vérification supplémentaires sur le jeton SAML
      this.validate(data, serviceVise, idAppliClient, new Date());

      // instanciation de la valeur retour
      VIContenuExtrait extrait = new VIContenuExtrait();
      extrait.setPagm(data.getAssertionParams().getCommonsParams().getPagm());
      extrait.setIdUtilisateur(data.getAssertionParams().getSubjectId2());
      // TODO extrait le code de l'application du certificat de signature

      return extrait;
   }

   protected final void validate(SamlAssertionData data, URI serviceVise,
         String idAppliClient, Date systemDate) throws VIInvalideException,
         VIAppliClientException, VINivAuthException, VIPagmIncorrectException,
         VIServiceIncorrectException {

      // la date systeme doit être postérieure à NotOnBefore
      Date notOnBefore = data.getAssertionParams().getCommonsParams()
            .getNotOnBefore();

      if (systemDate.compareTo(notOnBefore) < 0) {

         Map<String, String> args = new HashMap<String, String>();
         args.put("0", DateFormatUtils.format(notOnBefore, DATE_PATTERN));
         args.put("1", DateFormatUtils.format(systemDate, DATE_PATTERN));

         String message = "L'assertion n'est pas encore valable: elle ne sera active qu'à partir de ${0} alors que nous sommes le ${1}";

         throw new VIInvalideException(StrSubstitutor.replace(message, args));
      }

      // la date systeme doit être strictement antérieure à NotOnOrAfter
      Date notOnOrAfter = data.getAssertionParams().getCommonsParams()
            .getNotOnOrAfter();
      if (systemDate.compareTo(notOnOrAfter) >= 0) {

         Map<String, String> args = new HashMap<String, String>();
         args.put("0", DateFormatUtils.format(notOnOrAfter, DATE_PATTERN));
         args.put("1", DateFormatUtils.format(systemDate, DATE_PATTERN));

         String message = "L'assertion a expirée : elle n'était valable que jusqu’au ${0}, hors nous sommes le ${1}";

         throw new VIInvalideException(StrSubstitutor.replace(message, args));

      }

      // serviceVise doit être égal à Audience

      if (!serviceVise.equals(data.getAssertionParams().getCommonsParams()
            .getAudience())) {

         throw new VIServiceIncorrectException(serviceVise, data
               .getAssertionParams().getCommonsParams().getAudience());
      }

      // idAppliClient doit être égal à Issuer
      if (!idAppliClient.equals(data.getAssertionParams().getCommonsParams()
            .getIssuer())) {

         throw new VIAppliClientException(data.getAssertionParams()
               .getCommonsParams().getIssuer());
      }

      // MethodAuth2 doit être égal à
      // 'urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified'
      if (!METHOD_AUTH2.equals(data.getAssertionParams().getMethodAuthn2())) {

         throw new VINivAuthException(data.getAssertionParams()
               .getMethodAuthn2());
      }

      // les PAGMS doivent exister
      // TODO vérification des PAGM
      if (CollectionUtils.isEmpty(data.getAssertionParams().getCommonsParams()
            .getPagm())) {

         throw new VIPagmIncorrectException();
      }
   }
}
