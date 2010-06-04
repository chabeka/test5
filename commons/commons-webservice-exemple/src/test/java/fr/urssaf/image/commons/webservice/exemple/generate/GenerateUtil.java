package fr.urssaf.image.commons.webservice.exemple.generate;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class GenerateUtil {

	public static void execute(String generate) throws Exception {

		((GenerateCxf) (new XmlBeanFactory(new ClassPathResource(
				"applicationContext.xml"))).getBean(generate)).execute();
	}
}
