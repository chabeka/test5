package fr.urssaf.image.commons.webservice.exemple.rpc.encoded.generate;

import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceCxf;

public class GenerateSourceRpcEncoded extends GenerateSourceCxf {

	public GenerateSourceRpcEncoded(String path,String url) {
		super(path,url);

	}
	
	public static void generation() throws Exception{
		
		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load("rpc_encoded.properties");

		String url = prop.getProperty("url");
		String path = prop.getProperty("path");

		GenerateSourceRpcEncoded generateSource = new GenerateSourceRpcEncoded(path,url);
		generateSource.generate();
	}

	public static void main(String[] args) throws Exception {

		generation();

	}
}
