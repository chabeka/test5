package fr.urssaf.image.commons.maquette.richard.benjamin.school.java;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class JerichoTest {

	private String sampleScript = "main_sample.html" ;
	private InputStream in ;
	private Source sc ;
	
	@Before
	public void setUp() throws Exception {
		in = getClass().getResourceAsStream("/src/test/resources/" + sampleScript );
		if( in == null )
			throw new Exception( "Le script d'exemple n'a pas été trouvé, les tests unitaires ne peuvent être lancés" );
		sc = new Source( in ) ;
	}
	
	@After
	public void tearDown() throws Exception {
		in.close();
		in = null ;
		sc.clearCache();
		sc = null ;
	}
	
	@Ignore
	@Test
	public void headSize()
	{
		List<Element> el = sc.getAllElements("head");

		assertEquals( 1, el.size() ) ;
	}
	
	@Ignore
	@Test
	public void headLinkSize()
	{
		List<Element> elHead = sc.getAllElements("head");
		Element head = elHead.get(0) ;
		List<Element> elLink = head.getAllElements("link") ;

		assertEquals( 11, elLink.size() ) ;
	}
	
	@Ignore
	@Test
	public void headLinkContent()
	{
		List<Element> elHead = sc.getAllElements("head");
		Element head = elHead.get(0) ;
		List<Element> elLink = head.getAllElements("link") ;
		
		String linkString = "";
		for (Element el : elLink) 
       	{
			linkString += el.toString() + "\n" ;
       	}
		
		String expectedString = 
			"<link href=\"css/form.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/form-font.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/menu.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/menu-font.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/main.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/main-font.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/menu-color.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/menu-font-color.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/main-color.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/main-font-color.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"<link href=\"css/<%= css %>\" rel=\"stylesheet\" type=\"text/css\" />\n" ;
		assertEquals( expectedString, linkString ) ;
	}
	
	@Ignore
	@Test
	public void bodySize()
	{
		List<Element> elBody = sc.getAllElements("body");

		assertEquals( 1, elBody.size() ) ;
	}

	/*
	 * @TODO	les 2 chaînes correspondent mais j'ai quand même un échec, à corriger
	 */
	@Ignore
	@Test
	public void getElementById()
	{
		Element el = sc.getElementById("header");
		String headerContent = el.getContent().toString();
		String expectedString = 
			"\n" +
			"   <img id=\"logoimage\" src=\"img/logo_image.png\" alt=\"logo-image\" title=\"logo-image\" /> \n" +
			"   <h1 id=\"title-app\" title=\"Titre de l'application\">Titre de l'application</h1>\n" +
			"   <img id=\"logoappli\" src=\"img/logo_aed.png\" alt=\"logo-aed\" title=\"logo-aed\" /> \n" +
			"\n" +
			"<!--  menu : zone affichant le menu de navigation de type liste non ordonnee (ul/li) -->\n" +
			"   <div id=\"menu\" title=\"Menu de navigation de type liste non ordonn&egrave;e\">\n" +
			"\n" +
			"   </div>\n" +
			"<!--  menu : fin -->\n" ;
		assertEquals( "echec", expectedString, headerContent ) ;
	}
	
	@Ignore
	@Test
	public void getAttibute()
	{
		Element el = sc.getElementById("title-app");
		Attributes att = el.getAttributes() ;
		String titleAttContent = att.get("title").getValue() ;

		String expectedString = "Titre de l'application de mon attribut title" ;
		assertEquals( "echec", expectedString, titleAttContent ) ;
	}
	
	@Ignore
	@Test
	public void replaceAttibute()
	{	
		OutputDocument od = new OutputDocument( sc ) ;
		String newTitleContent = "Ceci sera mon nouveau titre" ;
		
		Element el = sc.getElementById("title-app");
		Attributes att = el.getAttributes() ;
		int start = att.get("title").getBegin() ;
		int end = att.get("title").getEnd() ;

		od.replace( start, end, newTitleContent ) ;
		
		String expectedString = "Titre de l'application de mon attribut title" ;
		assertEquals( "echec", expectedString, od.toString() ) ;
	}
	
	@Ignore
	@Test
	public void replaceElementContent()
	{
		OutputDocument od = new OutputDocument( sc ) ;
		String newTitleContent = "Ceci sera mon nouveau titre" ;
		
		Element el = sc.getElementById("title-app") ;
		int start = el.getContent().getBegin() ;
		int end = el.getContent().getEnd() ;
		
		od.replace( start, end, newTitleContent ) ;
		
		String expectedString = "Titre de l'application de mon attribut title" ;
		assertEquals( "echec", expectedString, od.toString() ) ;
	}
}
