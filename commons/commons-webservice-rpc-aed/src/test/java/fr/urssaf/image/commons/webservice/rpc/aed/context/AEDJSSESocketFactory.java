package fr.urssaf.image.commons.webservice.rpc.aed.context;

import java.util.Hashtable;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import fr.urssaf.image.commons.webservice.ssl.AbstractJSSESocketFactory;
import fr.urssaf.image.commons.webservice.ssl.MySSLContextFactory;

public class AEDJSSESocketFactory extends AbstractJSSESocketFactory {

   private final SSLContext sslContext;

   @SuppressWarnings( { "PMD.ReplaceHashtableWithMap",
         "PMD.ConstructorCallsOverridableMethod" })
   public AEDJSSESocketFactory(Hashtable<String, String> attributes) {
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
