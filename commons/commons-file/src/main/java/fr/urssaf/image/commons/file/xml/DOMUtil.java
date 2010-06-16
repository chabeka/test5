package fr.urssaf.image.commons.file.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public final class DOMUtil {

	private DOMUtil() {

	}

	/**
	 * 
	 * @param doc
	 *            objet XML
	 * @param fichier
	 *            chemin en dur du fichier
	 * @throws IOException
	 */
	public static void write(Document doc, String fichier) throws IOException {
		OutputFormat outf = new OutputFormat();
		outf.setIndent(0);
		outf.setIndenting(true);
		outf.setEncoding("UTF-8");

		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				fichier));
		XMLSerializer xmls = new XMLSerializer(fileOutputStream, outf);
		xmls.serialize(doc);
		fileOutputStream.close();

	}

	public static Element read(File fichierXML)
			throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(fichierXML);

		return document.getDocumentElement();
	}
}
