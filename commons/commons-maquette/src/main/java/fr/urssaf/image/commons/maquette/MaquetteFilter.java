package fr.urssaf.image.commons.maquette;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import fr.urssaf.image.commons.maquette.template.MaquetteConfig;
import fr.urssaf.image.commons.maquette.template.MaquetteParser;
import fr.urssaf.image.commons.maquette.tool.CharResponseWrapper;
import fr.urssaf.image.commons.maquette.tool.MaquetteConstant;
import fr.urssaf.image.commons.maquette.tool.MaquetteTools;
import fr.urssaf.image.commons.maquette.tool.UrlPatternMatcher;

public class MaquetteFilter implements Filter {
	
	public static final Logger logger = Logger.getLogger( MaquetteFilter.class.getName() );
	
	private FilterConfig filterConfig;

	@Override
	public void destroy() {
		filterConfig = null;
	}
	
	@Override
	public void doFilter(ServletRequest rq, ServletResponse rs,
			FilterChain chain) throws IOException, ServletException {
logger.debug( "---------------------------------------" );
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rs;
		
		// Gestion des paramètres statiques (via le web.xml)
		try {
			MaquetteConfig maquetteCfg = new MaquetteConfig( getFilterConfig(), request ) ;

logger.debug( "Request URL : " + request.getRequestURL() + "?" + request.getQueryString() );

			// 0) test d'application ou non du filtre
			if (!applyFilter(request)) {
logger.debug( "Arrêt du filtre") ;
				chain.doFilter(request, response) ;
				return;
			}
	
			// 1) récupérer le flux : créer le writer et appeler le doChain pour intercepter le html et le stocker 
			// 		dans une variable accessible gràce au CharResponseWrapper
			CharResponseWrapper wrapper = new CharResponseWrapper(
					(HttpServletResponse) response);
			
			// force l'encodage en UTF-8 : sinon le contentType était toujours à ISO-xxx
			// toutefois cela pourrait avoir des conséquences si on ne passe pas par la maquette
			// on stocke donc l'ancien encodage pour le restorer ensuite
			String originalCharSet = wrapper.getCharacterEncoding();
			wrapper.setCharacterEncoding("UTF-8");
			
			// permet de remplir le wrapper(=response) qui est vide actuellement : on est le premier filtre, la 
			// servlet sera ensuite interprétée et on récupère la main
			PrintWriter pw = response.getWriter();
			chain.doFilter(request, wrapper);
logger.debug( "wrapper content type : " + wrapper.getContentType() ) ;
			// Cas 1 : on a du text/html ou du text/plain ou on demande à forcer le passage
			if (wrapper.getContentType() != null
					&& ( wrapper.getContentType().contains("text/html") 
						|| wrapper.getContentType().contains("text/plain") )) {
				
				
				// forcer le type mime (surtout pour le text/plain en entrée)
				wrapper.setContentType("text/html");
logger.debug( "Encodage des caractères : " + wrapper.getCharacterEncoding() ) ;
				
				// Création du parser avec la chaîne à décorer et le tremplate décorateur
				MaquetteParser mp = new MaquetteParser( wrapper.toString(), MaquetteTools.getResourcePath("/html/main.html"), request, maquetteCfg ) ;
				try {
					mp.build();
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				String outputDocument = mp.getOutputDocument().toString() ;
				pw.println( outputDocument );
			} 
			// Cas 2 : ce n'est pas une resource à décorer => on l'affiche juste
			else if(wrapper.getContentType() != null )
			{
				// replace l'ancien charset modifié après la création du wrapper
				if( originalCharSet != null )
					wrapper.setCharacterEncoding( originalCharSet );
				
				String outputDocument = wrapper.toString() ; 
				pw.println( outputDocument );
			}
			// Cas 3 : la resource demandée n'est pas trouvée => 404 not found
			else
				response.setStatus(404);
			
		} catch (Exception e) {
logger.fatal( "Problème avec MaquetteConfig, regardez la stackTrace" );
			e.printStackTrace();
			throw new ServletException( "La servlet a rencontré une erreur : (" + e.getClass() + ") " + e.getMessage() ,
					e.getCause() );
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	

	/**
	 * @desc retourne false si on a trouvé un paramètre excludeFile ET que le
	 * contenu du paramètre correspond à l'URI demandé SINON retourne true, et
	 * le filtre pourra être appliqué
	 * @return 
	 */
	public boolean applyFilter(HttpServletRequest rq) {
		// Exclusion par défaut lié à la MaquetteServlet
		if( checkGetResource(rq) )
			return false ;
		
		// Exclusion explicite
		return !checkFilesParameters( rq, "excludeFiles" ) ;
	}
	
	/**
	 * @desc vérifie si l'URI de la requete correspond au pattern de notre Servlet getResource.
	 * @param rq
	 * @return
	 * @todo Ne pas utiliser une expression régulière mais une URI et son pattern RFC 2396
	 */
	private boolean checkGetResource( HttpServletRequest rq )
	{			
		Boolean match ;
		String URI = rq.getRequestURI();
		RegularExpression re = new RegularExpression( MaquetteConstant.GETRESOURCEURI );
		match = re.matches(URI) ;
logger.debug( "checkGetResource : " + match.toString() );
		return match ;
	}
	
	/**
	 * @desc lit le paramètre de configuration includeFiles/excludeFiles et retourne true si la requête correspond à un des pattern
	 * 		 inscrit dans la configuration du filtre
	 * @param rq
	 * @return
	 */
	private boolean checkFilesParameters( HttpServletRequest rq, String paramName )
	{
		Boolean match = false ;
		String patternList = getFilterConfig().getInitParameter(
				paramName );
logger.debug( "Pattern d'exclusion : " + patternList );
		if (patternList != null) {
			String[] filesToTest = patternList.split(";");
			String URI = rq.getRequestURI();
logger.debug( "URI pour test d'exclusion : " + URI );
			match = UrlPatternMatcher.matchOne( filesToTest, URI ) ;
		}

		return match;
	}

}
