package fr.urssaf.image.commons.webservice.ssl;

import java.io.IOException;
import java.util.Hashtable;

import javax.net.ssl.SSLContext;

import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.axis.components.net.SecureSocketFactory;

public abstract class AbstractJSSESocketFactory extends JSSESocketFactory implements
		SecureSocketFactory {

	public AbstractJSSESocketFactory(Hashtable<String, String> attributes) {
		super(attributes);
	}

	@Override
	protected void initFactory() throws IOException {

		sslFactory = getSSLContext().getSocketFactory();

	}

	public abstract SSLContext getSSLContext();

}
