package fr.urssaf.image.commons.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.commons.xml.modele.Bibliotheque;
import fr.urssaf.image.commons.xml.modele.Livre;

public class JDOMUtilTest {

   private static final String UNCHECKED = "unchecked";
   
	private static final String BIBLIOTHEQUE = "bibliotheque";
   private static final String LIVRE = "livre";
	private static final String TYPE = "poche";
   private static final String ATTRIBUT_STYLE = "style";
   private static final String ATTRIBUT_TYPE = "type";
	
	private static final String FILE = "bibliotheque_jdom.xml";
	
	private static final String XML_CRLF = "\r\n";

	@Test
	@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
	@Ignore
	public void writeToFile() throws FileNotFoundException, IOException
	{
	   JDOMUtil.writeToFile(createDocument(), new File("C:/" + FILE));
	}
	
	@Test
   public void writeToString() throws IOException {

	   // ------------------------------------------------------------------------
	   // Création d'un document XML avec les objets JDOM
	   // ------------------------------------------------------------------------
	   
	   // Création du Document
	   Document document = new Document();
	   
	   // Création de l'élément racine bibliotheque
	   Element bibliotheque = new Element(BIBLIOTHEQUE);
	   document.setRootElement(bibliotheque);
	   
	   // Création d'un élément livre avec une valeur
	   Element livre = new Element(LIVRE);
	   bibliotheque.addContent(livre);
	   livre.addContent("leLivre éè");
	   
	   // ------------------------------------------------------------------------
      // Appel de la méthode à tester
      // ------------------------------------------------------------------------
	   
	   String xml = JDOMUtil.writeToString(document);
      
	   
	   // ------------------------------------------------------------------------
      // Vérification du résultat
      // ------------------------------------------------------------------------
	   
      String xmlLineSeparator = "\r\n";
      
      String expected = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
         xmlLineSeparator + 
         "<bibliotheque>" + 
         xmlLineSeparator +
         "  <livre>leLivre éè</livre>" +
         xmlLineSeparator +
         "</bibliotheque>" + 
         xmlLineSeparator + 
         xmlLineSeparator;
      
      String actual = xml;
      
      String message = "Le XML généré est incorrect";
	   
      assertEquals(message,expected,actual);

   }

	private Document createDocument() {

		Element bibliotheque = new Element(BIBLIOTHEQUE);

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
		livre.setAttribute(ATTRIBUT_STYLE, style);
		Attribute type = new Attribute(ATTRIBUT_TYPE, TYPE);
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
		Document document = JDOMUtil.readFromFile(file);
		
		Element element = document.getRootElement(); 
		
		assertNotNull("Le noeud racine n'a pas été trouvé",element);

		assertEquals("Problème sur le noeud racine",BIBLIOTHEQUE, element.getName());

		@SuppressWarnings(UNCHECKED)
		List<Element> livres = element.getChildren();

		Element livre1 = livres.get(0);
		assertEquals("Problème sur le 1er noeud livre",LIVRE, livre1.getName());
		assertLivre(livre1, "romantisme du XXe", "Madame Bovary","Gustave Flaubert");

		Element livre2 = livres.get(1);
		assertEquals("Problème sur le 2ème noeud livre",LIVRE, livre2.getName());
		assertLivre(livre2, "naturalisme", "l'argent", "Emile Zola");

		assertEquals("il y a plus de livres que prévus", 2, livres.size());

	}

	private void assertLivre(Element livre, String style, String titre,
			String auteur) {

		// on test les attributs style et type
		@SuppressWarnings(UNCHECKED)
		List<Attribute> attributes = livre.getAttributes();

		Attribute styleAttribut = attributes.get(0);
		assertEquals("Mauvais attribut",ATTRIBUT_STYLE, styleAttribut.getName());
		assertEquals("Problème sur la valeur de l'attribut " + ATTRIBUT_STYLE,style, styleAttribut.getValue());

		Attribute typeAttribut = attributes.get(1);
		assertEquals("Mauvais attribut", ATTRIBUT_TYPE ,typeAttribut.getName());
		assertEquals("Problème sur la valeur de l'attribut " + ATTRIBUT_TYPE, TYPE, typeAttribut.getValue());

		assertEquals("il y a plus d'attributs que prévus", 2, attributes.size());

		@SuppressWarnings(UNCHECKED)
		List<Element> elements = livre.getChildren();
		// on test le titre
		Element titreElement = elements.get(0);
		assertEquals("Problème sur le titre du livre",titre, titreElement.getValue());

		// on test l'auteur
		Element auteurElement = elements.get(1);
		assertEquals("L'auteur est incorrect",auteur, auteurElement.getValue());

		assertEquals("il y a plus de caractéristiques que prévues", 2, elements
				.size());

	}

	@Test
	public void populate() throws JDOMException, IOException {

		File file = new File("src/test/resources/" + FILE);
		Document document = JDOMUtil.readFromFile(file);
		Element element = document.getRootElement();

		JDOMFactory<Bibliotheque> xmlFactory = new JDOMFactory<Bibliotheque>(
				element, Bibliotheque.class);

		Bibliotheque bibliotheque = xmlFactory.getInstance();

		assertNotNull("Noeud racine non trouvé",bibliotheque);

		Livre[] livres = bibliotheque.getLivre();

		assertLivre(livres[0], "romantisme du XXe", "Madame Bovary",
				"Gustave Flaubert");

		assertLivre(livres[1], "naturalisme", "l'argent", "Emile Zola");

		assertEquals("il y a plus de livres que prévus", 2, livres.length);

	}

	private void assertLivre(Livre livre, String style, String titre,
			String auteur) {

		assertEquals("Mauvais attribut",TYPE, livre.getType());
		assertEquals("Valeur incorrecte pour l'attribut style",style, livre.getStyle());
		assertEquals("Valeur incorrecte pour le titre",titre, livre.getTitre().getValue());
		assertEquals("Valeur incorrecte pour l'auteur du livre",auteur, livre.getAuteur().getValue());
		assertEquals("Valeur incorrecte pour la nationalité de l'auteur","FR", livre.getAuteur().getNat());

	}
	
	@Test
	public void readFromStringUTF8Test() throws JDOMException, IOException
	{
	   
	   String xml = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
         XML_CRLF + 
         "<bibliotheque>" + 
         XML_CRLF +
         "  <livre>leLivre éè</livre>" +
         XML_CRLF +
         "</bibliotheque>" + 
         XML_CRLF + 
         XML_CRLF;
	   
	   Document document = JDOMUtil.readFromStringUTF8(xml);
	   
	   // Vérification sur le document
	   assertNotNull("Echec de la transformation de la String xml en objet Document",document);
	   
	   // Vérification sur le noeud racine
	   Element noeudRacine = document.getRootElement();
	   assertNotNull("Noeud racine non trouvé",noeudRacine);
	   assertEquals("Le noeud racine devrait être un noeud <bibliotheque>",BIBLIOTHEQUE,noeudRacine.getName());
	   
	   // Vérifie qu'il y a un moins 1 livre
	   @SuppressWarnings(UNCHECKED)
	   List<Element> livres = noeudRacine.getChildren();
	   assertNotNull("On aurait du trouvé 1 noeud <livre>",livres);
	   assertEquals("On aurait du trouvé 1 noeud <livre>",false,livres.isEmpty());
	   
	   // Vérification sur le livre
	   Element livre = livres.get(0);
	   assertNotNull("On aurait du trouvé 1 noeud <livre>",livres);
	   assertEquals("Le noeud livre devrait être un noeud <livre>",LIVRE,livre.getName());
	   assertEquals("La valeur du noeud livre est incorrecte","leLivre éè",livre.getValue());
	   
	}

}
