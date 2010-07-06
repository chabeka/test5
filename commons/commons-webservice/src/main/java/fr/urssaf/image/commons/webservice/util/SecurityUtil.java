package fr.urssaf.image.commons.webservice.util;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.springframework.core.io.Resource;

public final class SecurityUtil {

	private SecurityUtil() {

	}

	public static KeyStore getKeyStore(Resource certificat, String password,
			String keyStoreType) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {

		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(certificat.getInputStream(), password.toCharArray());

		return keyStore;
	}
	
}
