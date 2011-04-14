package fr.urssaf.image.sae.webservices.security.ws;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.util.XMLUtils;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSConstants;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.vi.exception.VIVerificationException;
import fr.urssaf.image.sae.webservices.component.SpringConfiguration;
import fr.urssaf.image.sae.webservices.security.SecurityService;
import fr.urssaf.image.sae.webservices.security.exception.SaeAccessDeniedAxisFault;
import fr.urssaf.image.sae.webservices.security.exception.VIEmptyAxisFault;
import fr.urssaf.image.sae.webservices.security.exception.VIVerificationAxisFault;

/**
 * Classe aspect d'authentification des web services<br>
 * L'aspect sur les méthodes du skeleton du web services
 * {@link fr.urssaf.image.sae.webservices.skeleton.SaeServiceSkeleton}<br>
 * <br>
 * L'authentification s'appuie sur WS-Security et le jeton SAML 2.0 contenu
 * dedans
 * 
 * 
 */
@Aspect
public class AuthenticateHandler {

   private static final Logger LOG = Logger
         .getLogger(AuthenticateHandler.class);

   private static final String SKELETON = "fr.urssaf.image.sae.webservices.skeleton.*Skeleton.*Secure(..)";

   private static final String METHODE = "execution(public * " + SKELETON + ")";

   private final SecurityService securityService;

   /**
    * instanciation de {@link SecurityService}
    */
   public AuthenticateHandler() {

      this(SpringConfiguration.getService(SecurityService.class));

   }

   protected AuthenticateHandler(SecurityService securityService) {

      this.securityService = securityService;

   }

   /**
    * Encapsulation des exception de type {@link AccessDeniedException} dans une
    * exception AxisFault<br>
    * instanciation d'une {@link SaeAccessDeniedAxisFault}<br>
    * <br>
    * Toutes les méthodes du skeleton sont concernées
    * 
    * @param exception
    *           seules les {@link AccessDeniedException} sont prises en compte
    * @throws AxisFault
    *            les autorisations sont insuffisantes dans le contexte de
    *            sécurité
    */
   @AfterThrowing(pointcut = "execution(public * fr.urssaf.image.sae.webservices.skeleton.*Skeleton.*(..))", throwing = "exception")
   public final void deniedAccess(Throwable exception) throws AxisFault {

      if (exception instanceof AccessDeniedException) {

         throw new SaeAccessDeniedAxisFault((AccessDeniedException) exception);
      }

   }

/**
    * instanciation d'un contexte de sécurité à partir des informations
    * contenues dans le header du message SOAP<br>
    * <br>
    * Extraction du jeton SAML et récupération des PAGM contenues dans la balise
    * <code>&lt;PAGM></code> <br>
    * Appel de {@link SecurityService#authentification(Element)) pour créer d'un
    * contexte de sécurity<br>
    * <br>
    * Seules les méthodes finissant par Secure sont concernées
    * 
    * @throws AxisFault le VI comporte une erreur ou est absent
    */
   @Before(METHODE)
   public final void authenticate() throws AxisFault {

      String prefixeLog = "Demande de consommation d'un service web sécurisé - ";

      LOG.info(prefixeLog + "Mise en place du contexte de sécurité");

      MessageContext msgCtx = MessageContext.getCurrentMessageContext();

      // @SuppressWarnings( { "unchecked", "PMD.ReplaceVectorWithList" })
      // Vector results = (Vector) msgCtx
      // .getProperty(WSHandlerConstants.RECV_RESULTS);
      //
      // if (results != null) {
      //
      // for (Object result : results) {
      //
      // if (result instanceof WSHandlerResult) {
      //
      // WSUsernameTokenPrincipal userPrincipal = this
      // .getWSUsernameTokenPrincipal((WSHandlerResult) result);
      //
      // LOG.debug("user principal --> name :" + userPrincipal.getName());
      // LOG.debug("user principal --> password :"
      // + userPrincipal.getPassword());
      //
      // }
      //
      // }
      // }

      SOAPHeader header = msgCtx.getEnvelope().getHeader();

      if (header == null) {
         LOG.error(prefixeLog
               + "Erreur : pas d'en-tête SOAP dans le message SOAP");
         throw new VIEmptyAxisFault();
      }

      OMElement security = header.getFirstChildWithName(new QName(
            WSConstants.WSSE_NS, WSConstants.WSSE_LN));

      if (security == null) {
         LOG.error(prefixeLog
               + "Erreur : pas de WS-Security dans l'en-tête SOAP");
         throw new VIEmptyAxisFault();
      }

      OMElement saml = security.getFirstChildWithName(new QName(
            "urn:oasis:names:tc:SAML:2.0:assertion", WSConstants.ASSERTION_LN));

      Element identification = null;
      if (saml == null) {
         LOG
               .error(prefixeLog
                     + "Erreur : pas d'assertion SAML dans la partie WS-Security du message SOAP");
         throw new VIEmptyAxisFault();

      } else {
         try {
            identification = XMLUtils.toDOM(saml);
         } catch (Exception e) {
            LOG.error(prefixeLog
                  + "Erreur lors de la mise en place de l'authentification : "
                  + e.toString());
            throw new IllegalStateException();
         }

         try {
            securityService.authentification(identification);
         } catch (VIVerificationException e) {
            LOG.error(prefixeLog
                  + "Erreur lors de la mise en place de l'authentification : "
                  + e.toString());
            throw new VIVerificationAxisFault(e);
         }

      }

      LOG.info(prefixeLog + "Le contexte de sécurité est en place");

   }

}
