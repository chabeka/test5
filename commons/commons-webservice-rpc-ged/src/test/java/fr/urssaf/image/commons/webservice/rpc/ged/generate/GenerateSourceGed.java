package fr.urssaf.image.commons.webservice.rpc.ged.generate;

import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceAxis;

public class GenerateSourceGed extends GenerateSourceAxis {

	public GenerateSourceGed(String path,String url) {
		super(path,url);

	}

	public static void main(String[] args) {

		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load("ged.properties");

		String url = prop.getProperty("url");
		String path = prop.getProperty("path");

		GenerateSourceGed generateSource = new GenerateSourceGed(path,url);
		generateSource.generate();

	}
}
