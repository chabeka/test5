package fr.urssaf.image.commons.springsecurity.webservice.custom.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.commons.springsecurity.webservice.custom.modele.Modele;
import fr.urssaf.image.commons.springsecurity.webservice.custom.modele.SimpleService;
import fr.urssaf.image.commons.springsecurity.webservice.custom.modele.SimpleWebService;
import fr.urssaf.image.commons.springsecurity.webservice.custom.wssecurity.PasswordCallbackHandler;
import fr.urssaf.image.commons.springsecurity.webservice.custom.wssecurity.SecurityContextInterceptor;

@Service
public class SimpleServiceImpl {

   private final SimpleWebService service;

   @Autowired
   public SimpleServiceImpl(SecurityContextInterceptor interceptor) {

      SimpleService simpleService = new SimpleService();
      this.service = simpleService.getSimpleServicePort();

      Client client = ClientProxy.getClient(this.service);
      Endpoint cxfEndpoint = client.getEndpoint();

      Map<String, Object> outProps = new HashMap<String, Object>();
      WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);

      cxfEndpoint.getOutInterceptors().add(wssOut);
      cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());
      cxfEndpoint.getOutInterceptors().add(interceptor);

      StringBuffer action = new StringBuffer();
      action.append(WSHandlerConstants.USERNAME_TOKEN);

      outProps.put(WSHandlerConstants.ACTION, action.toString());
      outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
            PasswordCallbackHandler.class.getName());

      // USERNAME_TOKEN
      outProps.put(WSHandlerConstants.USER, "myuser");
      outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);

   }

   public final void save() {
      service.save("title", "text");
   }

   public final Modele load() {
      return service.load();
   }

}
