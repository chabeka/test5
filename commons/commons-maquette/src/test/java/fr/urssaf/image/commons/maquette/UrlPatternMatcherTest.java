package fr.urssaf.image.commons.maquette;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.commons.maquette.tool.UrlPatternMatcher;

public class UrlPatternMatcherTest {

	@Test
    public void Test()
    {
    	// Des tests issus de http://www2.roguewave.com/support/docs/leif/leif/html/bobcatug/7-3.html
		assertTrue(UrlPatternMatcher.match("/status/*","/status/synopsis"));
    	assertTrue(UrlPatternMatcher.match("/status/*","/status/complete?date=today"));
    	assertTrue(UrlPatternMatcher.match("/status/*","/status"));
    	assertFalse(UrlPatternMatcher.match("/status/*","/server/status"));
    	assertTrue(UrlPatternMatcher.match("*.map","/US/Oregon/Portland.map"));
    	assertTrue(UrlPatternMatcher.match("*.map","/US/Washington/Seattle.map"));
    	assertTrue(UrlPatternMatcher.match("*.map","/Paris.France.map"));
    	assertFalse(UrlPatternMatcher.match("*.map","/US/Oregon/Portland.MAP"));
    	assertFalse(UrlPatternMatcher.match("*.map","/interface/description/mail.mapi"));
    	
    	// Des tests inspirés de http://www.caucho.com/resin-3.0/servlet/servlet.xtp#url-pattern
    	assertTrue(UrlPatternMatcher.match("/foo/bar.html","/foo/bar.html"));
    	assertFalse(UrlPatternMatcher.match("/foo/bar.html","/application-name/foo/bar.html"));
    	assertTrue(UrlPatternMatcher.match("/foo/*","/foo"));
    	assertTrue(UrlPatternMatcher.match("/foo/*","/foo/"));
    	assertTrue(UrlPatternMatcher.match("/foo/*","/foo/bar.html"));
    	assertTrue(UrlPatternMatcher.match("*.foo","/application-name/toto.foo"));
    	assertFalse(UrlPatternMatcher.match("*.foo","/application-name/toto.FOO"));
    }
	
	@Ignore
	@Test
	public void maquette(){
    	// Test pour la maquette
    	assertTrue(UrlPatternMatcher.match("/*/getResourceImageMaquette","/TestMaquetteWeb/getResourceImageMaquette"));
	}
	
	@Ignore
	@Test
	public void maskFichier(){
    	// Test pour la maquette
    	assertTrue(UrlPatternMatcher.match("*/index.jsp","/TestMaquetteWeb/index.jsp"));
	}
}
