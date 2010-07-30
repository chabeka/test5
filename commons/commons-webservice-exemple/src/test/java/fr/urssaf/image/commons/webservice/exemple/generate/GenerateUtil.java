package fr.urssaf.image.commons.webservice.exemple.generate;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public final class GenerateUtil {
   
   private GenerateUtil(){
      
   }

	public static void execute(String generate) {

		((GenerateCxf) (new XmlBeanFactory(new ClassPathResource(
				"applicationContext.xml"))).getBean(generate)).execute();
	}
}
