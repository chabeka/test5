package fr.urssaf.image.commons.file.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.urssaf.image.commons.file.xml.XMLUtil;

public class XMLUtilTest {

	@Test
	public void write() throws IOException, ParserConfigurationException {

		XMLUtil.write(getExempleXmlStatic(), "C:/test.xml");
	}

	private Document getExempleXmlStatic() throws ParserConfigurationException {
		Document document = null;
		DocumentBuilderFactory fabrique = null;

		fabrique = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = fabrique.newDocumentBuilder();
		document = builder.newDocument();

		Element racine = (Element) document.createElement("bibliotheque");
		document.appendChild(racine);

		// livre n°1
		Element livre1 = (Element) document.createElement("livre");
		livre1.setAttribute("style", "romantisme du XXe");
		Attr attribut1 = document.createAttribute("type");
		attribut1.setValue("poche");
		livre1.setAttributeNode(attribut1);

		racine.appendChild(livre1);
		Element titre1 = (Element) document.createElement("titre");
		titre1.appendChild(document.createTextNode("Madame Bovary"));
		livre1.appendChild(titre1);

		Element auteur1 = (Element) document.createElement("auteur");
		auteur1.appendChild(document.createTextNode("Gustave Flaubert"));
		auteur1.setAttribute("nat", "FR");
		livre1.appendChild(auteur1);

		// livre n°2
		Element livre2 = (Element) document.createElement("livre");
		livre2.setAttribute("style", "naturalisme");
		Attr attribut2 = document.createAttribute("type");
		attribut2.setValue("poche");
		livre2.setAttributeNode(attribut2);

		racine.appendChild(livre2);
		Element titre2 = (Element) document.createElement("titre");
		titre2.appendChild(document.createTextNode("l'argent"));
		livre2.appendChild(titre2);

		Element auteur2 = (Element) document.createElement("auteur");
		auteur2.appendChild(document.createTextNode("Emile Zola"));
		auteur2.setAttribute("nat", "FR");
		livre2.appendChild(auteur2);

		racine.appendChild(document
				.createComment("on place ici un commentaire"));

		return document;
	}
}
