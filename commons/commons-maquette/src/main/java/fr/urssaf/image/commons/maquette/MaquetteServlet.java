package fr.urssaf.image.commons.maquette;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.maquette.template.MaquetteConfig;
import fr.urssaf.image.commons.maquette.template.config.exception.TemplateConfigNotInitialized;
import fr.urssaf.image.commons.maquette.tool.MaquetteConstant;
import fr.urssaf.image.commons.maquette.tool.MaquetteTools;


public class MaquetteServlet extends HttpServlet {
	
	public static final Logger logger = Logger.getLogger( MaquetteServlet.class.getName() );
	 
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
		String overloadColor = rq.getParameter("overload");
		String requestedFile = rq.getParameter("name");
		
		if( requestedFile == null )
			return ;
	
		ServletContext sc = getServletContext();
    	String filename = MaquetteTools.getResourcePath( requestedFile );
		InputStream in = getClass().getResourceAsStream( filename );
		
		// cas normal on ne fait que lire le fichier de resource et l'afficher
		if( overloadColor == null )
		{		
	    	MaquetteTools.printFileContentWithResponse( rs, sc, in, requestedFile ) ;
		}
		// cas surcharge des couleurs : on lit le fichier et on en modifie le contenu
		else
		{			
	    	try {
	    		MaquetteConfig maquetteCfg = (MaquetteConfig) rq.getSession().getAttribute( MaquetteConstant.SESSION_MAQUETTECONFIG ) ;
				MaquetteTools.printFileContentWithResponseAndModifyColor( rs, sc, in, requestedFile, maquetteCfg ) ;
			} catch (TemplateConfigNotInitialized e) {
logger.debug( "Exception lors de la génération dynamique des css, plus d'info dnas la stacktrace" ) ;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
