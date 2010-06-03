
/*
 * 
 */

package fr.urssaf.image.commons.webservice.exemple.document.modele;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.6
 * Thu Jun 03 13:46:49 CEST 2010
 * Generated source version: 2.2.6
 * 
 */


@WebServiceClient(name = "DocServiceDocument", 
                  wsdlLocation = "http://localhost:8080/commons-webservice-spring-exemple/service/DocServiceDocument?wsdl",
                  targetNamespace = "http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/") 
public class DocServiceDocument extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/", "DocServiceDocument");
    public final static QName DocumentServicePort = new QName("http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/", "DocumentServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/commons-webservice-spring-exemple/service/DocServiceDocument?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8080/commons-webservice-spring-exemple/service/DocServiceDocument?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public DocServiceDocument(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DocServiceDocument(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DocServiceDocument() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns DocService
     */
    @WebEndpoint(name = "DocumentServicePort")
    public DocService getDocumentServicePort() {
        return super.getPort(DocumentServicePort, DocService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DocService
     */
    @WebEndpoint(name = "DocumentServicePort")
    public DocService getDocumentServicePort(WebServiceFeature... features) {
        return super.getPort(DocumentServicePort, DocService.class, features);
    }

}
