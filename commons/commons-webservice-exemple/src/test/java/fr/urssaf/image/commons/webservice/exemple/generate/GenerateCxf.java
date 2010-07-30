package fr.urssaf.image.commons.webservice.exemple.generate;

import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceCxf;

public class GenerateCxf {


	private String url;
	
	private String ssl;
	
	private String path;
	
	public void setProperties(String properties){
		
		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load(properties);

		this.url = prop.getProperty("url");
		this.ssl = prop.getProperty("ssl");
		this.path = prop.getProperty("path");
	}

	public void execute(){

		String url = this.url;
		if(HttpConnection.isHttps()){
			url = this.ssl;
		}
		
		GenerateSourceCxf generateSource = new GenerateSourceCxf(path, url);
		generateSource.generate();

	}
}
