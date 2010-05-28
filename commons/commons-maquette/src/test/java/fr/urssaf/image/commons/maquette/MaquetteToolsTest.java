package fr.urssaf.image.commons.maquette;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.commons.maquette.tool.MaquetteTools;


public class MaquetteToolsTest{
	
	@Test
	public void testConstantString()
	{
		String filename = "/toto.jpg" ;
		assertEquals( "/resource/toto.jpg", MaquetteTools.getResourcePath(filename) ) ;
	}
	
	@Ignore
	@Test
	public void charBuffer()
	{
		String expected = "body\n"+
"{\n" +
"	background: cssMainBackgroundColor;\n"+
"}\n"+
"\n"+
"#container\n"+
"{\n"+
"	background: cssInfoboxBackgroundColor ; /* simule la colonne de gauche */\n"+
"}\n"+
"\n"+
"#test\n"+
"{\n"+
"	background: cssMainBackgroundColor ;\n"+
"}";
		String resourceFile = "/main-color.css" ;
    	String filename = MaquetteTools.getResourcePath( resourceFile );
		InputStream in = getClass().getResourceAsStream( filename );
		
		InputStreamReader isr;
		try {
			StringBuffer buffer = new StringBuffer();
			isr = new InputStreamReader(in, "ISO-8859-15");
			Reader r = new BufferedReader(isr);
			int ch;
			try {
				while ((ch = r.read()) > -1) {
					buffer.append((char)ch);
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			} finally{
				try {
					r.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
			
			assertEquals(expected, buffer.toString() );
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			fail( "InputStreamReader failed" );
		}
    	
	}
	
	@Test
	public void stringReplace()
	{
		String expected = "body\n"+
		"{\n" +
		"	background: toto;\n"+
		"}\n"+
		"\n"+
		"#container\n"+
		"{\n"+
		"	background: cssInfoboxBackgroundColor ; /* simule la colonne de gauche */\n"+
		"}\n"+
		"\n"+
		"#test\n"+
		"{\n"+
		"	background: toto ;\n"+
		"}";
		String resourceFile = "body\n"+
		"{\n" +
		"	background: cssMainBackgroundColor;\n"+
		"}\n"+
		"\n"+
		"#container\n"+
		"{\n"+
		"	background: cssInfoboxBackgroundColor ; /* simule la colonne de gauche */\n"+
		"}\n"+
		"\n"+
		"#test\n"+
		"{\n"+
		"	background: cssMainBackgroundColor ;\n"+
		"}";
    	
    	resourceFile = resourceFile.replace("cssMainBackgroundColor", "toto") ;
    	
    	assertEquals( expected, resourceFile) ;
	}
	
}
