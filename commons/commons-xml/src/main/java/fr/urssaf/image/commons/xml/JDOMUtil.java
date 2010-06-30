package fr.urssaf.image.commons.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public final class JDOMUtil {

   private JDOMUtil()
   {
      
   }
   
	/**
	 * Persiste un objet JDOM Document dans un fichier
	 * @param document
	 * @param fichier
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeToFile(Document document, File file)
			throws FileNotFoundException, IOException {
	   
	   Format format = Format.getPrettyFormat();

	   // Pour info, le séparateur de ligne utilisé est : format.getLineSeparator()
	   // Par défaut : "\r\n" 
	   
	   XMLOutputter out = new XMLOutputter(format);
		out.output(document, new FileOutputStream(file));
	}
	
	
	/**
	 * Persiste un objet JDOM Document dans une chaîne de caractères
	 * @param document l'objet représentant le document XML
	 * @return le XML sous forme de chaîne de caractères
	 * @throws IOException 
	 */
	public static String writeToString(Document document) throws IOException
   {
      XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
      StringWriter stringWriter = new StringWriter(0);
      out.output(document, stringWriter);
      return stringWriter.toString();
   }

	/**
	 * Lit du XML à partir d'un fichier et le restitue dans un objet JDOM Document
	 * @param fichierXML le chemin complet du fichier XML à lire
	 * @return l'objet JDOM Document représentant de le document XML
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document readFromFile(File fichierXML) throws JDOMException, IOException {
	   SAXBuilder sxb = new SAXBuilder();
	   return sxb.build(fichierXML);
	}
	
	
	/**
	 * Lit du XML à partir d'une chaîne de caractères UTF8 et le restitue dans un objet JDOM Document
	 * @param xml le XML en chaîne de caractères
	 * @return l'objet JDOM Document représentant de le document XML 
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document readFromStringUTF8(String xml) throws JDOMException, IOException
	{
	   SAXBuilder sxb = new SAXBuilder();
      InputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
      return sxb.build(inputStream);
	}
	
	
	

}
