package fr.urssaf.image.sae.webservices.security;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.vi.exception.VIVerificationException;
import fr.urssaf.image.sae.vi.modele.VIContenuExtrait;
import fr.urssaf.image.sae.vi.modele.VIPagm;
import fr.urssaf.image.sae.vi.modele.VISignVerifParams;
import fr.urssaf.image.sae.vi.service.WebServiceVIService;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationContext;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationFactory;
import fr.urssaf.image.sae.webservices.security.spring.AuthenticationToken;
import fr.urssaf.image.sae.webservices.util.ResourceUtils;

/**
 * Service de sécurisation du service web par authentification
 * 
 * 
 */
public class SecurityService {

   private static final Logger LOG = Logger.getLogger(SecurityService.class);

   private final WebServiceVIService service;

   private final URI serviceVise;

   private final String idAppliClient;

   private final VISignVerifParams signVerifParams;

   /**
    * Instanciation de {@link WebServiceVIService}
    * 
    * @param acResource
    *           répertoire des AC racines
    * @param crlResource
    *           répertoire des CRLs
    */
   public SecurityService(FileSystemResource acResource,
         FileSystemResource crlResource) {

      this.service = new WebServiceVIService();

      try {
         this.serviceVise = new URI("http://sae.urssaf.fr");
      } catch (URISyntaxException e) {
         throw new IllegalStateException(e);
      }

      this.idAppliClient = "urn:ISSUER_NON_RENSEIGNE";

      signVerifParams = new VISignVerifParams();

      // ----------------------------
      // Chargement des AC
      // ----------------------------
      loadACResources(acResource);

      // ----------------------------
      // Chargement des CRL
      // ----------------------------
      loadCRLResources(crlResource);
   }

   private void loadACResources(FileSystemResource acResource) {

      List<Resource> listeAC = ResourceUtils.loadResources(acResource, "crt");
      SecurityUtils.signVerifParamsSetCertifsAC(signVerifParams, listeAC);

   }

   private void loadCRLResources(FileSystemResource crlResource) {

      List<Resource> listeCRL = ResourceUtils.loadResources(crlResource, "crl");
      SecurityUtils.signVerifParamsSetCRL(signVerifParams, listeCRL);

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

      logVI(viExtrait);

      String[] roles = loadDroitsApplicatifs(viExtrait.getPagm());

      AuthenticationToken authentication = AuthenticationFactory
            .createAuthentication(viExtrait.getIdUtilisateur(), "nc", roles,
                  loadActionsUnitaires(viExtrait.getPagm()));

      AuthenticationContext.setAuthenticationToken(authentication);
   }

   private void logVI(VIContenuExtrait viExtrait) {

      String prefixeLog = "Informations extraites du VI : ";

      // LOG des PAGM
      if ((viExtrait != null) && (viExtrait.getPagm() != null)) {
         StringBuffer sBufferMsgLog = new StringBuffer();
         sBufferMsgLog.append(prefixeLog);
         sBufferMsgLog.append("PAGM(s) : ");
         for (VIPagm pagm : viExtrait.getPagm()) {
            sBufferMsgLog.append(pagm.getDroitApplicatif());
            sBufferMsgLog.append(';');
            sBufferMsgLog.append(pagm.getPerimetreDonnees());
            sBufferMsgLog.append(' ');
         }
         LOG.info(sBufferMsgLog.toString());
      }

      // LOG du code application
      LOG.info(prefixeLog + "Code application : " + viExtrait.getCodeAppli());

      // LOG de l'identifiant utilisateur
      LOG.info(prefixeLog + "Identifiant utilisateur : "
            + viExtrait.getIdUtilisateur());

   }

   private String[] loadDroitsApplicatifs(List<VIPagm> pagms) {
      String[] roles = new String[pagms.size()];
      for (int i = 0; i < pagms.size(); i++) {
         roles[i] = pagms.get(i).getDroitApplicatif();
      }
      return roles;
   }

   private ActionsUnitaires loadActionsUnitaires(List<VIPagm> pagms) {

      ActionsUnitaires actionsUnitaires = new ActionsUnitaires();

      for (VIPagm viPagm : pagms) {

         actionsUnitaires.addAction(viPagm.getDroitApplicatif(), viPagm
               .getPerimetreDonnees());
      }
      return actionsUnitaires;
   }

}
