package fr.urssaf.image.commons.file.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.JDOMException;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.urssaf.image.commons.file.modele.Bibliotheque;
import fr.urssaf.image.commons.file.modele.Livre;

public class XMLUtilTest {
	
	
	private static final String LIVRE = "livre";
	
	private static final String TYPE = "poche";

	@Test
	public void write() throws ParserConfigurationException {

		try {
			XMLUtil.write(getExempleXmlStatic(), "C:/test.xml");
		} catch (IOException e) {
			fail("le test est un echec");
		}
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
		Element livre1 = (Element) document.createElement(LIVRE);
		livre1.setAttribute("style", "romantisme du XXe");
		Attr attribut1 = document.createAttribute("type");
		attribut1.setValue(TYPE);
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
		Element livre2 = (Element) document.createElement(LIVRE);
		livre2.setAttribute("style", "naturalisme");
		Attr attribut2 = document.createAttribute("type");
		attribut2.setValue(TYPE);
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

	@Test
	public void read() throws JDOMException, IOException{

		File file = new File("src/test/resources/test.xml");
		org.jdom.Element element = XMLUtil.read(file);

		assertEquals("bibliotheque", element.getName());

		@SuppressWarnings("unchecked")
		List<org.jdom.Element> livres = element.getChildren();

		org.jdom.Element livre1 = livres.get(0);
		assertEquals("livre", livre1.getName());
		assertLivre(livre1, "romantisme du XXe", "Madame Bovary",
				"Gustave Flaubert");

		org.jdom.Element livre2 = livres.get(1);
		assertEquals("livre", livre2.getName());
		assertLivre(livre2, "naturalisme", "l'argent", "Emile Zola");

		assertEquals("il y a plus de livres que prévus", 2, livres.size());

		assertNotNull(element);

	}

	private void assertLivre(org.jdom.Element livre, String style, String titre,
			String auteur) {

		// on test les attributs style et type
		@SuppressWarnings("unchecked")
		List<org.jdom.Attribute> attributes = livre.getAttributes();

		org.jdom.Attribute styleAttribut = attributes.get(0);
		assertEquals("style", styleAttribut.getName());
		assertEquals(style, styleAttribut.getValue());

		org.jdom.Attribute typeAttribut = attributes.get(1);
		assertEquals("type", typeAttribut.getName());
		assertEquals(TYPE, typeAttribut.getValue());

		assertEquals("il y a plus d'attributs que prévus", 2, attributes.size());

		@SuppressWarnings("unchecked")
		List<org.jdom.Element> elements = livre.getChildren();
		// on test le titre
		org.jdom.Element titreElement = elements.get(0);
		assertEquals(titre, titreElement.getValue());

		// on test l'auteur
		org.jdom.Element auteurElement = elements.get(1);
		assertEquals(auteur, auteurElement.getValue());

		assertEquals("il y a plus de caractéristiques que prévues", 2, elements
				.size());

	}

	@Test
	public void populate() throws JDOMException, IOException {

		File file = new File("src/test/resources/test.xml");
		org.jdom.Element element = XMLUtil.read(file);

		XMLFactory<Bibliotheque> xmlFactory = new XMLFactory<Bibliotheque>(
				element, Bibliotheque.class);

		Bibliotheque bibliotheque = xmlFactory.getInstance();

		assertNotNull(bibliotheque);

		Livre[] livres = bibliotheque.getLivre();

		assertLivre(livres[0], "romantisme du XXe", "Madame Bovary",
				"Gustave Flaubert");

		assertLivre(livres[1], "naturalisme", "l'argent", "Emile Zola");

		assertEquals("il y a plus de livres que prévus", 2, livres.length);

	}

	private void assertLivre(Livre livre, String style, String titre,
			String auteur) {

		assertEquals(TYPE, livre.getType());
		assertEquals(style, livre.getStyle());
		assertEquals(titre, livre.getTitre().getValue());
		assertEquals(auteur, livre.getAuteur().getValue());
		assertEquals("FR", livre.getAuteur().getNat());
		
	}

}
