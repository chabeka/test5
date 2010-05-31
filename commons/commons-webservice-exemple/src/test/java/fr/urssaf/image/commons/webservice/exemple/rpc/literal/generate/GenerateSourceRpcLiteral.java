package fr.urssaf.image.commons.webservice.exemple.rpc.literal.generate;

import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceCxf;

public class GenerateSourceRpcLiteral extends GenerateSourceCxf {

	public GenerateSourceRpcLiteral(String path,String url) {
		super(path,url);

	}
	
	public static void generation() throws Exception{
		
		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load("rpc_literal.properties");

		String url = prop.getProperty("url");
		String path = prop.getProperty("path");

		GenerateSourceRpcLiteral generateSource = new GenerateSourceRpcLiteral(path,url);
		generateSource.generate();
	}

	public static void main(String[] args) throws Exception {

		generation();

	}
}
