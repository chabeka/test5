package fr.urssaf.image.commons.maquette.tool;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * @author CER6990172
 * @desc classe récupérée sur http://java.sun.com/products/servlet/Filters.html
 *       permet de stocker les output et d'y accéder alors que c'est impossible
 *       depuis la class mère HttpServletResponseWrapper
 */
public class CharResponseWrapper extends HttpServletResponseWrapper 
{
	
	// private Logger logger = Logger.getLogger(CharResponseWrapper.class) ;
	
	private CharArrayWriter output;

	public String toString() {
		return output.toString();
	}

	/**
	 * @param response
	 */
	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new CharArrayWriter();
//		logger.debug("contenu : " + output.toString() + "<br />");
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	public PrintWriter getWriter() {
//		logger.debug("getWriter appelé : ----------" + output.toString() + "----------<br />");
		return new PrintWriter(output);
	}

}
