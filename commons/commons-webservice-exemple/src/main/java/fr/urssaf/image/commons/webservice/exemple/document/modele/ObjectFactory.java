
package fr.urssaf.image.commons.webservice.exemple.document.modele;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.urssaf.image.commons.webservice.exemple.document.modele package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AllDocumentsResponse_QNAME = new QName("http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/", "allDocumentsResponse");
    private final static QName _AllDocuments_QNAME = new QName("http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/", "allDocuments");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.urssaf.image.commons.webservice.exemple.document.modele
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link AllDocumentsResponse }
     * 
     */
    public AllDocumentsResponse createAllDocumentsResponse() {
        return new AllDocumentsResponse();
    }

    /**
     * Create an instance of {@link AllDocuments }
     * 
     */
    public AllDocuments createAllDocuments() {
        return new AllDocuments();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllDocumentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/", name = "allDocumentsResponse")
    public JAXBElement<AllDocumentsResponse> createAllDocumentsResponse(AllDocumentsResponse value) {
        return new JAXBElement<AllDocumentsResponse>(_AllDocumentsResponse_QNAME, AllDocumentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllDocuments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://document.service.exemple.spring.webservice.commons.image.urssaf.fr/", name = "allDocuments")
    public JAXBElement<AllDocuments> createAllDocuments(AllDocuments value) {
        return new JAXBElement<AllDocuments>(_AllDocuments_QNAME, AllDocuments.class, null, value);
    }

}
