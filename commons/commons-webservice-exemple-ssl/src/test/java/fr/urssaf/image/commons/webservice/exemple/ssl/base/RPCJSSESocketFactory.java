package fr.urssaf.image.commons.webservice.exemple.ssl.base;

import java.util.Hashtable;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import fr.urssaf.image.commons.webservice.ssl.AbstractJSSESocketFactory;
import fr.urssaf.image.commons.webservice.ssl.MySSLContextFactory;

public class RPCJSSESocketFactory extends AbstractJSSESocketFactory {

	
	private SSLContext sslContext;
	
	public RPCJSSESocketFactory(Hashtable<String, String> attributes) {
		super(attributes);
		sslContext = ((MySSLContextFactory) (new XmlBeanFactory(
				new ClassPathResource("applicationContext.xml")))
				.getBean("SSLContextFactory")).getSSLContext();
		
	}

	@Override
	public SSLContext getSSLContext() {
		return sslContext;
	}

}
