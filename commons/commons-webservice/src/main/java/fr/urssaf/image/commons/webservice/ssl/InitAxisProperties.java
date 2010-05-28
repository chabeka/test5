package fr.urssaf.image.commons.webservice.ssl;

import org.apache.axis.AxisProperties;

public final class InitAxisProperties {

	private InitAxisProperties(){
		
	}
	
	public static void initSoketSecureFactory(Class<? extends AbstractJSSESocketFactory> classe) {

		AxisProperties.setProperty("axis.socketSecureFactory", classe
				.getCanonicalName());
	}
}
