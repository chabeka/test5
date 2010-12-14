package fr.urssaf.image.sae.vi.component;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * Classe d'instanciation du schema XSD
 * 
 * 
 */
public class SchemaXSDFactory {

   /**
    * Instanciation d'un objet de type {@link Schema} pour le schema
    * <code>/xsd/sae-anais.xsd</code>
    * 
    * @return Schema correspond à <code>/xsd/sae-anais.xsd</code>
    */
   public final Schema createSchema() {

      SchemaFactory schemaFactory = SchemaFactory
            .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

      try {
         return schemaFactory.newSchema(this.getClass().getResource(
               "/xsd/sae-anais.xsd"));
      } catch (SAXException e) {
         throw new IllegalStateException(e);
      }
   }
}
