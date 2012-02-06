package fr.urssaf.image.commons.spring.batch.support.stax;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;

import fr.urssaf.image.commons.spring.batch.model.flat.Livre;

public class BibliothequeXMLSupport {

   private static final String B_NAMESPACE = "http://www.cirtil.fr/sae/common/bibliotheque";

   private static final String REF_NAMESPACE = "http://www.cirtil.fr/sae/common/bibliotheque_element";

   private static final String REF_PREFIX = "ref";

   private static final String B_PREFIX = "b";

   public void writer(File bibliothequeXMLPath, int nombreDeLivres) {

      OutputStream libraryXML;
      try {
         libraryXML = FileUtils.openOutputStream(bibliothequeXMLPath);
      } catch (IOException e) {
         throw new NestableRuntimeException(e);
      }

      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

      try {
         XMLStreamWriter writer = outputFactory
               .createXMLStreamWriter(libraryXML);

         try {

            this.headerWriter(writer);

            for (int i = 1; i <= nombreDeLivres; i++) {

               Livre livre = new Livre();
               livre.setIdentifiant(i);
               livre.setAuteur("auteur_" + i);
               livre.setTitre("titre_" + i);

               this.itemWriter(livre, writer);

            }

            this.footerWriter(writer);

         } catch (XMLStreamException e) {
            throw new NestableRuntimeException(e);
         } finally {
            writer.close();

         }

      } catch (XMLStreamException e) {
         throw new NestableRuntimeException(e);
      }

   }

   private void headerWriter(XMLStreamWriter writer) throws XMLStreamException {

      writer.writeStartDocument("UTF-8", "1.0");

      writer.writeStartElement(B_PREFIX, "bibliotheque", B_NAMESPACE);
      writer.writeNamespace(B_PREFIX, B_NAMESPACE);
      writer.writeNamespace(REF_PREFIX, REF_NAMESPACE);

      // balise adresse
      writer.writeStartElement(B_PREFIX, "adresse", B_NAMESPACE);
      writer.writeCharacters("adresse de la bibliotheque");
      writer.writeEndElement();

      writer.writeStartElement(B_PREFIX, "livres", B_NAMESPACE);

   }

   private void footerWriter(XMLStreamWriter writer) throws XMLStreamException {

      writer.writeEndElement();
      writer.writeEndDocument();

   }

   private void itemWriter(Livre livre, XMLStreamWriter writer)
         throws XMLStreamException {

      // ouverture de la balise livre
      writer.writeStartElement(REF_PREFIX, "livre", REF_NAMESPACE);
      writer.writeAttribute("id", ObjectUtils.toString(livre.getIdentifiant(),
            "nc"));

      // balise titre
      writer.writeStartElement(REF_PREFIX, "titre", REF_NAMESPACE);
      writer.writeCharacters(livre.getTitre());
      writer.writeEndElement();

      // balise auteur
      writer.writeStartElement(REF_PREFIX, "auteur", REF_NAMESPACE);
      writer.writeCharacters(livre.getAuteur());
      writer.writeEndElement();

      writer.writeEndElement();

   }
}
