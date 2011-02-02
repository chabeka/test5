package fr.urssaf.image.commons.springsecurity.webservice.custom.wssecurity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Set;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JInterceptor;
import org.apache.log4j.Logger;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

@Component
public class SecurityContextInterceptor extends AbstractWSS4JInterceptor {

   private static final Logger LOG = Logger
         .getLogger(SecurityContextInterceptor.class);

   public SecurityContextInterceptor() {

      super();
      setPhase(Phase.POST_PROTOCOL);

   }

   private String samlFile;

   public final void setSAMLFile(String samlFile) {
      this.samlFile = samlFile;
   }

   @Override
   public final void handleMessage(SoapMessage msg) {

      msg.getInterceptorChain().add(new SecurityContextInterceptorInternal());
   }

   final class SecurityContextInterceptorInternal implements
         PhaseInterceptor<SoapMessage> {

      @Override
      public Set<String> getAfter() {
         return Collections.emptySet();
      }

      @Override
      public Set<String> getBefore() {
         return Collections.emptySet();
      }

      @Override
      public String getId() {
         return SecurityContextInterceptorInternal.class.getName();
      }

      @Override
      public String getPhase() {
         return Phase.POST_PROTOCOL;
      }

      @Override
      public void handleFault(SoapMessage message) {
         // nothing
      }

      @Override
      public void handleMessage(SoapMessage msg) {

         if(samlFile == null){
            throw new IllegalStateException("'samlFile' is required");
         }
        
         SOAPMessage soapMsg = msg.getContent(SOAPMessage.class);

         try {
            SOAPHeader soapHeader = soapMsg.getSOAPHeader();
            SOAPElement wsseSecurity = (SOAPElement) soapHeader
                  .getElementsByTagName("wsse:Security").item(0);

            FileInputStream inputStream = new FileInputStream(samlFile);

            SAMLAssertion samlAssertion = new SAMLAssertion(inputStream);

            SOAPFactory soapFactory = SOAPFactory.newInstance();

            SOAPElement assertionSOAP = soapFactory
                  .createElement((Element) samlAssertion.toDOM());
            wsseSecurity.addChildElement(assertionSOAP);

            LOG.debug(samlAssertion.getId());

         } catch (SOAPException e) {

            throw new Fault(e);

         } catch (SAMLException e) {

            throw new Fault(e);

         } catch (FileNotFoundException e) {

            throw new Fault(e);
         }

      }
   }

}
