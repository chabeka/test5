package fr.urssaf.image.sae.saml.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * Méthodes courantes pour les sorties (console, fichier ...) des objets de type
 * {@link org.w3c.dom.Node} afin de les afficher sous format XML
 * 
 * 
 */
public final class PrintUtil {

   private PrintUtil() {

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
      try {
         out.close();
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }

      return out.toString();

   }

   /**
    * Méthode générale d'affichage pour n'importe quel type de sortie
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
               new OutputStreamWriter(out, "UTF-8")));
      } catch (UnsupportedEncodingException e) {
         throw new IllegalStateException(e);
      } catch (TransformerException e) {
         throw new IllegalStateException(e);
      }
   }
}
