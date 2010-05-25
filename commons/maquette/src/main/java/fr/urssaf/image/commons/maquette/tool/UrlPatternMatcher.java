package fr.urssaf.image.commons.maquette.tool;

public class UrlPatternMatcher {

	// Pour récupérer l'URL de la request :
	// HttpServletRequest.getRequestURI()
	
	/**
     * Return <code>true</code> if the context-relative request path
     * matches the requirements of the specified filter mapping;
     * otherwise, return <code>false</code>.
     * 
     * Méthode extraite des sources de Apache Tomcat 6.0.26
     *
     * @param testPath URL mapping being checked
     * @param requestPath Context-relative request path of this request
     */
    private static boolean matchFiltersURL(String testPath, String requestPath) {
        
        if (testPath == null)
            return (false);

        // Case 1 - Exact Match
        if (testPath.equals(requestPath))
            return (true);

        // Case 2 - Path Match ("/.../*")
        if (testPath.equals("/*"))
            return (true);
        if (testPath.endsWith("/*")) {
            if (testPath.regionMatches(0, requestPath, 0, 
                                       testPath.length() - 2)) {
                if (requestPath.length() == (testPath.length() - 2)) {
                    return (true);
                } else if ('/' == requestPath.charAt(testPath.length() - 2)) {
                    return (true);
                }
            }
            return (false);
        }

        // Case 3 - Extension Match
        if (testPath.startsWith("*.")) {
            int slash = requestPath.lastIndexOf('/');
            int period = requestPath.lastIndexOf('.');
            if ((slash >= 0) && (period > slash) 
                && (period != requestPath.length() - 1)
                && ((requestPath.length() - period) 
                    == (testPath.length() - 1))) {
                return (testPath.regionMatches(2, requestPath, period + 1,
                                               testPath.length() - 2));
            }
        }

        // Case 4 - "Default" Match
        return (false); // NOTE - Not relevant for selecting filters

    }
    
    
    public static boolean match(String urlPattern, String requestPath)
    {
    	
    	// Vérifications extraites des sources de Apache Tomcat 6.0.26
    	
    	if (urlPattern=="*")
            return (true);
    	
    	if (requestPath == null)
            return (false);

    	// Fin de vérifications extraites des sources de Apache Tomcat 6.0.26
    	
    	// Appel de la sous-méthode
    	return matchFiltersURL(urlPattern, requestPath);
    	
    }
    
    
    public static boolean matchOne(String[] urlPatterns, String requestPath)
    {
    	if (urlPatterns==null)
    	{
    		return false;
    	}
    	for(String urlPattern:urlPatterns)
    	{
    		if (matchFiltersURL(urlPattern, requestPath))
    			return true;
    	}
    	return false;
    }
    
    
    public static boolean matchAll(String[] urlPatterns, String requestPath)
    {
    	if ((urlPatterns==null) || (urlPatterns.length==0))
    	{
    		return false;
    	}
    	for(String urlPattern:urlPatterns)
    	{
    		if (!matchFiltersURL(urlPattern, requestPath))
    			return false;
    	}
    	return true;
    }	
}
