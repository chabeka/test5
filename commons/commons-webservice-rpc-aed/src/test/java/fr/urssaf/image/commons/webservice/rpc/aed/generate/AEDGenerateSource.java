package fr.urssaf.image.commons.webservice.rpc.aed.generate;

import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.generate.GenerateSourceAxis;
import fr.urssaf.image.commons.webservice.rpc.aed.context.AEDSSLContextFactory;

public final class AEDGenerateSource extends GenerateSourceAxis {

	private AEDGenerateSource(String path,String url) {
		super(path,url);

	}

	public static void main(String[] args) {

		HttpsURLConnection.setDefaultSSLSocketFactory(AEDSSLContextFactory.getSSLContext()
				.getSocketFactory());
		
		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load("aed.properties");

		String url = prop.getProperty("url");
		String path = prop.getProperty("path");

		AEDGenerateSource generateSource = new AEDGenerateSource(path,url);
		generateSource.generate();

	}
}
