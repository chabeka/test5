package fr.urssaf.image.sae.saml.component;

import java.util.List;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.schema.XSAny;

import fr.urssaf.image.sae.saml.util.ListUtils;

/**
 * Fabrique des différentes balises du jeton SAML<br>
 * Le recours à cette classe nécessite une initialisation de la librairie
 * OpenSAML : {@link SAMLConfiguration#init}<br>
 * 
 * 
 */
public final class SAMLFactory {

   private SAMLFactory() {

   }

   /**
    * Création de la balise <code>&lt;AttributeStatement></code>
    * 
    * <pre>
    *  &lt;saml:AttributeStatement>
    *         &lt;saml:Attribute Name="PAGM">
    *             &lt;saml:AttributeValue>PAGM_1&lt;/saml:AttributeValue>
    *             &lt;saml:AttributeValue>PAGM_2&lt;/saml:AttributeValue>
    *         &lt;/saml:Attribute>
    * &lt;/saml:AttributeStatement>
    * </pre>
    * 
    * Les pagms non renseignés sont filtrés tels que null, "" et "  "
    * 
    * @param pagms
    *           Liste des PAGM
    * @return balise XML
    */
   @SuppressWarnings("unchecked")
   public static AttributeStatement createAttributeStatementPAGM(
         List<String> pagms) {

      AttributeStatement attrStatement = create(AttributeStatement.DEFAULT_ELEMENT_NAME);

      Attribute attribute = create(Attribute.DEFAULT_ELEMENT_NAME);
      attribute.setName("PAGM");

      for (String pagm : ListUtils.filter(pagms)) {

         XMLObjectBuilder<XSAny> builder = Configuration.getBuilderFactory()
               .getBuilder(XSAny.TYPE_NAME);

         XSAny value = builder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
         value.setTextContent(pagm);
         attribute.getAttributeValues().add(value);

      }

      attrStatement.getAttributes().add(attribute);

      return attrStatement;
   }

   /**
    * Création de la balise <code>&lt;AuthnStatement></code>
    * 
    * <pre>
    * 
    * &lt;saml:AuthnStatement AuthnInstant="<i><b>[authnInstant]</b></i>" SessionIndex="<i><b>[sessionIndex]</b></i>">
    *         &lt;saml:AuthnContext>
    *             &lt;saml:AuthnContextClassRef><i><b>[classrefValue]</b></i>&lt;/saml:AuthnContextClassRef>
    *         &lt;/saml:AuthnContext>
    * &lt;/saml:AuthnStatement>
    * 
    * </pre>
    * 
    * @param authnInstant
    *           Instant d'authentification de l'usager sur le SI
    * @param sessionIndex
    *           Identifiant unique de l'assertion
    * @param classrefValue
    *           Méthode d'authentification de l'utilisateur sur le SI de
    *           l'organisme client
    * @return balise XML
    */
   public static AuthnStatement createAuthnStatement(DateTime authnInstant,
         String sessionIndex, String classrefValue) {

      AuthnStatement authnStatement = create(AuthnStatement.DEFAULT_ELEMENT_NAME);
      authnStatement.setAuthnInstant(authnInstant);
      authnStatement.setSessionIndex(sessionIndex);

      AuthnContext authncontext = create(AuthnContext.DEFAULT_ELEMENT_NAME);
      AuthnContextClassRef classref = create(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
      classref.setAuthnContextClassRef(classrefValue);
      authncontext.setAuthnContextClassRef(classref);
      authnStatement.setAuthnContext(authncontext);

      return authnStatement;
   }

   /**
    * Création de la balise <code>&lt;Conditions></code>
    * 
    * <pre>
    * 
    * &lt;saml:Conditions NotBefore="<i><b>[notBefore]</b></i>" NotOnOrAfter="<i><b>[notOnOrAfter]</b></i>">
    *         &lt;saml:AudienceRestriction>
    *             &lt;saml:Audience><i><b>[audienceURI]</b></i>&lt;/saml:Audience>
    *         &lt;/saml:AudienceRestriction>
    * &lt;/saml:Conditions>
    * 
    * </pre>
    * 
    * @param notBefore
    *           Date de début de validité de l'assertion
    * @param notOnOrAfter
    *           Date d'expiration de l'assertion
    * @param audienceURI
    *           URI décrivant le service visé
    * @return balise XML
    */
   public static Conditions createConditions(DateTime notBefore,
         DateTime notOnOrAfter, String audienceURI) {

      Conditions conditions = create(Conditions.DEFAULT_ELEMENT_NAME);
      conditions.setNotBefore(notBefore);
      conditions.setNotOnOrAfter(notOnOrAfter);

      AudienceRestriction restriction = create(AudienceRestriction.DEFAULT_ELEMENT_NAME);
      Audience audience = create(Audience.DEFAULT_ELEMENT_NAME);
      audience.setAudienceURI(audienceURI);
      restriction.getAudiences().add(audience);
      conditions.getAudienceRestrictions().add(restriction);

      return conditions;
   }

   /**
    * Création de la balise <code>&lt;Assertion></code>
    * 
    * <pre>
    * 
    * &lt;saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" 
    *                    ID="<i><b>[i]</b></i>" 
    *                    IssueInstant="<i><b>[issueInstant]</b></i>" 
    *                    Version="2.0">
    * 
    * &lt;/saml:Assertion>
    * 
    * </pre>
    * 
    * @param id
    *           Identifiant unique de l'assertion
    * @param issueInstant
    *           Instant de génération de l’assertion
    * @return balise XML
    */
   @SuppressWarnings("PMD.ShortVariable")
   public static Assertion createAssertion(String id, DateTime issueInstant) {

      Assertion assertion = create(Assertion.DEFAULT_ELEMENT_NAME);
      assertion.setIssueInstant(issueInstant);
      assertion.setID(id);

      return assertion;

   }

   /**
    * Création de la balise <code>&lt;Issuer></code>
    * 
    * <pre>
    * 
    * &lt;saml:Issuer><i><b>[value]</b></i>&lt;/saml:Issuer>
    * 
    * </pre>
    * 
    * @param value
    *           Identifiant de l'émetteur de l'assertion
    * @return balise XML
    */
   public static Issuer createIssuer(String value) {

      Issuer issuer = create(Issuer.DEFAULT_ELEMENT_NAME);
      issuer.setValue(value);

      return issuer;
   }

   /**
    * Création de la balise <code>&lt;Subject></code>
    * 
    * <pre>
    * 
    * &lt;saml:Subject>
    *         &lt;saml:NameID Format="<i><b>[subjectFormat2]</b></i>"><i><b>[subjectId2]</b></i>&lt;/saml:NameID>
    *         &lt;saml:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
    *             &lt;saml:SubjectConfirmationData NotOnOrAfter="<i><b>[notOnOrAfter]</b></i>" Recipient="<i><b>[recipient]</b></i>"/>
    *         &lt;/saml:SubjectConfirmation>
    * &lt;/saml:Subject>
    * 
    * </pre>
    * 
    * @param subjectId2
    *           Identifiant de l’utilisateur
    * @param subjectFormat2
    *           Identifiant du format de l’identifiant du demandeur
    * @param notOnOrAfter
    *           Date d'expiration de l'assertion
    * @param recipient
    *           Identifiant de l'organisme fournisseur
    * @return balsie XML
    */
   public static Subject createSubject(String subjectId2,
         String subjectFormat2, DateTime notOnOrAfter, String recipient) {
      NameID nameID = create(NameID.DEFAULT_ELEMENT_NAME);
      nameID.setValue(subjectId2);
      nameID.setFormat(subjectFormat2);

      Subject subject = create(Subject.DEFAULT_ELEMENT_NAME);
      subject.setNameID(nameID);

      SubjectConfirmation confirmation = create(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
      confirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");

      SubjectConfirmationData data = create(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
      data.setNotOnOrAfter(notOnOrAfter);
      data.setRecipient(recipient);
      confirmation.setSubjectConfirmationData(data);

      subject.getSubjectConfirmations().add(confirmation);

      return subject;
   }

   @SuppressWarnings("unchecked")
   private static <T> T create(QName qname) {
      return (T) ((XMLObjectBuilder) Configuration.getBuilderFactory()
            .getBuilder(qname)).buildObject(qname);
   }
}
