package fr.urssaf.image.sae.webservices.security;

import java.util.List;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.util.XMLUtils;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.opensaml.saml2.core.Assertion;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.opensaml.SamlAssertionService;
import fr.urssaf.image.sae.saml.opensaml.service.SamlXML;

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

   private static final String SKELETON = "fr.urssaf.image.sae.webservices.skeleton.*Skeleton.*(..)";

   private static final String METHODE = "execution(public * " + SKELETON + ")";

   private final SamlAssertionService assertionService;

   /**
    * instanciation de {@link SamlAssertionService}
    */
   public AuthenticateHandler() {

      this.assertionService = new SamlAssertionService();
   }

   /**
    * instanciation d'un contexte de sécurité à partir des informations
    * contenues dans le header du message SOAP<br>
    * <br>
    * Instanciation {@link WSUsernameTokenPrincipal} à partir de la balise
    * <code>&lt;wsse:UsernameToken></code><br>
    * Extraction du jeton SAML et récupération des PAGM contenues dans la balise
    * <code>&lt;PAGM></code> <br>
    * Instancition d'un contexte de sécurity pour Spring Security<br>
    * La méthode {@link SecurityContextHolder#getContext()} permet d'accéder à
    * ce contexte de securité
    * 
    */
   @Before(METHODE)
   public final void authenticate() {
      MessageContext msgCtx = MessageContext.getCurrentMessageContext();

      @SuppressWarnings( { "unchecked", "PMD.ReplaceVectorWithList" })
      Vector results = (Vector) msgCtx
            .getProperty(WSHandlerConstants.RECV_RESULTS);

      WSUsernameTokenPrincipal userPrincipal = null;

      if (results != null) {

         for (Object result : results) {

            if (result instanceof WSHandlerResult) {

               userPrincipal = this
                     .getWSUsernameTokenPrincipal((WSHandlerResult) result);

            }

         }
      }

      SOAPHeader header = msgCtx.getEnvelope().getHeader();
      if (header != null) {
         OMElement security = header.getFirstChildWithName(new QName(
               WSConstants.WSSE_NS, "Security"));

         OMElement saml = security.getFirstChildWithName(new QName(
               "urn:oasis:names:tc:SAML:2.0:assertion", "Assertion"));

         Element samlElement = null;
         if (saml != null) {
            try {
               samlElement = XMLUtils.toDOM(saml);
            } catch (Exception e) {
               throw new IllegalStateException(e);
            }

            Assertion assertion = (Assertion) SamlXML.unmarshaller(samlElement);

            SamlAssertionData data = assertionService.load(assertion);

            List<String> pagms = data.getAssertionParams().getCommonsParams()
                  .getPagm();

            List<GrantedAuthority> authorities = AuthorityUtils
                  .createAuthorityList(StringUtils.toStringArray(pagms));

            Authentication authentication = AuthenticationFactory
                  .createAuthentication(userPrincipal, authorities);

            SecurityContextHolder.getContext()
                  .setAuthentication(authentication);

         }

      }

   }

   private WSUsernameTokenPrincipal getWSUsernameTokenPrincipal(
         WSHandlerResult wsHandlerResult) {

      @SuppressWarnings( { "unchecked", "PMD.ReplaceVectorWithList" })
      Vector results = wsHandlerResult.getResults();

      WSUsernameTokenPrincipal principal = null;

      if (results != null) {

         for (Object result : results) {

            if (result instanceof WSSecurityEngineResult) {

               WSSecurityEngineResult wsSecurity = (WSSecurityEngineResult) result;

               principal = (WSUsernameTokenPrincipal) wsSecurity
                     .get(WSSecurityEngineResult.TAG_PRINCIPAL);

            }

         }

      }

      return principal;
   }

}
