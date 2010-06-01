package fr.urssaf.image.commons.webservice.rpc.aed.context;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import fr.urssaf.image.commons.webservice.ssl.MySSLContextFactory;

public class AEDSSLContextFactory {

	public static SSLContext getSSLContext() {

		MySSLContextFactory ctx = (MySSLContextFactory) (new XmlBeanFactory(
				new ClassPathResource("applicationContext.xml")))
				.getBean("SSLContextFactory");

		return ctx.getSSLContext();

	}
}
