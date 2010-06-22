package fr.urssaf.image.commons.maquette;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;


public class MaquetteFilterTest{

	private Logger logger = Logger.getLogger(MaquetteFilterTest.class) ;
	
	@Test
	public void getResourceImg_1()
	{
		// URL u = getClass().getResource("/fr/urssaf/cirtil/technocom/img/logo_aed.png");
		URL u = getClass().getResource("/resource/img/logo_aed.png");
		if( u == null )
			fail("Resource non trouvée");
			
	    String filename = u.toString();
	    logger.debug(filename);
	    
	}
	
	@Test
	public void getResourceImg_2()
	{
		URL u = getClass().getResource("/resource/img/leftcol_aed.png");
		if( u == null )
			fail("Resource non trouvée");
			
	    String filename = u.toString();
	    logger.debug(filename);
	}
	
	@Test
	public void getResourceCss_1()
	{
		URL u = getClass().getResource("/resource/css/form.css");
		if( u == null )
			fail("Resource non trouvée");
			
	    String filename = u.toString();
	    logger.debug(filename);
	}
	
	@Test
	public void getResourceHtml_1()
	{
		URL u = getClass().getResource("/resource/html/main.html");
		if( u == null )
			fail("Resource non trouvée");
			
	    String filename = u.toString();
	    logger.debug(filename);
	}
	
	/*
	@Test
	public void getResourceJsp()
	{
		URL u = getClass().getResource("/resource/html/main.jsp");
		if( u == null )
			fail("Resource non trouvée");
			
	    String filename = u.toString();
	    logger.debug(filename);
	}
	*/
	
	@Test
	public void applyFilter_endsWith()
	{
		String reference = "/paulWeb/getResource" ;
		String test = "/getResource" ;
		
		assertTrue( reference.endsWith(test) ) ;
	}
	
	@Test
	public void applyFilter_RegularExpression_1()
	{
		String reference = "/paulWeb/getResource" ;
		String test = "/getResource*" ;

		RegularExpression re = new RegularExpression( test );
		assertTrue( re.matches( reference ) ) ;
	}
	
	@Test
	public void applyFilter_RegularExpression_2()
	{
		String reference = "/paulWeb/getResource/toto" ;
		String test = "/getResource*" ;

		RegularExpression re = new RegularExpression( test );
		assertTrue( re.matches( reference ) ) ;
	}
	
	@Test
	public void applyFilter_RegularExpression_3_1()
	{
		String reference = "/paulWeb/getResource/toto.bmp" ;
		String test = "/*\\.bmp" ;

		RegularExpression re = new RegularExpression( test );
		assertTrue( re.matches( reference ) ) ;
	}
	
	@Test
	public void applyFilter_RegularExpression_3_2()
	{
		String reference = "/paulWeb/getResource/toto.bmp" ;
		String test = "/*.bmp" ;

		RegularExpression re = new RegularExpression( test );
		assertFalse( re.matches( reference ) ) ;
	}
	
	// doit échouer
	@Test
	public void applyFilter_RegularExpression_4()
	{
		String reference = "/paulWeb/getmyresource/toto" ;
		String test = "/getResource*" ;

		RegularExpression re = new RegularExpression( test );
		assertFalse( re.matches( reference ) ) ;
	}
	
	@Test
	public void applyFilter_RegularExpression_5()
	{
		String reference = "/paulWeb/img/logo_toto.png" ;
		String test = "/img/*" ;

		RegularExpression re = new RegularExpression( test );
		assertTrue( re.matches( reference ) ) ;
	}
	
	@Test
	public void applyFilter_split()
	{
		String reference = "/paulWeb/getResource" ;
		String test = "/getResource*;/getToto" ;
		
		String[] testFiles = test.split(";") ;

		assertTrue( testFiles.length == 2 );
		
		RegularExpression reTrue = new RegularExpression( testFiles[0] );
		assertTrue( reTrue.matches( reference ) ) ;

		RegularExpression reFalse = new RegularExpression( testFiles[1] );
		assertFalse( reFalse.matches( reference ) ) ;
	}
	
	@Test 
	public void streamToString()
	{
		// 4) appeler la maquette html
	   String HtmlFilename = "/resource/html/main.html" ;
	   String myTplContent ;
	   InputStream in = getClass().getResourceAsStream( HtmlFilename ) ;
	   OutputStream out = new ByteArrayOutputStream();

        if( in==null )
    		fail("InputStream vide");
        else
        {
   		    int c;
            try {
				while ((c = in.read()) != -1) 
				{
				    out.write(c);
				}
				
				myTplContent = out.toString();
				logger.debug("Contenu de mon html : " + myTplContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
					try {
						if( in!=null )
							in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
            
   		    // 5) injecter les données en utilisant jericho

        }
		
	}
}
