//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.16 at 01:52:56 PM CET 
//


package fr.urssaf.image.commons.xml.jaxb.exemple.sandre.intref;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import fr.urssaf.image.commons.xml.jaxb.exemple.sandre.composantstypes.TextType;


/**
 * Nom de la personne
 *             agissant en tant que contact pour un intervenant et pour une
 *             fonction donn�e.
 * 
 *             Le nom du contact rel�ve de la responsabilit� de l'organisme ayant
 *             saisi les informations, gestionnaire de la liste des
 *             contacts.
 * 
 * <p>Java class for NomContact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NomContact">
 *   &lt;simpleContent>
 *     &lt;restriction base="&lt;http://xml.sandre.eaufrance.fr/Composants/1>TextType">
 *     &lt;/restriction>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NomContact")
public class NomContact
    extends TextType
{


}
