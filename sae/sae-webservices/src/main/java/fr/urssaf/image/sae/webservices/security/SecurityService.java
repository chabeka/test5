package fr.urssaf.image.sae.webservices.security;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.extensions.spring.receivers.ApplicationContextHolder;
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
import fr.urssaf.image.sae.vi.modele.VISignVerifParams;
import fr.urssaf.image.sae.vi.service.WebServiceVIService;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationFactory;

/**
 * Service de sécurisation du service web par authentification
 * 
 * 
 */
public class SecurityService {

   private final WebServiceVIService service;

   private final URI serviceVise;

   private final String idAppliClient;
   
   private final VISignVerifParams signVerifParams;

   /**
    * Constructeur
    */
   public SecurityService() {
    
      this.service = new WebServiceVIService();

      try {
         this.serviceVise = new URI("http://sae.urssaf.fr");
      } catch (URISyntaxException e) {
         throw new IllegalStateException(e);
      }

      this.idAppliClient = "urn:ISSUER_NON_RENSEIGNE";

      signVerifParams = new VISignVerifParams();
      
      chargeElementsVerifSignature();

   }
   
   
   
   private void chargeElementsVerifSignature() {
      
      // TODO : mécanisme de chargement des éléments permettant de vérifier la signature

      // En attendant, on se base sur des éléments de test 
      chargeCertificatsEtCRLPourLesTests();
      
   }
   
   
   private void chargeCertificatsEtCRLPourLesTests() {
      
      
      // DefaultResourceLoader defaultResLoad = new DefaultResourceLoader ();
      
      // ----------------------------
      // Chargement des AC
      // ----------------------------
      
      
      List<Resource> listeAC = new ArrayList<Resource>();
      listeAC.add(ApplicationContextHolder.getContext().getResource(
            "classpath:security/AC/AC-01_IGC_A.crt"));
      
      SecurityUtils.signVerifParamsSetCertifsAC(
            signVerifParams, 
            listeAC);
      
      // ----------------------------
      // Chargement des CRL
      // ----------------------------
      
      List<Resource> listeCRL = new ArrayList<Resource>();
      listeCRL.add(ApplicationContextHolder.getContext().getResource(
            "classpath:security/CRL/CRL_AC-01_Pseudo_IGC_A.crl"));
      listeCRL.add(ApplicationContextHolder.getContext().getResource(
            "classpath:security/CRL/CRL_AC-02_Pseudo_ACOSS.crl"));
      listeCRL.add(ApplicationContextHolder.getContext().getResource(
            "classpath:security/CRL/CRL_AC-03_Pseudo_Appli.crl"));
      
      SecurityUtils.signVerifParamsSetCRL(
            signVerifParams, 
            listeCRL);
      
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
            serviceVise, idAppliClient, signVerifParams);

      List<GrantedAuthority> authorities = AuthorityUtils
            .createAuthorityList(StringUtils.toStringArray(viExtrait.getPagm()));

      Authentication authentication = AuthenticationFactory
            .createAuthentication(viExtrait.getIdUtilisateur(), "nc",
                  authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);
   }

}
