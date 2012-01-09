package fr.urssaf.image.sae.integration.bouchon.security;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.log4j.Logger;
import org.apache.rampart.util.Axis2Util;
import org.w3c.dom.Document;

import fr.urssaf.image.sae.integration.bouchon.utils.RessourcesUtils;


/**
 * Handler pour ajouter le jeton SAML 2.0 dans la la balise WS-security du web
 * service<br>
 * Le handler est configuré dans le fichier <code>META-INF/axis2.xml</code>
 * 
 */
public class VIHandler extends AbstractHandler {

   
   private static final Logger LOG = Logger.getLogger(VIHandler.class);
   
   
   /**
    * Le nom de la propriété du MessageContext dans laquelle il faut
    * renseigner le chemin du fichier de ressource XML contenant le
    * vecteur d'identifiant à utiliser
    */
   public static final String PROP_FICHIER_VI = "ficRessourceVi" ;
   
   
   /**
    * Constructeur 
    */
   public VIHandler() {
      
   }
   

   /**
    * Création d'une balise WS-Security dans le header du SOAP<br>
    * <br>
    * Insertion du VI dans cet balise WS-Security
    *  
    * {@inheritDoc}
    * 
    */
   @Override
   public final InvocationResponse invoke(MessageContext msgCtx)
         throws AxisFault {

      LOG.debug("Début de l'interception du message SOAP avant envoi");
      
      /* 
      // Création de l'en-tête WS-Security
      LOG.debug("Création de l'en-tête WS-Security");
      try {
         
         Document doc = Axis2Util.getDocumentFromSOAPEnvelope(
               msgCtx.getEnvelope(), 
               true);

         WSSecHeader secHeader = new WSSecHeader(null, false);
         secHeader.insertSecurityHeader(doc);
         
         msgCtx.setEnvelope((SOAPEnvelope) doc.getDocumentElement());

      } catch (WSSecurityException e) {
         throw new IllegalStateException(e);
      }
      
      // Ajout du VI dans l'en-tête WS-Security
      String ficRessourceVi = (String)msgCtx.getProperty(PROP_FICHIER_VI);
      if ((ficRessourceVi!=null) && (ficRessourceVi.length()>0)) {
         LOG.debug("Ajout du VI dans l'en-tête WS-Security");
         SOAPHeader header = msgCtx.getEnvelope().getHeader();
         OMElement security = header.getFirstChildWithName(
               new QName(WSConstants.WSSE_NS, "Security"));
         try {
            security.addChild(
                  org.apache.axis2.util.XMLUtils.toOM(
                        RessourcesUtils.loadResource(this,ficRessourceVi)));
         } catch (Exception e) {
            throw new IllegalStateException(e);
         }
      } else { 
         LOG.debug("Aucun VI à ajouter dans l'en-tête WS-Security");
      }
      
      /* */
      
      
      /* */

      // Ajout de l'en-tête WS-Security chargé depuis un fichier de ressource XML
      LOG.debug("Insertion de l'en-tête WS-Security");
      try {
         
         String ficRessourceVi = (String)msgCtx.getProperty(PROP_FICHIER_VI);
         LOG.debug("Insertion du VI du fichier " + ficRessourceVi);
         
         Document doc = Axis2Util.getDocumentFromSOAPEnvelope(
               msgCtx.getEnvelope(), 
               true);
         
         msgCtx.setEnvelope((SOAPEnvelope) doc.getDocumentElement());
         
         SOAPHeader soapHeader = msgCtx.getEnvelope().getHeader();
         
         soapHeader.addChild(
               org.apache.axis2.util.XMLUtils.toOM(
                     RessourcesUtils.loadResource(this,ficRessourceVi)));
         
      } catch (Exception e) {
         throw new IllegalStateException(e);
      }
      
      /* */
      
      // fin
      LOG.debug("Fin de l'interception du message SOAP avant envoi");
      return InvocationResponse.CONTINUE;
      
   }
}
