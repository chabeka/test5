//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.02 at 05:11:16 PM CET 
//


package fr.urssaf.image.sae.services.capturemasse.modele.sommaire;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.urssaf.image.sae.services.capturemasse.modele.sommaire package. 
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
@SuppressWarnings("PMD")
public class ObjectFactory {

    private final static QName _Sommaire_QNAME = new QName("http://www.cirtil.fr/sae/sommaireXml", "sommaire");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.urssaf.image.sae.services.capturemasse.modele.sommaire
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SommaireType }
     * 
     */
    public SommaireType createSommaireType() {
        return new SommaireType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SommaireType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cirtil.fr/sae/sommaireXml", name = "sommaire")
    public JAXBElement<SommaireType> createSommaire(SommaireType value) {
        return new JAXBElement<SommaireType>(_Sommaire_QNAME, SommaireType.class, null, value);
    }

}