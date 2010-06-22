package fr.urssaf.image.commons.file.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.junit.Test;

import fr.urssaf.image.commons.file.modele.Bibliotheque;
import fr.urssaf.image.commons.file.modele.Livre;

public class JDOMUtilTest {

	private static final Logger log = Logger.getLogger(JDOMUtilTest.class);

	private static final String LIVRE = "livre";

	private static final String TYPE = "poche";

	private static final String FILE = "bibliotheque_jdom.xml";

	@Test
	public void write() {

		try {
			JDOMUtil.write(createDocument(), "C:/" + FILE);
		} catch (FileNotFoundException e) {
			log.error(e);
			fail("echec du test");
		} catch (IOException e) {
			log.error(e);
			fail("echec du test");
		}

	}

	private Document createDocument() {

		Element bibliotheque = new Element("bibliotheque");

		// livre n°0
		Element livre0 = createLivre("romantisme du XXe", "Madame Bovary",
				"Gustave Flaubert");
		bibliotheque.addContent(livre0);

		// livre n°1
		Element livre1 = createLivre("naturalisme", "l'argent", "Emile Zola");
		bibliotheque.addContent(livre1);

		// Commentaire
		Comment comment = new Comment("on place ici un commentaire");
		bibliotheque.addContent(comment);

		// CDATA
		CDATA cData = new CDATA("caractères spéciaux:< > ' & + é @ £ $");
		bibliotheque.addContent(cData);

		Document document = new Document(bibliotheque);

		return document;
	}

	private Element createLivre(String style, String titre, String auteur) {

		Element livre = new Element(LIVRE);
		livre.setAttribute("style", style);
		Attribute type = new Attribute("type", TYPE);
		livre.setAttribute(type);

		Element titreElement = new Element("titre");
		titreElement.addContent(titre);
		livre.addContent(titreElement);

		Element auteurElement = new Element("auteur");
		auteurElement.addContent(auteur);
		auteurElement.setAttribute("nat", "FR");
		livre.addContent(auteurElement);

		return livre;
	}

	@Test
	public void read() throws JDOMException, IOException {

		File file = new File("src/test/resources/" + FILE);
		Element element = JDOMUtil.read(file);

		assertEquals("bibliotheque", element.getName());

		@SuppressWarnings("unchecked")
		List<Element> livres = element.getChildren();

		Element livre1 = livres.get(0);
		assertEquals("livre", livre1.getName());
		assertLivre(livre1, "romantisme du XXe", "Madame Bovary",
				"Gustave Flaubert");

		Element livre2 = livres.get(1);
		assertEquals("livre", livre2.getName());
		assertLivre(livre2, "naturalisme", "l'argent", "Emile Zola");

		assertEquals("il y a plus de livres que prévus", 2, livres.size());

		assertNotNull(element);

	}

	private void assertLivre(Element livre, String style, String titre,
			String auteur) {

		// on test les attributs style et type
		@SuppressWarnings("unchecked")
		List<Attribute> attributes = livre.getAttributes();

		Attribute styleAttribut = attributes.get(0);
		assertEquals("style", styleAttribut.getName());
		assertEquals(style, styleAttribut.getValue());

		Attribute typeAttribut = attributes.get(1);
		assertEquals("type", typeAttribut.getName());
		assertEquals(TYPE, typeAttribut.getValue());

		assertEquals("il y a plus d'attributs que prévus", 2, attributes.size());

		@SuppressWarnings("unchecked")
		List<Element> elements = livre.getChildren();
		// on test le titre
		Element titreElement = elements.get(0);
		assertEquals(titre, titreElement.getValue());

		// on test l'auteur
		Element auteurElement = elements.get(1);
		assertEquals(auteur, auteurElement.getValue());

		assertEquals("il y a plus de caractéristiques que prévues", 2, elements
				.size());

	}

	@Test
	public void populate() throws JDOMException, IOException {

		File file = new File("src/test/resources/" + FILE);
		Element element = JDOMUtil.read(file);

		JDOMFactory<Bibliotheque> xmlFactory = new JDOMFactory<Bibliotheque>(
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
