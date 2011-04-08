package fr.urssaf.image.sae.webservices.security;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.vi.exception.VIVerificationException;
import fr.urssaf.image.sae.vi.modele.VIContenuExtrait;
import fr.urssaf.image.sae.vi.service.WebServiceVIService;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationFactory;

/**
 * Service de sécurisation du service web par authentification
 * 
 * 
 */
public class SecurityService {

   private final WebServiceVIService service;

   private final KeyStore keystore;

   private final URI serviceVise;

   private final String idAppliClient;

   private final List<X509CRL> x509CRLs;

   /**
    * 
    * <ul>
    * <li>instancation de {@link WebServiceVIService}</li>
    * <li>instanciation d'un keystore
    * <ul>
    * <li><b>password</b> : <code>hiUnk6O3QnRN</code></li>
    * <li><b>p12</b> : <code>/Portail_Image.p12</code></li>
    * </ul>
    * <li><b>serviceVise</b> : <code>saeServices</code></li>
    * <li><b>idAppliClient</b> : <code>urn:ISSUER_NON_RENSEIGNE</code></li>
    * <li><b>crl</b></li>
    * </li>
    * </ul>
    * 
    * @param keystore
    *           fichier du p12 not null
    * @param crls
    *           fichiers des CRLs au moins 1
    */
   public SecurityService(Resource keystore, Resource... crls) {
    
      Assert.notNull(keystore, "'keystore' is required");
      Assert.notEmpty(crls, "'crls' is required and not empty");

      this.service = new WebServiceVIService();

      String password = "hiUnk6O3QnRN";

      this.keystore = initKeystore(keystore, password);

      try {
         this.serviceVise = new URI("http://sae.urssaf.fr");
      } catch (URISyntaxException e) {
         throw new IllegalStateException(e);
      }

      this.idAppliClient = "urn:ISSUER_NON_RENSEIGNE";

      this.x509CRLs = new ArrayList<X509CRL>();
      for (Resource crl : crls) {

         this.x509CRLs.add(initCRL(crl));

      }

   }

   protected static final KeyStore initKeystore(Resource keystore,
         String password) {

      try {
         return SecurityUtils.createKeyStore(keystore, password);
      } catch (GeneralSecurityException e) {
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   protected static final X509CRL initCRL(Resource crl) {

      try {
         return SecurityUtils.createCRL(crl);
      } catch (GeneralSecurityException e) {
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }

   }

   /**
    * Création d'un contexte de sécurité par partir du Vecteur d'indentifcation<br>
    * <br>
    * Paramètres du {@link Authentication} de ttype Anonymous
    * <ul>
    * <li>Credentials : empty</li>
    * <li>Principal : {@link VIContenuExtrait#getIdUtilisateur()}</li>
    * <li>Authorities : {@link VIContenuExtrait#getPagm()}</li>
    * </ul>
    * 
    * @param identification
    *           Vecteur d'identification
    * @throws VIVerificationException
    *            exception levée par le VI
    */
   public final void authentification(Element identification)
         throws VIVerificationException {

      Assert
            .notNull(
                  identification,
                  "Le paramètre 'identification' n'est pas renseigné alors qu'il est obligatoire ");

      VIContenuExtrait viExtrait;

      viExtrait = this.service.verifierVIdeServiceWeb(identification,
            serviceVise, idAppliClient, keystore, x509CRLs);

      List<GrantedAuthority> authorities = AuthorityUtils
            .createAuthorityList(StringUtils.toStringArray(viExtrait.getPagm()));

      Authentication authentication = AuthenticationFactory
            .createAuthentication(viExtrait.getIdUtilisateur(), "nc",
                  authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);
   }

}
