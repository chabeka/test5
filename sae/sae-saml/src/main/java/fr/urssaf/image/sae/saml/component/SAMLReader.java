package fr.urssaf.image.sae.saml.component;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.xml.XMLObject;

/**
 * Classe de lecture des différentes balises d'un jeton SAML<br>
 * 
 * 
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class SAMLReader {

   private SAMLReader() {

   }

   /**
    * lecture de l'attribut 'IssueInstant' de la balise
    * <code>&lt;Assertion></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Instant de génération de l'assertion
    */
   public static DateTime loadIssueInstant(Assertion assertion) {

      return assertion.getIssueInstant();
   }

   /**
    * lecture de l'attribut 'ID' de la balise <code>&lt;Assertion></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Identifiant unique de l'assertion
    */
   public static String loadID(Assertion assertion) {

      return assertion.getID();
   }

   /**
    * lecture de la balise <code>&lt;Issuer></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Identifiant de l'émetteur de l'assertion
    */
   public static String loadIssuer(Assertion assertion) {

      return assertion.getIssuer() == null ? null : assertion.getIssuer()
            .getValue();
   }

   /**
    * lecture de la balise <code>&lt;Audience></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return URI décrivant le service visé
    */
   public static String loadAudience(Assertion assertion) {

      String audience = null;

      if (assertion.getConditions() != null
            && CollectionUtils.isNotEmpty(assertion.getConditions()
                  .getAudienceRestrictions())
            && CollectionUtils.isNotEmpty(assertion.getConditions()
                  .getAudienceRestrictions().get(0).getAudiences())) {

         audience = assertion.getConditions().getAudienceRestrictions().get(0)
               .getAudiences().get(0).getAudienceURI();

      }

      return audience;
   }

   /**
    * lecture de la l'attribut 'AuthnInstant' de la balise
    * <code>&lt;AuthnStatement></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Instant d'authentification de l'usager sur le SI
    */
   public static DateTime loadAuthInstant(Assertion assertion) {

      DateTime authInstant = null;

      if (CollectionUtils.isNotEmpty(assertion.getAuthnStatements())) {

         authInstant = assertion.getAuthnStatements().get(0).getAuthnInstant();
      }

      return authInstant;
   }

   /**
    * lecture de l'attribut 'NotBefore' de la balise <code>&lt;Conditions><code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Date de début de validité de l'assertion
    */
   public static DateTime loadNotBefore(Assertion assertion) {

      DateTime notBefore = null;

      if (assertion.getConditions() != null) {

         notBefore = assertion.getConditions().getNotBefore();
      }

      return notBefore;
   }

   /**
    * lecture de l'attribut 'NotOnOrAfter' de la balise
    * <code>&lt;Conditions><code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Date d'expiration de l'assertion
    */
   public static DateTime loadNotOnOrAfter(Assertion assertion) {

      DateTime notOnOrAfter = null;

      if (assertion.getConditions() != null) {

         notOnOrAfter = assertion.getConditions().getNotOnOrAfter();
      }

      return notOnOrAfter;
   }

   /**
    * lecture des balises PAGM dans <code>&lt;AttributeStatement><code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Liste des PAGM
    */
   @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
   public static List<String> loadPagm(Assertion assertion) {

      List<String> pagm = new ArrayList<String>();

      if (CollectionUtils.isNotEmpty(assertion.getAttributeStatements())) {

         for (AttributeStatement attributStatement : assertion
               .getAttributeStatements()) {

            if (CollectionUtils.isNotEmpty(attributStatement.getAttributes())) {

               for (Attribute attribute : attributStatement.getAttributes()) {

                  if ("PAGM".equals(attribute.getName())) {

                     for (XMLObject value : attribute.getAttributeValues()) {

                        pagm.add(value.getDOM().getTextContent());
                     }
                  }
               }

            }

         }
      }

      return pagm;
   }

   /**
    * lecture de la balise <code>&lt;AuthnContextClassRef></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Méthode d'authentification de l'utilisateur sur le SI de
    *         l'organisme client
    */
   public static String loadAuthnContextClassRef(Assertion assertion) {

      String classRef = null;

      if (CollectionUtils.isNotEmpty(assertion.getAuthnStatements())
            && assertion.getAuthnStatements().get(0).getAuthnContext() != null) {

         classRef = assertion.getAuthnStatements().get(0).getAuthnContext()
               .getAuthnContextClassRef().getAuthnContextClassRef();
      }

      return classRef;
   }

   /**
    * lecture de l'attribut 'Recipient' de la balise
    * <code>&lt;SubjectConfirmationData></code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Identifiant de l'organisme fournisseur
    */
   public static String loadRecipient(Assertion assertion) {

      String recipient = null;

      if (assertion.getSubject() != null
            && CollectionUtils.isNotEmpty(assertion.getSubject()
                  .getSubjectConfirmations())
            && assertion.getSubject().getSubjectConfirmations().get(0)
                  .getSubjectConfirmationData() != null) {

         recipient = assertion.getSubject().getSubjectConfirmations().get(0)
               .getSubjectConfirmationData().getRecipient();
      }

      return recipient;
   }

   /**
    * lecture de l'attribut 'Format' de la balise <code>&lt;Subject><code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Identifiant du format de l'identifiant du demandeur
    */
   public static String loadSubjectFormat(Assertion assertion) {

      String subjectFormat = null;

      if (assertion.getSubject() != null
            && assertion.getSubject().getNameID() != null) {

         subjectFormat = assertion.getSubject().getNameID().getFormat();
      }

      return subjectFormat;
   }

   /**
    * lecture de la balise <code>&lt;NameID><code>
    * 
    * @param assertion
    *           jeton SAML
    * @return Identifiant de l'utilisateur
    */
   public static String loadSubjectId(Assertion assertion) {

      String subjectId = null;

      if (assertion.getSubject() != null
            && assertion.getSubject().getNameID() != null) {

         subjectId = assertion.getSubject().getNameID().getValue();
      }

      return subjectId;
   }

   /**
    * 
    * @param assertion
    *           jeton SAML
    * @return La clé publique qui a signé l'assertion
    */
   public static X509Certificate loadPublicKey(Assertion assertion) {

      // TODO Auto-generated method stub

      return null;

   }
}
