//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.22 at 10:23:26 AM CET 
//


package fr.gouv.ae.archive.draft.standard_echange_v0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import fr.gouv.ae.archive.draft.standard_echange_v0_2.qualifieddatatype._1.ArchivesCodeType;
import fr.gouv.ae.archive.draft.standard_echange_v0_2.qualifieddatatype._1.ArchivesIDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._6.TextType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;OCAAES000004&lt;/ccts:UniqueID&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Acronym xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;ABIE&lt;/ccts:Acronym&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Communication&lt;/ccts:DictionaryEntryName&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Version xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;1.0&lt;/ccts:Version&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Moyens de communication des personnes et/ou organisations (téléphone, fax, e-mail, etc.) sauf courrier postal.&lt;/ccts:Definition&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:ObjectClassTerm xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Communication&lt;/ccts:ObjectClassTerm&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:QualifierTerm xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Communication&lt;/ccts:QualifierTerm&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for CommunicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommunicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Channel" type="{fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1}ArchivesCodeType" minOccurs="0"/>
 *         &lt;element name="CompleteNumber" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6}TextType" minOccurs="0"/>
 *         &lt;element name="URI" type="{fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1}ArchivesIDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationType", propOrder = {
    "channel",
    "completeNumber",
    "uri"
})
public class CommunicationType {

    @XmlElement(name = "Channel")
    protected ArchivesCodeType channel;
    @XmlElement(name = "CompleteNumber")
    protected TextType completeNumber;
    @XmlElement(name = "URI")
    protected ArchivesIDType uri;

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link ArchivesCodeType }
     *     
     */
    public ArchivesCodeType getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchivesCodeType }
     *     
     */
    public void setChannel(ArchivesCodeType value) {
        this.channel = value;
    }

    /**
     * Gets the value of the completeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getCompleteNumber() {
        return completeNumber;
    }

    /**
     * Sets the value of the completeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setCompleteNumber(TextType value) {
        this.completeNumber = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link ArchivesIDType }
     *     
     */
    public ArchivesIDType getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchivesIDType }
     *     
     */
    public void setURI(ArchivesIDType value) {
        this.uri = value;
    }

}