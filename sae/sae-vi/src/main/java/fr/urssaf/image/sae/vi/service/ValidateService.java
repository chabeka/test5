package fr.urssaf.image.sae.vi.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

import fr.urssaf.image.sae.vi.component.SchemaXSDFactory;
import fr.urssaf.image.sae.vi.schema.SaeJetonAuthentificationType;

/**
 * Classe de validation du VI<br>
 * <br>
 * Exemple d'instanciation de la classe<br>
 * 
 * <pre>
 * String xsdPath = this.getClass().getResource(&quot;/xsd/sae-anais.xsd&quot;).toURI()
 *       .getPath();
 * service = new ValidateService(xsdPath);
 * </pre>
 * 
 */
public class ValidateService {

   private final Schema schema;

   /**
    * initialisation du xsd pour la validation des VI<br>
    */
   // TODO Instancier le fichier xsd directement du jar
   public ValidateService() {

      SchemaXSDFactory factory = new SchemaXSDFactory();

      schema = factory.createSchema();

   }

   /**
    * Retourne si le VI est valide en s'appuyant sur le schéma XSD
    * <code>sae-anais.xsd</code><br>
    * <br>
    * La validation se fait en appelant la méthode
    * {@link Unmarshaller#unmarshal(InputStream)}<br>
    * <br>
    * Si le jeton comporte la moindre {@link JAXBException} ou lève une
    * exception alors le jeton est considéré comme invalide
    * 
    * @param xml
    *           VI du jeton d'authentification
    * @return true si le jeton n'est pas conforme faux sinon
    */
   @SuppressWarnings("PMD.EmptyCatchBlock")
   public final boolean isValidate(String xml) {

      boolean validate = false;

      try {

         JAXBContext context = JAXBContext
               .newInstance(SaeJetonAuthentificationType.class);
         Unmarshaller unmarshaller = context.createUnmarshaller();
         unmarshaller.setSchema(schema);
         InputStream input = new ByteArrayInputStream(xml.getBytes());

         try {
            unmarshaller.unmarshal(input);
         } finally {
            input.close();
         }

         validate = true;

      } catch (JAXBException e) {
         // not implemented
      } catch (IOException e) {
         // not implemented
      }

      return validate;
   }

}
