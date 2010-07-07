package fr.urssaf.image.commons.webservice.ssl;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.webservice.util.SecurityUtil;

public class MySSLContextFactory {

	private static final Logger LOGGER = Logger
			.getLogger(MySSLContextFactory.class);

	private SSLContext ctx;

	private MySSLContextSource contextSource;

	private String keyStoreType = "PKCS12";

	private void initFactory() {

		KeyStore keyStore = null;

		try {
			keyStore = SecurityUtil.getKeyStore(contextSource.getCertificat(),
					contextSource.getCertifPassword(), keyStoreType);

			// RECUPERATION DU COUPLE CLE PRIVEE/PUBLIQUE ET DU CERTIFICAT
			// PUBLIQUE

			try {
				
				X509Certificate cert = null;
				PrivateKey privatekey = null;
				PublicKey publickey = null;
				String ALIAS = "";
				KeyStore jks = null;

				jks = KeyStore.getInstance("JKS");
				jks.load(null, null);
				Enumeration<String> enumAlias = keyStore.aliases();

				List<String> listAlias = new ArrayList<String>();

				while (enumAlias.hasMoreElements()) {
					String alias = enumAlias.nextElement();
					LOGGER.debug(alias);
					listAlias.add(alias);
				}

				String[] aliases = listAlias.toArray(new String[listAlias
						.size()]);
				for (int i = 0; i < aliases.length; i++) {
					if (keyStore.isKeyEntry(aliases[i])) {
						LOGGER.debug(aliases[i]);
						ALIAS = aliases[i];
						break;
					}
				}
				char[] password = contextSource.getCertifPassword()
						.toCharArray();

				privatekey = (PrivateKey) keyStore.getKey(ALIAS, password);
				cert = (X509Certificate) keyStore.getCertificate(ALIAS);
				publickey = keyStore.getCertificate(ALIAS).getPublicKey();

				jks.setCertificateEntry(ALIAS, cert);

				KeyManagerFactory kmf = KeyManagerFactory
						.getInstance("SunX509");
				kmf.init(keyStore, password);

				TrustManagerFactory tmf = TrustManagerFactory
						.getInstance("SunX509");
				tmf.init(jks);

				ctx = SSLContext.getInstance("SSL");

				ctx.init(kmf.getKeyManagers(),
						new TrustManager[] { new X509TrustManager() {

							public java.security.cert.X509Certificate[] getAcceptedIssuers() {
								return new X509Certificate[0];
							}

							public void checkClientTrusted(
									java.security.cert.X509Certificate[] chain,
									String authType) {
								// aucune implémentation
							}

							public void checkServerTrusted(
									java.security.cert.X509Certificate[] chain,
									String authType) {
								// aucune implémentation
							}
						} }, null);
				
				LOGGER.debug("alias:" + ALIAS);
				LOGGER.debug(cert.getType());
				LOGGER.debug("private key:" + privatekey);
				LOGGER.debug("public key:" + publickey);

			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

			

		} catch (Exception e) {
			LOGGER.error("Erreur: fichier " + contextSource.getCertificat()
					+ " n'est pas un fichier " + this.keyStoreType
					+ " valide ou passphrase incorrect");
			LOGGER.error(e.getMessage(), e);
			return;
		}
	}

	private void initSSLContext() {

		try {
			ctx = SSLContext.getInstance("SSL");
			ctx.init(null, new TrustManager[] { new X509TrustManager() {

				@Override
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// aucune implémentation
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// aucune implémentation
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, null);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return;
		}

	}

	public void setSSLContextSource(MySSLContextSource contextSource) {
		this.contextSource = contextSource;
	}

	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public SSLContext getSSLContext() {

		synchronized (this) {
			if (this.ctx == null) {
				if (contextSource == null) {
					initSSLContext();
				} else {
					initFactory();
				}

			}
		}

		return this.ctx;
	}

}
