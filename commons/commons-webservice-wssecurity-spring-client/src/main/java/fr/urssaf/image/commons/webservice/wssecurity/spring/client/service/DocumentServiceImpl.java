package fr.urssaf.image.commons.webservice.wssecurity.spring.client.service;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

import fr.urssaf.image.commons.webservice.wssecurity.spring.client.modele.Document;
import fr.urssaf.image.commons.webservice.wssecurity.spring.client.modele.DocumentService_Service;

public class DocumentServiceImpl implements DocumentService {

	private fr.urssaf.image.commons.webservice.wssecurity.spring.client.modele.DocumentService port;

	public DocumentServiceImpl() {

		DocumentService_Service service = new DocumentService_Service();

		this.port = service.getDocumentServicePort();

		Client client = ClientProxy.getClient(this.port);
		Endpoint cxfEndpoint = client.getEndpoint();
		initWSS4JOutInterceptor(cxfEndpoint);

	}

	private void initWSS4JOutInterceptor(Endpoint cxfEndpoint) {

		Map<String, Object> outProps = new HashMap<String, Object>();
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);

		cxfEndpoint.getOutInterceptors().add(wssOut);
		cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());

		StringBuffer action = new StringBuffer();
		action.append(" " + WSHandlerConstants.USERNAME_TOKEN);
		action.append(" " + WSHandlerConstants.SIGNATURE);
		action.append(" " + WSHandlerConstants.ENCRYPT);

		action.append(" " + WSHandlerConstants.TIMESTAMP);

		outProps.put(WSHandlerConstants.ACTION, action.toString());
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
				ClientPasswordCallback.class.getName());

		// USERNAME_TOKEN
		outProps.put(WSHandlerConstants.USER, "myuser");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);

		// SIGNATURE
		outProps.put(WSHandlerConstants.SIGNATURE_USER, "myclientkey");
		outProps.put(WSHandlerConstants.SIG_PROP_FILE,
				"clientKeyStore.properties");

		// ENCRYPT
		outProps.put(WSHandlerConstants.ENCRYPTION_USER, "myservicekey");
		outProps.put(WSHandlerConstants.ENC_PROP_FILE,"clientKeyStore.properties");

		StringBuffer parts = new StringBuffer();
		
		parts.append("{Content}{http://www.w3.org/2000/09/xmldsig#}Signature;");
		parts.append("{Content}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}UsernameToken;");
		parts.append("{Content}{}Body;");

		outProps.put(WSHandlerConstants.ENCRYPTION_PARTS, parts.toString());
	}

	@Override
	public List<Document> allDocuments() throws RemoteException {
		return port.allDocuments();
	}

	@Override
	public Document get(int id) throws RemoteException {
		return port.getDocument(id);
	}

	@Override
	public void save(Document document) throws RemoteException {
		port.save(document);

	}

}
