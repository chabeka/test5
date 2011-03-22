package fr.urssaf.image.sae.saml.service;

import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;
import org.w3c.dom.Element;

/**
 * Classe de service pour les jetons SAML<br>
 * Le recours à cette classe nécessite une initialisation de la librairie
 * OpenSAML : {@link fr.urssaf.image.sae.saml.component.SAMLConfiguration#init}<br>
 * 
 */
public class SamlAssertionService {

   private final MarshallerFactory marshallerFactory;

   private final UnmarshallerFactory unmarshallFactory;

   /**
    * initialisation de {@link MarshallerFactory} pour la transformation du
    * jeton SAML au format XML<br>
    */
   public SamlAssertionService() {

      marshallerFactory = Configuration.getMarshallerFactory();
      unmarshallFactory = Configuration.getUnmarshallerFactory();
   }

   /**
    * Méthode de validation d'un jeton SAML<br>
    * <br>
    * La validation appelle la méthode {@link Configuration#getValidatorSuite}<br>
    * <br>
    * La méthode utilise les suites de validations spécifiés dans le schéma '
    * <code>saml2-core-validation-config-xml</code>' présent dans la librairie
    * OpenSAML
    * <ul>
    * <li><code>saml2-core-schema-validator</code></li>
    * <li><code>saml2-core-spec-validator</code></li>
    * </ul>
    * 
    * @param samlObject
    *           jeton SAML
    * @throws ValidationException
    *            le jeton n'est pas valide
    */
   public final void validate(SAMLObject samlObject) throws ValidationException {

      validate(samlObject, "saml2-core-schema-validator");
      validate(samlObject, "saml2-core-spec-validator");

   }

   private void validate(SAMLObject samlObject, String suiteId)
         throws ValidationException {

      ValidatorSuite validatorSuite = Configuration.getValidatorSuite(suiteId);

      validatorSuite.validate(samlObject);
   }

   /**
    * Méthode de pour transformer un objet de type {@link SAMLObject} en en un
    * objet de type {@link Element}<br>
    * La méthode appelle {@link Marshaller#marshall(org.opensaml.xml.XMLObject)}
    * 
    * @param samlObject
    *           jeton SAML
    * @return jeton SAML
    */
   public final Element marshaller(SAMLObject samlObject) {

      Marshaller marshaller = marshallerFactory.getMarshaller(samlObject);
      try {

         return marshaller.marshall(samlObject);

      } catch (MarshallingException e) {
         throw new IllegalStateException(e);
      }
   }

   /**
    * méthode de transformation un objet de type {@link Element} en un objet de
    * type {@link XMLObject}
    * 
    * @param element
    *           jeton SAML
    * @return jeton SAML
    */
   public final XMLObject unmarshaller(Element element) {

      Unmarshaller unmarshaller = unmarshallFactory.getUnmarshaller(element);

      try {
         return unmarshaller.unmarshall(element);
      } catch (UnmarshallingException e) {
         throw new IllegalStateException(e);
      }

   }
}
