package fr.urssaf.image.commons.webservice.exemple.document.generate;

import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceCxf;

public class DocumentGenerateSource extends GenerateSourceCxf {

	public DocumentGenerateSource(String path, String url) {
		super(path, url);

	}

	public static void generation() throws Exception {

		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load("document.properties");

		String url = prop.getProperty("url");
		String path = prop.getProperty("path");

		DocumentGenerateSource generateSource = new DocumentGenerateSource(
				path, url);
		generateSource.generate();

	}

	public static void main(String[] args) throws Exception {

		generation();

	}
}
