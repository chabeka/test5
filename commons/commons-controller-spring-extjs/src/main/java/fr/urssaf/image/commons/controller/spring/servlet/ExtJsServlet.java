package fr.urssaf.image.commons.controller.spring.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet permettant le téléchargement de la ressource Javascript fr.urssaf.image.commons.js.extjs.js
 * aux applications clientes
 * 
 * Voici le lien à inclure dans la vue de l'application cliente : 
 * <script type="text/javascript" src="getResourceExtJs.do?name=/js/fr.urssaf.image.commons.js.extjs.js">image extjs</script>
 *
 * Et voici ce qu'il faut ajouter au web.xml :
 *  
   <!-- Servlet pour extjs -->
   <servlet>
      <description></description>
      <display-name>ExtJsServlet</display-name>
      <servlet-name>ExtJsServlet</servlet-name>
      <servlet-class>fr.urssaf.image.commons.controller.spring.servlet.ExtJsServlet</servlet-class>
   </servlet>
   <servlet-mapping>
      <servlet-name>ExtJsServlet</servlet-name>
      <url-pattern>/getResourceExtJs.do</url-pattern>
   </servlet-mapping>
 
 */
@SuppressWarnings("serial")
public class ExtJsServlet extends HttpServlet {
	
	
   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
		
		String requestedFile = rq.getParameter("name");
		
		if( requestedFile == null )
			return ;
	
		ServletContext sc = getServletContext();
    	String filename = requestedFile;
		InputStream in = getClass().getResourceAsStream( filename );
		
		printFileContentWithResponse( rs, sc, in, requestedFile ) ;
		
	}
	
	private static void printFileContentWithResponse(
	      HttpServletResponse rs,
	      ServletContext sc,
	      InputStream in,
	      String requestedFile ) throws IOException
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

}
