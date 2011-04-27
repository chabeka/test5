package fr.urssaf.image.tests.dfcetest;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.docubase.rheatoolkit.RheaToolkit;
import net.docubase.rheatoolkit.RheaToolkitException;
import net.docubase.rheatoolkit.base.BaseIndex;
import net.docubase.rheatoolkit.domain.DomainFactory;
import net.docubase.rheatoolkit.session.UserSession;
import net.docubase.rheatoolkit.session.UserSessionFactory;
import net.docubase.rheatoolkit.session.base.DocumentStore;
import net.docubase.rheatoolkit.session.base.DocumentTag;
import net.docubase.rheatoolkit.session.base.UserBase;
import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DocubaseHelper {
   public static final String NUMERIC = "0123456789";
   public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
   public static final String ALPHA_NUM = ALPHA + NUMERIC;
   private static final byte[] DOC_CONTENT = "Ceci est un petit document!".getBytes();
   private static final String[] CAT_NAMES = Categories.getNames(); // alias

   private static Random rand = new Random();
   private static final Logger LOGGER = LoggerFactory.getLogger(DocubaseHelper.class);

   private DocubaseHelper() {
   }

   public static void initToolkit() throws RheaToolkitException {
      Properties parameters = new Properties();
      parameters.setProperty("service.access", "cer69-ds4int:4020");
      // parameters.setProperty("service.encoding", "UTF8");
      // parameters.setProperty("service.retry", "5"); // nombre de tentatives
      // de connection (5)
      // parameters.setProperty("service.timeout", "10000"); //Timeout de la
      // connexion (10000 i.e. 10s)
      if (RheaToolkit.isInitialized()) {
         RheaToolkit.shutdown();
      }
      RheaToolkit.initialize(parameters);
   }

   public static List<Document> insertManyDocs(int num, Base base, String appliSource)
         throws Exception {
      List<Document> docs = new ArrayList<Document>();

      for (int i = 0; i < num; i++) {
         Document doc = storeDocWithRandomCategories(base, appliSource);
         docs.add(doc);
      }
      return docs;
   }

   public static Document storeDoc(Base base, String title, String type,
         Map<String, String> catValues) throws Exception {
      Document document = ToolkitFactory.getInstance().createDocumentTag(base);

      //document.setDocTitle(title);
      document.setDocType(type);

      for (Entry<String, String> ent : catValues.entrySet()) {
         BaseCategory baseCategory = base.getBaseCategory(ent.getKey());
         document.addCriterion(baseCategory, ent.getValue());
      }
      ServiceProvider.getStoreService().storeDocument(document, getAFile(), null);
      return document;
   }

   public static Document storeDocWithRandomCategories(Base base, String title) throws Exception {
      String type = Math.random() > 0.5 ? "PDF" : "DOC";
      Map<String, String> catValues = getCategoriesRandomValues(title);
      return storeDoc(base, title, type, catValues);
   }

   /**
    * @param title
    *           Le titre du document est affecté à la catégorie
    *           "application source"
    * @return nom de la catégorie => valeur générée aléatoirement
    */
   private static Map<String, String> getCategoriesRandomValues(String title) {
      Map<String, String> catValues = new HashMap<String, String>();
      catValues.put(Categories.TYPE_DOC.toString(), Math.random() > 0.5 ? ".pdf" : ".doc"); // 0
      catValues.put(Categories.TYPE_DOC_LIBELLE.toString(),
            generateString("libTypeDoc".toLowerCase(), 50, 1000)); // 1
      catValues.put(Categories.NUM_CPT.toString(), randomNum(9)); // 2
      catValues.put(Categories.SIRET.toString(), randomNum(13)); // 3
      if (Math.random() > .9)
         catValues.put(Categories.NNI.toString(), randomNum(15)); // 4
      if (Math.random() > .2)
         catValues.put(Categories.PERIODE.toString(), randomNum(4)); // 5
      catValues.put(Categories.CODE_ORGA_CPT.toString(), "ur" + randomNum(3)); // 6
      catValues.put(Categories.CODE_UR.toString(), "ur" + randomNum(3)); // 7
      if (Math.random() > .95)
         catValues.put(Categories.NUM_PERS.toString(), randomNum(9)); // 8
      if (Math.random() > .95)
         catValues.put(Categories.NUM_AFFAIRE.toString(), randomNum(6)); // 9
      catValues.put(Categories.DENOM_CPT.toString(), randomString(50)); // 10
      if (Math.random() > .5)
         catValues.put(Categories.NUM_PIECE.toString(), randomAlphaNum(12)); // 11
      catValues.put(Categories.APPLI_SOURCE.toString(), title); // 12
      catValues.put(Categories.NUM_LOT.toString(), randomNum(6)); // 13
      if (Math.random() > .95)
         catValues.put(Categories.CODE_COMMUNE.toString(), randomAlphaNum(6)); // 14
      if (Math.random() > .98)
         catValues.put(Categories.CATEGORIE.toString(), randomNum(5)); // 15
      if (Math.random() > .2)
         catValues.put(Categories.NUM_GROUPE.toString(), randomNum(18)); // 16
      catValues.put(Categories.DATE_ORIGINE.toString(), generateDate()); // 17
      if (Math.random() > .2)
         catValues.put(Categories.DATE_TRAITEMENT.toString(), generateDate()); // 18
      if (Math.random() > .2)
         catValues.put(Categories.DATE_EFFET.toString(), generateDate()); // 19
      // catValues.put(Categories.JOURNEE_COMPTABLE.toString(), AAA); // 20
      if (Math.random() > .2)
         catValues.put(Categories.NUM_STRUCT_CTX.toString(), randomNum(10)); // 21
      if (Math.random() > .2)
         catValues.put(Categories.NUM_PARTENAIRE.toString(), randomNum(7)); // 22
      if (Math.random() > .05)
         catValues.put(Categories.NUM_INTER_CTX.toString(), randomNum(5)); // 23
      // catValues.put(Categories.NUM_ALLOCATAIRE.toString(), AAA); // 24
      catValues.put(Categories.CODE_USER_INDEX.toString(), "ur" + randomNum(8)); // 25
      catValues.put(Categories.CODE_USER_STOCK.toString(), "ur" + randomNum(8)); // 26
      // catValues.put(Categories.NUM_DOSSIER.toString(), AAA); // 27
      if (Math.random() > .05) {
         catValues.put(Categories.DATE_VERIF.toString(), generateDate()); // 28
         catValues.put(Categories.AGENT_VERIF.toString(), "ur" + randomNum(8)); // 29
         catValues.put(Categories.CODE_VERIF.toString(), randomNum(3)); // 30
      }
      catValues.put(Categories.DATE_CLASSEMENT_GED.toString(), generateDate()); // 31
      catValues.put(Categories.NUM_CPT_INT.toString(), randomNum(7)); // 32
      if (Math.random() > .05)
         catValues.put(Categories.NUM_CPT_ANCIEN.toString(), randomNum(10)); // 33
      catValues.put(Categories.TYPE_DOC_ARCHIVAGE.toString(), generateTypeDoc()); // 34
      if (Math.random() > .4)
         catValues.put(Categories.NUM_ID_ARCHIVAGE.toString(), "ur" + randomNum(18)); // 35
      // catValues.put(Categories.CERTIFICAT.toString(), AAA); // 36
      if (Math.random() > .1)
         catValues.put(Categories.CODE_ORGA_MUTUALISE.toString(), "ur" + randomNum(3)); // 37
      if (Math.random() > .05) {
         catValues.put(Categories.NOM_NAISS_SALARIE.toString(), randomString(10)); // 38
         catValues.put(Categories.NOM_MARITAL_SALARIE.toString(), randomString(10)); // 39
         catValues.put(Categories.PRENOM_SALARIE.toString(), randomString(10)); // 40
         catValues.put(Categories.CODE_INT_SALARIE.toString(), randomAlphaNum(10)); // 41
         catValues.put(Categories.NATURE_EMPLOI.toString(), randomNum(5)); // 42
         catValues.put(Categories.DATE_NAISS_SALARIE.toString(), generateDate()); // 43
      }
      if (Math.random() > .1)
         catValues.put(Categories.NUM_COMPOSTAGE.toString(), randomNum(11)); // 44
      if (Math.random() > .1)
         catValues.put(Categories.DATE_VERSEMENT.toString(), generateDate()); // 45
      if (Math.random() > .05)
         catValues.put(Categories.NUM_VOLET_SOCIAL.toString(), randomAlphaNum(10)); // 46
      catValues.put(Categories.RFP_FONCTION.toString(),
            new Integer(rand.nextInt(10) + 1).toString()); // 47
      catValues.put(Categories.RFP_ACTIVITE.toString(), randomAlphaNum(1)); // 48
      if (Math.random() > .05)
         catValues.put(Categories.NOM_PERS.toString(), randomString(50)); // 49
      // catValues.put(Categories.ID_COMPLEMENTAIRE.toString(), AAA); // 50
      if (Math.random() > .01)
         catValues.put(Categories.NNI_EMPLOYEUR.toString(), randomNum(15)); // 51
      // catValues.put(Categories.NUM_RECOURS.toString(), AAA); // 52
      return catValues;
   }

   public static String generateTypeDoc() {
      int nb = rand.nextInt(4) + 1;
      String typeDoc = "";
      for (int i = 0; i < nb; i++) {
         if (!typeDoc.isEmpty())
            typeDoc = typeDoc + ".";
         typeDoc = typeDoc + rand.nextInt(10);
      }
      return typeDoc;
   }

   public static String generateAppliSource() {
      if (Math.random() > .15)
         return "lad";
      if (Math.random() > .5)
         return "ged";
      if (Math.random() > .5)
         return "watt";
      return "bcc";
   }

   public static String generateDate() {
      int annee = rand.nextInt(10) + 1;
      int mois = rand.nextInt(12) + 1;
      int jour = rand.nextInt(28) + 1;
      String date = "20" + String.format("%02d-%02d-%02d", annee, mois, jour);
      return date;
   }

   public static String randomNum(int len) {
      StringBuilder sb = new StringBuilder(len);
      for (int i = 0; i < len; i++) {
         int index = rand.nextInt(NUMERIC.length());
         sb.append(NUMERIC.substring(index, index + 1));
      }
      return sb.toString();
   }

   public static String randomString(int len) {
      StringBuilder sb = new StringBuilder(len);
      for (int i = 0; i < len; i++) {
         int index = rand.nextInt(ALPHA.length());
         sb.append(ALPHA.substring(index, index + 1));
      }
      return sb.toString();
   }

   public static String randomAlphaNum(int len) {
      StringBuilder sb = new StringBuilder(len);
      for (int i = 0; i < len; i++) {
         int index = rand.nextInt(ALPHA_NUM.length());
         sb.append(ALPHA_NUM.substring(index, index + 1));
      }
      return sb.toString();
   }

   public static File getAFile() throws Exception {
      File newDoc = File.createTempFile("doc" + System.nanoTime(), ".txt");
      FileOutputStream fos = new FileOutputStream(newDoc);
      fos.write(DOC_CONTENT);
      fos.flush();
      fos.close();
      newDoc.deleteOnExit();
      return newDoc;
   }

   public static ConcurrentMap<String, List<String>> generatedStrings = new ConcurrentHashMap<String, List<String>>();

   public static String generateString(String id, int longueur, int nbValue) {
      List<String> list = generatedStrings.get(id);
      if (list == null) {
         list = new ArrayList<String>();
         generatedStrings.put(id, list);
      }

      String result = null;
      if (list.size() < nbValue) {
         // Génère un nouvel élément
         String s = randomString(longueur);
         list.add(s);
         result = s;
      } else {
         // Choisi un élément au hasard dans la liste
         int index = rand.nextInt(list.size());
         result = list.get(index);
      }
      if (result == null) {
         result = generateString(id, longueur, nbValue);
      }
      return result;
   }

   private static volatile UserSession session = null;

   public static UserSession getSession(String login, String pwd) throws RheaToolkitException {
      if (session == null) {
         Locale[] list = new Locale[2];
         list[0] = Locale.FRENCH;
         list[1] = Locale.ENGLISH;

         File path = new File(System.getProperty("java.io.tmpdir") + "/" + login);
         // try {
         session = UserSessionFactory.connect(login, pwd, list, path);
         // } catch (RheaToolkitException e) {}
      }
      return session;
   }

   public static UserSession getAdminSession() throws RheaToolkitException {
      return getSession("_ADMIN", "DOCUBASE");
   }

   public static UserBase getBase(Integer domainId) throws RheaToolkitException {
      return getAdminSession().getBaseManager().getBase(DomainFactory.getDomain(domainId), "NCOTI");
   }

   public static void testCall() throws Exception {
      Locale[] list = new Locale[2];
      list[0] = Locale.FRENCH;
      list[1] = Locale.ENGLISH;

      File path = new File(System.getProperty("java.io.tmpdir") + "/_ADMIN");
      // try {
      UserSession sess = UserSessionFactory.connect("_ADMIN", "DOCUBASE", list, path);

      UserBase base = sess.getBaseManager().getBase(DomainFactory.getDomain(1), "NCOTI");
      BaseIndex index = base.getBaseDefinition().getIndex();

      MyTagControlError control = new MyTagControlError(base.getBaseDefinition().getIndex());
      File newDoc = getAFile();
      /*
       * Note pour plus tard pour les recherches : c0 catégorie très peu
       * discriminante. c3 fortement discriminante.
       */

      DocumentTag tag = new DocumentTag(base);
      // tag.setDocTitle("Document n " + i);

      tag.setDocType(rand.nextDouble() > 0.5 ? "TXT" : "DOC");
      tag.addCriterion(index.getCategory(CAT_NAMES[0]), true, rand.nextDouble() > 0.5 ? ".txt"
            : ".doc");

      tag.addCriterion(index.getCategory(CAT_NAMES[1]), true,
            generateString("libTypeDoc".toLowerCase(), 50, 1000));
      tag.addCriterion(index.getCategory(CAT_NAMES[2]), true, randomNum(9)); // Num
                                                                             // compte

      tag.addCriterion(index.getCategory(CAT_NAMES[3]), true, randomNum(13)); // Siret
      if (Math.random() > .9) {
         tag.addCriterion(index.getCategory(CAT_NAMES[4]), true, randomNum(15)); // NNI
                                                                                 // salarié
      }
      // Période
      if (Math.random() > .2) {
         tag.addCriterion(index.getCategory(CAT_NAMES[5]), true, randomNum(4));
      }
      // C6="Code organisme du compte",IE,1,1
      tag.addCriterion(index.getCategory(CAT_NAMES[6]), true, "ur" + randomNum(3));
      // C7="Code URSSAF",IE,1,1
      tag.addCriterion(index.getCategory(CAT_NAMES[7]), true, "ur" + randomNum(3));
      // C8="No Personne",I,0,1
      if (Math.random() > .95) {
         tag.addCriterion(index.getCategory(CAT_NAMES[8]), true, randomNum(9));
      }
      // C9="No Affaire",I,0,1
      if (Math.random() > .95) {
         tag.addCriterion(index.getCategory(CAT_NAMES[9]), true, randomNum(6));
      }
      // C10="Denomination du compte",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[10]), true, randomString(50));
      // C11="No Piece",I,0,1
      if (Math.random() > .5) {
         tag.addCriterion(index.getCategory(CAT_NAMES[11]), true, randomAlphaNum(12));
      }
      // C12="Application Source",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[12]), true, generateAppliSource());
      // C13="No Lot",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[13]), true, randomNum(6));
      // C14="Code Commune",I,0,1
      if (Math.random() > .95) {
         tag.addCriterion(index.getCategory(CAT_NAMES[14]), true, randomAlphaNum(6));
      }
      // C15="Categorie",I,0,1
      if (Math.random() > .98) {
         tag.addCriterion(index.getCategory(CAT_NAMES[15]), true, randomNum(5));
      }
      // C16="No Groupe",I,0,1
      if (Math.random() > .2) {
         tag.addCriterion(index.getCategory(CAT_NAMES[16]), true, randomNum(18));
      }
      // C17="Date d origine",I,1,1
      tag.addCriterion(index.getCategory(CAT_NAMES[17]), true, generateDate());
      // C18="Date Traitement",I,0,1
      if (Math.random() > .2) {
         tag.addCriterion(index.getCategory(CAT_NAMES[18]), true, generateDate());
      }
      // C19="Date d Effet",I,0,1
      if (Math.random() > .2)
         tag.addCriterion(index.getCategory(CAT_NAMES[19]), true, generateDate());
      // C20="Journee Comptable",I,0,1
      // C21="No Structure CNTX",I,0,1
      if (Math.random() > .2)
         tag.addCriterion(index.getCategory(CAT_NAMES[21]), true, randomNum(10));
      // C22="No Partenaire",I,0,1
      if (Math.random() > .2)
         tag.addCriterion(index.getCategory(CAT_NAMES[22]), true, randomNum(7));
      // C23="No Intervention CTRL",I,0,1
      if (Math.random() > .05)
         tag.addCriterion(index.getCategory(CAT_NAMES[23]), true, randomNum(5));
      // C24="No Allocataire",I,0,1
      // C25="Code utilisateur indexation",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[25]), true, "ur" + randomNum(8));
      // C26="Code utilisateur stockage",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[26]), true, "ur" + randomNum(8));
      // C27="No Dossier",I,0,1
      if (Math.random() > .05) {
         // C28="Date verification",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[28]), true, generateDate());
         // C29="Agent verificateur",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[29]), true, "ur" + randomNum(8));
         // C30="Code verification",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[30]), true, randomNum(3));
      }
      // C31="Date de classement GED",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[31]), true, generateDate());
      // C32="No de compte Interne",I,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[32]), true, randomNum(7));
      // C33="Ancien numero de compte",I,0,1
      if (Math.random() > .05)
         tag.addCriterion(index.getCategory(CAT_NAMES[33]), true, randomNum(10));
      // C34="Type de document d archivage",IE,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[34]), true, generateTypeDoc());
      // C35="No Id Archivage",I,0,1
      if (Math.random() > .4)
         tag.addCriterion(index.getCategory(CAT_NAMES[35]), true, "ur" + randomNum(18));
      // C36="Certificat",I,0,1
      // C37="Code organisme mutualise",IE,0,1
      if (Math.random() > .1)
         tag.addCriterion(index.getCategory(CAT_NAMES[37]), true, "ur" + randomNum(3));
      if (Math.random() > .05) {
         // C38="Nom naissance salarie",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[38]), true, randomString(10));
         // C39="Nom marital salarie",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[39]), true, randomString(10));
         // C40="Prenom salarie",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[40]), true, randomString(10));
         // C41="Code interne salarie",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[41]), true, randomAlphaNum(10));
         // C42="Nature de l emploi",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[42]), true, randomNum(5));
         // C43="Date naissance salarie",I,0,1
         tag.addCriterion(index.getCategory(CAT_NAMES[43]), true, generateDate());
      }
      // C44="No de Compostage",I,0,1
      if (Math.random() > .1)
         tag.addCriterion(index.getCategory(CAT_NAMES[44]), true, randomNum(11));
      // C45="Date de versement",I,0,1
      if (Math.random() > .1)
         tag.addCriterion(index.getCategory(CAT_NAMES[45]), true, generateDate());
      // C46="No Volet Social",I,0,1
      if (Math.random() > .05)
         tag.addCriterion(index.getCategory(CAT_NAMES[46]), true, randomAlphaNum(10));
      // C47="Fonction RFP",IE,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[47]), true,
            new Integer(rand.nextInt(10) + 1).toString());
      // C48="Activite RFP",IE,0,1
      tag.addCriterion(index.getCategory(CAT_NAMES[48]), true, randomAlphaNum(1));
      // C49="Nom de personne",I,0,1
      if (Math.random() > .05)
         tag.addCriterion(index.getCategory(CAT_NAMES[49]), true, randomString(50));
      // C50="Id Complementaire",I,0,1
      // C51="NNI employeur",I,0,1
      if (Math.random() > .01)
         tag.addCriterion(index.getCategory(CAT_NAMES[51]), true, randomNum(15));
      // C52="Numero de Recours",I,0,1

      DocumentStore store = new DocumentStore(tag, newDoc);

      try {
         store.store(control);
      } catch (Exception exc) {
         exc.printStackTrace();
      }
   }

   /**
    * Arrête la base puis la supprime. (null safe)
    */
   public static void dropBase(Base base) {
      if (base != null) {
         ServiceProvider.getBaseAdministrationService().stopBase(base);
         ServiceProvider.getBaseAdministrationService().deleteBase(base);
      }
   }

   /**
    * Démarre la base. (null safe)
    */
   public static void startBase(Base base) {
      if (base != null) {
         ServiceProvider.getBaseAdministrationService().startBase(base);
      }
   }

   // public class BaseNotFoundException()

   public static Base getBase(String baseId) {
      Base base = ServiceProvider.getBaseAdministrationService().getBase(baseId);

      if (base == null) {
         throw new RuntimeException("Base " + baseId + " introuvable");
      }
      return base;
   }

   /**
    * @param baseId
    */
   public static void dropBase(String baseId) {
      dropBase(getBase(baseId));
   }
}