package fr.urssaf.image.commons.file.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public final class JDOMUtil {

	private JDOMUtil() {

	}

	/**
	 * 
	 * @param document
	 * @param fichier
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void write(Document document, String fichier)
			throws FileNotFoundException, IOException {

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(document, new FileOutputStream(fichier));

	}

	/**
	 * 
	 * @param fichierXML
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Element read(File fichierXML) throws JDOMException,
			IOException {
		SAXBuilder sxb = new SAXBuilder();
		Document document = sxb.build(fichierXML);
		return document.getRootElement();
	}

}
