/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.RNDTypeDocument;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service.DataManagementInterface;

/**
 * 
 * Stockage des données
 */
public class DataManagementInterfaceImpl implements DataManagementInterface {

   /**
    * Chemin de sauvegarde du fichier
    */
   private String filePath;

   /**
    * {@inheritDoc}
    */
   @Override
   @SuppressWarnings("PMD.EmptyCatchBlock")
   public void saveDocuments(RNDTypeDocument[] typesDocuments, String version)
         throws AdrnToRcndException {

      FileWriter fileWriter = null;
      StreamResult streamResult = null;

      try {
         File file = new File(filePath);
         fileWriter = new FileWriter(file);

         streamResult = new StreamResult(fileWriter);
         SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory
               .newInstance();

         // SAX2.0 ContentHandler.
         TransformerHandler handler = factory.newTransformerHandler();
         Transformer serializer = handler.getTransformer();
         serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
         serializer.setOutputProperty(OutputKeys.INDENT, "yes");
         handler.setResult(streamResult);
         handler.startDocument();
         AttributesImpl atts = new AttributesImpl();
         // USERS tag.
         handler.startElement(StringUtils.EMPTY, StringUtils.EMPTY, "RCND",
               atts);

         for (RNDTypeDocument rndTypeDocument : typesDocuments) {
            addTypeDocument(handler, rndTypeDocument, version, atts);
         }

         handler.endElement(StringUtils.EMPTY, StringUtils.EMPTY, "RCND");
         handler.endDocument();

      } catch (TransformerConfigurationException e) {
         throw new AdrnToRcndException(e);
      } catch (IllegalArgumentException e) {
         throw new AdrnToRcndException(e);
      } catch (IOException e) {
         throw new AdrnToRcndException(e);
      } catch (TransformerFactoryConfigurationError e) {
         throw new AdrnToRcndException(e);
      } catch (SAXException e) {
         throw new AdrnToRcndException(e);

      } finally {

         try {
            if (fileWriter != null) {
               fileWriter.close();
            }
         } catch (IOException exception) {
            // nothing to do
         }

      }

   }

   /**
    * @param handler
    * @param rndTypeDocument
    * @param atts
    * @throws SAXException
    */
   private void addTypeDocument(TransformerHandler handler,
         RNDTypeDocument rndTypeDocument, String version, AttributesImpl atts)
         throws SAXException {

      atts.clear();

      handler.startElement(StringUtils.EMPTY, StringUtils.EMPTY,
            "TypeDocument", atts);

      addElement(handler, "CodeRND", rndTypeDocument.get_reference(), atts);

      String value = rndTypeDocument.get_refFonction();
      String[] tabValues = rndTypeDocument.get_reference().split("\\.");
      if (StringUtils.isNotBlank(value)) {
         value = tabValues[0];
      }
      addElement(handler, "CodeFonction", value, atts);

      value = rndTypeDocument.get_refActivite();
      if (StringUtils.isNotBlank(value)) {
         value = tabValues[1];
      }

      addElement(handler, "CodeActivite", value, atts);
      addElement(handler, "LibelleRND", rndTypeDocument.get_label(), atts);
      addElement(handler, "DureeConservation", null, atts);
      addElement(handler, "VersionRND", version, atts);

      handler.endElement(StringUtils.EMPTY, StringUtils.EMPTY, "TypeDocument");
   }

   private void addElement(TransformerHandler handler, String name,
         String value, AttributesImpl atts) throws SAXException {
      handler.startElement(StringUtils.EMPTY, StringUtils.EMPTY, name, atts);

      if (StringUtils.isNotBlank(value)) {
         char[] tabValue = value.toCharArray();
         handler.characters(tabValue, 0, tabValue.length);
      }
      handler.endElement(StringUtils.EMPTY, StringUtils.EMPTY, name);
   }

   /**
    * @return the filePath
    */
   public String getFilePath() {
      return filePath;
   }

   /**
    * @param filePath
    *           the filePath to set
    */
   public void setFilePath(String filePath) {
      this.filePath = filePath;
   }

}
