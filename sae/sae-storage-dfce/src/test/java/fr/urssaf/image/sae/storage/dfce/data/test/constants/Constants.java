package fr.urssaf.image.sae.storage.dfce.data.test.constants;

/**
 * Classe des constantes utilisés dans les tests.
 * 
 * @author akenore
 * 
 */
@SuppressWarnings("PMD.LongVariable")
public final class Constants {
   /**
    * Répertoire de base des fichiers Xml
    */
   public static final String XML_DIR = "src/test/resources/XML/";
   /**
    * Chemin des fichiers Xml
    */
   public static final String XML_FILE_PATH[] = { XML_DIR + "doc1.xml",
         XML_DIR + "doc11.xml", XML_DIR + "doc132.xml", XML_DIR + "doc4.xml",
         XML_DIR + "doc7.xml", XML_DIR + "doc70.xml", XML_DIR + "doc77.xml" };

   /**
    * Liste des métadatas techniques
    */
   public static final String TEC_METADATAS[] = { "DCR", "title", "docType" };
   /**
    * Chemin du fichier Xml des code fonction.
    */

   public static final String XML_FILE_CODE_APPIL[] = { XML_DIR
         + "codeFonction.xml" };
   /**
    * Chemin du fichier Xml des métadonnées.
    */
   public static final String XML_FILE_DESIRED_MDATA[] = { XML_DIR
         + "desiredMetaData.xml" };
   // Identifiant du processus de traitement en masse pour les test.
   public static final String ID_PROCESS_TEST = "999999";

   /** Cette classe n'est pas faite pour être instanciée. */
   private Constants() {
      assert false;
   }
}