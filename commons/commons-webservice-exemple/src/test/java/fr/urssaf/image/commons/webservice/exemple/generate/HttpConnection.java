package fr.urssaf.image.commons.webservice.exemple.generate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import fr.urssaf.image.commons.webservice.ssl.MySSLContextFactory;

public class HttpConnection {
	
	
	private static boolean https;
	
	public void setContextFactory(MySSLContextFactory ctx){
		
		HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSSLContext()
				.getSocketFactory());

		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		https = true;
	}
	
	public static boolean isHttps(){
		return https;
	}
	
	

}
