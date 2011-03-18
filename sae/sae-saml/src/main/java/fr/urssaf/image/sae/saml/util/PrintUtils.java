package fr.urssaf.image.sae.saml.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.XmlStreamWriter;
import org.w3c.dom.Node;

/**
 * Méthodes courantes pour les sorties (console, fichier ...) des objets de type
 * {@link org.w3c.dom.Node} afin de les afficher sous format XML
 * 
 * 
 */
public final class PrintUtils {

   private PrintUtils() {

   }

   /**
    * méthode d'affichage à destinatation des consoles
    * 
    * @param node
    *           balise xml à afficher
    * @return balise xml
    */
   public static String print(Node node) {

      ByteArrayOutputStream out = new ByteArrayOutputStream();

      print(node, out);

      // inutile de out.close() car la méthode ne fait rien pour
      // ByteArrayOutputStream

      return out.toString();

   }

   /**
    * Méthode générale d'affichage pour n'importe quel type de sortie<br>
    * <br>
    * <a href="http://stackoverflow.com/questions/2325388/java-shortest-way-to-pretty-print-to-stdout-a-org-w3c-dom-document"
    * >source du code</a>
    * 
    * @param node
    *           balise xml à afficher
    * @param out
    *           sortie pour l'affichage
    */
   public static void print(Node node, OutputStream out) {

      TransformerFactory factory = TransformerFactory.newInstance();

      try {

         Transformer transformer = factory.newTransformer();
         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
         transformer.setOutputProperty(
               "{http://xml.apache.org/xslt}indent-amount", "4");

         transformer.transform(new DOMSource(node), new StreamResult(
               new XmlStreamWriter(out, "UTF-8")));
      } catch (TransformerException e) {
         throw new IllegalStateException(e);
      }
   }
}
