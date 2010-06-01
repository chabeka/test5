package fr.urssaf.image.commons.webservice.ssl;

import org.springframework.core.io.Resource;

public class MySSLContextSource {

	private Resource certificat;

	private String certificatPassword;
	
	public Resource getCertificat() {
		return certificat;
	}

	public void setCertificat(Resource certificat) {
		this.certificat = certificat;
	}

	public String getCertificatPassword() {
		return certificatPassword;
	}

	public void setCertificatPassword(String certificatPassword) {
		this.certificatPassword = certificatPassword;
	}


}
