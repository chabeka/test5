package fr.urssaf.image.commons.maquette.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.maquette.template.MaquetteConfig;
import fr.urssaf.image.commons.maquette.template.config.exception.TemplateConfigNotInitialized;

public class MaquetteTools {

	public static final Logger logger = Logger.getLogger( MaquetteTools.class.getName() );
	
	/**
	 * @param filename
	 * @return chemin complet vers le fichier
	 */
	public static String getResourcePath( String filename )
	{
		return MaquetteConstant.RESOURCEFOLDER + filename ;
	}
	
	/**
	 * @param rs
	 * @param sc
	 * @param in
	 * @param requestedFile
	 * @throws IOException
	 */
	public static void printFileContentWithResponse( HttpServletResponse rs, ServletContext sc, InputStream in, String requestedFile ) throws IOException
	{
	    if( in != null )
	    {
	    	// Get the MIME type of the requested file
		    String mimeType = sc.getMimeType(requestedFile);
		    if (mimeType == null) {
		        sc.log("Could not get MIME type of " + requestedFile);
		        rs.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        return;
		    }
		    		    
		    // Copy the contents of the file to the output stream
		    OutputStream out = rs.getOutputStream();
		    byte[] buf = new byte[1024];
		    int count = 0;
		    int length = 0 ;
		    while ((count = in.read(buf)) >= 0) {
		    	length += count ;	
		        out.write(buf, 0, count);
		    }
		    in.close();
		    out.close();
		    
		    // Set content type
		    rs.setContentType(mimeType);
		    
		 	// Set content size
		    rs.setContentLength(length);
	    }
	}
	
	/**
	 * @param rs
	 * @param sc
	 * @param in
	 * @param requestedFile
	 * @throws IOException
	 * @throws TemplateConfigNotInitialized 
	 */
	public static void printFileContentWithResponseAndModifyColor( HttpServletResponse rs, ServletContext sc, InputStream in, String requestedFile, MaquetteConfig maquetteCfg ) throws IOException, TemplateConfigNotInitialized
	{
	    if( in != null )
	    {    	
	    	InputStreamReader isr;
			try {
				String cssContent = "" ;
				StringBuffer buffer = new StringBuffer();
				// isr = new InputStreamReader(in, "ISO-8859-15");
				isr = new InputStreamReader(in, "UTF-8");
				Reader r = new BufferedReader(isr);
				int charPos;
				try {
					while ((charPos = r.read()) > -1) {
						buffer.append((char)charPos);
					}
					
					// Si hpc n'a jamais été instancié avec FilterConfig on va avoir des soucis car pas de cssListParam
					// TODO améliorer cette implémentation... elle est bancale
					if( maquetteCfg.getCssListParam().size() > 0 )
					{
						// faire les modifications sur le contenu de la CSS
						cssContent = buffer.toString();
// TODO ne devrais je pas remonter le else directement dans le MaquetteConfig : après le test sur theme ou non, je check la taille de la liste et je colle les valeurs par défauts si besoin						
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMAINBACKGROUNDCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMAINBACKGROUNDCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMAINBACKGROUNDCOLOR, MaquetteConstant.CSSMAINBACKGROUNDCOLOR_DEFAULT ) ;
*/						
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSCONTENTBACKGROUNDCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSCONTENTBACKGROUNDCOLOR, MaquetteConstant.CSSCONTENTBACKGROUNDCOLOR_DEFAULT ) ;
*/						
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSLEFTCOLORBACKGROUNDIMG, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSLEFTCOLORBACKGROUNDIMG, MaquetteConstant.CSSLEFTCOLORBACKGROUNDIMG_DEFAULT ) ;
*/
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSINFOBOXBACKGROUNDCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSINFOBOXBACKGROUNDCOLOR, MaquetteConstant.CSSINFOBOXBACKGROUNDCOLOR_DEFAULT ) ;
*/	
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSHEADERBACKGROUNDCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSHEADERBACKGROUNDCOLOR, MaquetteConstant.CSSHEADERBACKGROUNDCOLOR_DEFAULT ) ;
*/	
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSHEADERBACKGROUNDIMG, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSHEADERBACKGROUNDIMG_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSHEADERBACKGROUNDIMG, MaquetteConstant.CSSHEADERBACKGROUNDIMG_DEFAULT ) ;
*/	
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSSELECTEDMENUBACKGROUNDCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSSELECTEDMENUBACKGROUNDCOLOR, MaquetteConstant.CSSSELECTEDMENUBACKGROUNDCOLOR_DEFAULT ) ;
*/
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMAINFONTCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMAINFONTCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMAINFONTCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMAINFONTCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMAINFONTCOLOR, MaquetteConstant.CSSMAINFONTCOLOR_DEFAULT ) ;
*/
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSCONTENTFONTCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSCONTENTFONTCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSCONTENTFONTCOLOR, MaquetteConstant.CSSCONTENTFONTCOLOR_DEFAULT ) ;				
*/
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMENUFIRSTROWFONTCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMENUFIRSTROWFONTCOLOR, MaquetteConstant.CSSMENUFIRSTROWFONTCOLOR_DEFAULT ) ;				
*/
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMENULINKFONTCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENULINKFONTCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMENULINKFONTCOLOR, MaquetteConstant.CSSMENULINKFONTCOLOR_DEFAULT ) ;				
*/
						if( maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX ) != null
							&& maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX ).length() > 0 )
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMENULINKHOVERFONTCOLOR, maquetteCfg.getCssListParam().get( MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_INDEX )) ;
/*						else
							cssContent = cssContent.replace(MaquetteConstant.P_CSSMENULINKHOVERFONTCOLOR, MaquetteConstant.CSSMENULINKHOVERFONTCOLOR_DEFAULT ) ;				
*/
					}
					else
						cssContent = "" ;
					
					// TODO se baser sur l'extension, pour l'instant je force à CSS
					rs.setContentType("text/css"); 
					
					rs.setContentLength( cssContent.length() );
					
					// Affichage
					ServletOutputStream out = rs.getOutputStream();
					out.print( cssContent );
					
//logger.debug( cssContent );

				} catch (IOException e2) {
logger.warn( e2.getMessage() ) ;
					e2.printStackTrace();
				} finally{
					try {
						r.close();
					} catch (IOException e3) {
logger.warn( e3.getMessage() );
						e3.printStackTrace();
					}
				}
								
			} catch (UnsupportedEncodingException e1) {
logger.warn( e1.getMessage() );
				e1.printStackTrace();
			}
	    }
	}
}
