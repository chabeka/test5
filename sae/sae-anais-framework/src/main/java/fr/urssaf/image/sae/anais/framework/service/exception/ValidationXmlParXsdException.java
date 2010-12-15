package fr.urssaf.image.sae.anais.framework.service.exception;

import java.util.Arrays;

import fr.urssaf.image.sae.vi.exception.VIException;

/**
 * Classe d'exception hérite de {@link RuntimeException}<br>
 * Le document XML n'est pas conforme au schéma XSD<br>
 * <br>
 * Cette exception peut être levée par l'appel de la méthode
 * <code>authentifierPourSaeParLoginPassword<code>
 * 
 * @see SaeAnaisService
 */
public class ValidationXmlParXsdException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   private final String nomDuDocumentXml;

   private final String nomDuSchemaXsd;
   
   private final String[] erreurs;

   /**
    * initialisation des attributs
    * <ul>
    * <li><code>nomDuDocumentXml</code> : <code>VECTEUR IDENTIFICATION</code></li>
    * <li><code>nomDuSchemaXsd</code> : {@link VIException#getXsd()}</li>
    * <li><code>erreurs</code> : {@link VIException#getErrors()}</li>
    * </ul>
    * @param exception exception levée lors de l'écriture du VI
    */
   public ValidationXmlParXsdException(VIException exception) {
      super(exception);
      this.nomDuSchemaXsd = exception.getXsd();
      this.nomDuDocumentXml = "VECTEUR IDENTIFICATION";
      this.erreurs = exception.getErrors();
   }

   /**
    * @return Nom du fichier (sans le path) ou nom logique du document XML dont la validation à échouer
    */
   public final String getNomDuDocumentXml() {
      return nomDuDocumentXml;
   }

   /**
    * @return Nom du fichier (sans le path) du schéma XSD utilisé par la validation
    */
   public final String getNomDuSchemaXsd() {
      return nomDuSchemaXsd;
   }

   /**
    * @return Liste des erreurs survenues pendant la validation
    */
   public final String[] getErreurs() {
      return Arrays.copyOf(erreurs, erreurs.length);
   }

}
