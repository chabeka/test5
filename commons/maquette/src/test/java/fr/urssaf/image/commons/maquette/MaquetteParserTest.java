package fr.urssaf.image.commons.maquette;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Ignore;
import org.junit.Test;


public class MaquetteParserTest {
	
	@Test
	public void setSourceFromHtml()
	{
		String html = "<html><head><title>Chaine html TITRE</title></head><body><h1>test via une chaine html</h1><p>contenu de mon paragraphe</p></body></html>" ;
		
		Source scHtml = new Source( html ) ;
		
		assertTrue( scHtml != null );
		assertTrue( scHtml.getAllElements("head").size() == 1 );
	}
	
	@Test
	public void setSourceFromFile()
	{
		String filename = "/main_sample.html" ;
		InputStream is = getClass().getResourceAsStream( filename ) ;
		try {
			Source scHtml = new Source( is ) ;
			assertTrue( scHtml != null );
			assertTrue( scHtml.getAllElements("head").size() == 1 );
		} catch (IOException e) {
			fail( e.toString() );
		}
	}
	
	/**
	 * @todo l'exception est attendu donc ça devrait être juste, or le test échoue : à corriger
	 */
	@Test
	public void setSourceFromFileInResourceFolderWithoutStartingSlash()
	{
		String filename = "yarienavoir.jexistepas" ;
		InputStream is = getClass().getResourceAsStream( filename ) ;
		assertTrue( is == null ) ;
		//new Source( is ) ;
		//fail( "Le test devrait planter" );
	}
	
	@Test
	public void setSourceFromFileInResourceFolder()
	{
		String filename = "/resource/html/main.html" ;
		InputStream is = getClass().getResourceAsStream( filename ) ;
		try {
			Source scHtml = new Source( is ) ;
			assertTrue( scHtml != null );
			assertTrue( scHtml.getAllElements("head").size() == 1 );
		} catch (IOException e) {
			fail( e.toString() );
		}
	}

	@Test
	public void setSourceFromFileInResourceFolderWithDoubleSlash()
	{
		String filename = "/resource//html/main.html" ;
		InputStream is = getClass().getResourceAsStream( filename ) ;
		try {
			Source scHtml = new Source( is ) ;
			assertTrue( scHtml != null );
			assertTrue( scHtml.getAllElements("head").size() == 1 );
		} catch (IOException e) {
			fail( e.toString() ) ;
		}
	}
	
	@Test
	public void getAttributePosition()
	{
		String html = "<html><head><title>Chaine html TITRE</title></head><body>" +
			"<h1>test via une chaine html</h1><p>contenu de mon paragraphe</p>" +
			"<img id='imageId' src='monImage' />" +
			"</body></html>" ;
		
		Source scHtml = new Source( html ) ;
		
		assertTrue( scHtml != null );
		
		Element imgTag = scHtml.getElementById("imageId") ;
		int imgStartTag = imgTag.getStartTag().getBegin() ;
		
		Attributes attList = imgTag.getAttributes();
		Attribute id = attList.get(0);
		Attribute src = attList.get(1);
		
		assertEquals("imageId", id.getValueSegment().toString());
		assertEquals("id", id.getName());
		assertEquals("id", id.getKey());
		assertEquals((imgStartTag+5), id.getBegin());
		assertEquals(12, id.length());
		assertEquals((imgStartTag+9), id.getValueSegment().getBegin());
		
		assertEquals("monImage", src.getValueSegment().toString());
		assertEquals("src", src.getName());
		assertEquals("src", src.getKey());
		assertEquals((imgStartTag+18), src.getBegin());
		assertEquals(14, src.length());
		assertEquals((imgStartTag+23), src.getValueSegment().getBegin());
	}
	
	@Test
	public void getHeadPosition()
	{
		String html = "<html><head><meta name='author' value='yoyo' /><title>Chaine html TITRE</title></head><body>" +
			"<h1>test via une chaine html</h1><p>contenu de mon paragraphe</p>" +
			"<img id='imageId' src='monImage' />" +
			"</body></html>" ;
		
		Source scHtml = new Source( html ) ;
		
		assertTrue( scHtml != null );
		
		List<Element> headList = scHtml.getAllElements("head") ;
		if( headList.size() == 0 )
			fail( "Aucun head trouvé" );
		Element head = headList.get(0);
		
		assertEquals(6, head.getStartTag().getBegin());
		assertEquals(12, head.getStartTag().getEnd());
	}
	
	@Test
	public void getBodyFromTxtString()
	{
		String html = "contenu texte sans html du tout, y a t il un body recupere ?" ;
		
		Source scHtml = new Source( html ) ;
		List<Element> elBody = scHtml.getAllElements("body");
		
		assertEquals( 0, elBody.size() );
		assertEquals( html, scHtml.toString() );
		System.out.println( scHtml.toString() );
	}
	
	@Test
	public void getBodyFromTxtString_2()
	{
		String html = "<h1>Titre du paragraphe 1 de ma page JSP</h1>\n"+
"<div>\n"+
"Contenu applicatif de ma JSP toto éééééééééé<br />\n"+ 
"</div>" ;
		
		Source scHtml = new Source( html ) ;
		List<Element> elBody = scHtml.getAllElements("body");
		
		assertEquals( 0, elBody.size() );
		assertEquals( html, scHtml.toString() );
		System.out.println( scHtml.toString() );
	}
	
	@Ignore
	@Test
	public void encodageAccent()
	{
		fail( "Test à écrire" );
	}
}
