package fr.urssaf.image.commons.webservice.ssl;

import org.springframework.core.io.Resource;

public class MySSLContextSource {

	private Resource certificat;

	private String certifPassword;
	
	public Resource getCertificat() {
		return certificat;
	}

	public void setCertificat(Resource certificat) {
		this.certificat = certificat;
	}

	public String getCertifPassword() {
		return certifPassword;
	}

	public void setCertifPassword(String certifPassword) {
		this.certifPassword = certifPassword;
	}


}
