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
// System.out.println( "contenu : " + output.toString() + "<br />" );
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	public PrintWriter getWriter() {
// System.out.println( "getWriter appelé : ----------" + output.toString() + "----------<br />" );
		return new PrintWriter(output);
	}

}
