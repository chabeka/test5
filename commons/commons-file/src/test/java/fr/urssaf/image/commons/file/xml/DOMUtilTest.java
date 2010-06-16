package fr.urssaf.image.commons.file.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMUtilTest {

	private static final String LIVRE = "livre";

	private static final String TYPE = "poche";

	private static final String FILE = "bibliotheque_w3c.xml";;

	private static final Logger log = Logger.getLogger(DOMUtilTest.class);

	@Test
	public void write() throws ParserConfigurationException {

		try {
			DOMUtil.write(getExempleXmlStatic(), "C:/" + FILE);
		} catch (IOException e) {
			log.error(e);
			fail("le test est un echec");

		}
	}

	private Document getExempleXmlStatic() throws ParserConfigurationException {

		DocumentBuilderFactory documentFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		Document document = builder.newDocument();

		Element bibliotheque = document.createElement("bibliotheque");

		// livre n°0

		Element livre0 = createLivre(document, "romantisme du XXe",
				"Madame Bovary", "Gustave Flaubert");
		bibliotheque.appendChild(livre0);

		// livre n°1

		Element livre1 = createLivre(document, "naturalisme", "l'argent",
				"Emile Zola");
		bibliotheque.appendChild(livre1);

		// Commentaire
		bibliotheque.appendChild(document
				.createComment("on place ici un commentaire"));

		document.appendChild(bibliotheque);

		return document;

	}

	private Element createLivre(Document document, String style, String titre,
			String auteur) {

		Element livre = document.createElement(LIVRE);
		livre.setAttribute("style", style);
		livre.setAttribute("type", TYPE);

		Element titreElement = document.createElement("titre");
		titreElement.appendChild(document.createTextNode(titre));
		livre.appendChild(titreElement);

		Element auteurElement = document.createElement("auteur");
		auteurElement.appendChild(document.createTextNode(auteur));
		auteurElement.setAttribute("nat", "FR");
		livre.appendChild(auteurElement);

		return livre;
	}

	@Test
	public void read() throws ParserConfigurationException, SAXException, IOException {

		File file = new File("src/test/resources/" + FILE);
		Element element = DOMUtil.read(file);

		assertEquals("bibliotheque", element.getNodeName());
		
		

		NodeList livres = element.getElementsByTagName(LIVRE);
		
		Element livre1 = (Element) livres.item(0);
		assertEquals("livre", livre1.getNodeName());
		assertLivre(livre1, "romantisme du XXe", "Madame Bovary",
				"Gustave Flaubert");

		Element livre2 = (Element) livres.item(1);
		assertEquals("livre", livre2.getNodeName());
		assertLivre(livre2, "naturalisme", "l'argent", "Emile Zola");

		assertEquals("il y a plus de livres que prévus", 2, livres.getLength());

		assertNotNull(element);

	}

	private void assertLivre(Element livre, String style, String titre,
			String auteur) {

		// on test les attributs style et type
		NamedNodeMap attributes = livre.getAttributes();

		Node styleAttribut = attributes.getNamedItem("style");
		assertEquals("style", styleAttribut.getNodeName());
		assertEquals(style, styleAttribut.getNodeValue());

		Node typeAttribut = attributes.getNamedItem("type");
		assertEquals("type", typeAttribut.getNodeName());
		assertEquals(TYPE, typeAttribut.getNodeValue());

		assertEquals("il y a plus d'attributs que prévus", 2, attributes
				.getLength());

		
		// on test le titre
		Node titreElement = livre.getElementsByTagName("titre").item(0);
		assertEquals(titre, titreElement.getTextContent());

		// on test l'auteur
		Node auteurElement = livre.getElementsByTagName("auteur").item(0);
		assertEquals(auteur, auteurElement.getTextContent());

	}

}
