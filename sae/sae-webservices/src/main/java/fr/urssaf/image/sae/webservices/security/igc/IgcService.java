package fr.urssaf.image.sae.webservices.security.igc;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.igc.modele.IgcConfig;
import fr.urssaf.image.sae.igc.util.TextUtils;
import fr.urssaf.image.sae.webservices.security.SecurityUtils;
import fr.urssaf.image.sae.webservices.security.igc.exception.LoadCertifsAndCrlException;
import fr.urssaf.image.sae.webservices.security.igc.modele.CertifsAndCrl;
import fr.urssaf.image.sae.webservices.util.DateTimeUtils;
import fr.urssaf.image.sae.webservices.util.ResourceUtils;

/**
 * Service qui charge et renvoie les certificats des AC racines de confiance et
 * les CRL<br>
 * les AC racines ne sont chargés qu'une seule fois tandis que les CRL le sont
 * une fois par jour
 * 
 * 
 */
@Service
public class IgcService {

   private static final Logger LOG = Logger.getLogger(IgcService.class);

   private CertifsAndCrl certifsAndCrl;

   private final IgcConfig igcConfig;

   private final List<X509Certificate> certsAcRacine;

   private static final int VALIDATE_TIME = 24;

   private static final String CRL_ERROR = "Une erreur s'est produite lors du chargement des CRL";

   private static final String AC_RACINE_ERROR = "Une erreur s'est produite lors du chargement des certificats des AC racine de confiance";

   public static final String AC_RACINE_EMPTY = "Aucun certificat d'AC racine de confiance trouvé dans le répertoire ${0}";

   /**
    * 
    * Chargement des AC racines<br>
    * Les certificats sont récupérés dans le repertoire indiqué par
    * {@link IgcConfig#getRepertoireACRacines()}<br>
    * Tous les fichier de type *.pem sont chargés en mémoire<br>
    * <br>
    * Une exception {@link IllegalArgumentException} est levée avec le message
    * {@value #AC_RACINE_ERROR} si le chargement des certificats lève une
    * exception ou si l'un des certificats pose problème <br>
    * Si aucun certificat n'est chargé une exception
    * {@link IllegalArgumentException} avec le message {@value #AC_RACINE_EMPTY}
    * est levé où ${0} est le nom du répertoire des AC racine
    * 
    * 
    * @param igcConfig
    *           configuration de l'IGC
    */
   @Autowired
   public IgcService(IgcConfig igcConfig) {

      this.igcConfig = igcConfig;

      FileSystemResource repACRacine = new FileSystemResource(igcConfig
            .getRepertoireACRacines());

      List<Resource> resources = ResourceUtils.loadResources(repACRacine,
            new String[] { "crt" });
      certsAcRacine = new ArrayList<X509Certificate>();

      for (Resource crt : resources) {

         try {

            LOG.debug("loading certificat: " + crt.getFilename());
            InputStream input = crt.getInputStream();

            try {

               certsAcRacine.add(SecurityUtils.loadCertificat(input));

            } catch (CertificateException e) {

               LOG.error("error loading certificat: " + crt.getFilename());
               throw new IllegalArgumentException(AC_RACINE_ERROR, e);
            }

            finally {
               input.close();
            }

         } catch (IOException e) {
            throw new IllegalArgumentException(AC_RACINE_ERROR, e);
         }
      }

      if (CollectionUtils.isEmpty(certsAcRacine)) {
         throw new IllegalArgumentException(TextUtils.getMessage(
               AC_RACINE_EMPTY, repACRacine.getPath()));
      }
   }

   /**
    * La même instance de {@link CertifsAndCrl} est partagé par tous les Threads
    * ayant accès à cette classe<br>
    * Si l'instance n'existe ou que {@link CertifsAndCrl#getDateMajCrl()} date
    * de plus de 24 heures alors une une nouvelle instance de
    * {@link CertifsAndCrl} est mise à disposition des Threads<br>
    * <br>
    * Lors de l'instanciation les CRL sont de nouveau chargés<br>
    * Les CRL sont récupérés dans le répertoire indiqué par
    * {@link IgcConfig#getRepertoireCRLs()}<br>
    * Tous les fichiers de ce répertoire sont ainsi chargés
    * 
    * 
    * @return instance de {@link CertifsAndCrl}
    * @throws LoadCertifsAndCrlException
    *            échec lors du chargement des CRL ou si l'un CRL pose problème
    *            avec le message {@value #CRL_ERROR}
    */
   public final CertifsAndCrl getInstanceCertifsAndCrl()
         throws LoadCertifsAndCrlException {

      synchronized (IgcService.class) {

         createCertifsAndCrl();
      }

      return certifsAndCrl;
   }

   /**
    * l'instance de {@link CertifsAndCrl} est mise à null ce quiveut dire qu'au
    * prochain appel de {@link #getInstanceCertifsAndCrl()} une nouvelle
    * instance de {@link CertifsAndCrl} sera mise à disposition
    * 
    */
   public final void initCertifsAndCrl() {

      synchronized (IgcService.class) {

         this.setCertifsAndCrl(null);

      }
   }

   private void createCertifsAndCrl() throws LoadCertifsAndCrlException {

      DateTime systemDate = new DateTime();

      if (certifsAndCrl == null
            || certifsAndCrl.getDateMajCrl() == null
            || DateTimeUtils.diffHours(certifsAndCrl.getDateMajCrl(),
                  systemDate) > VALIDATE_TIME) {

         CertifsAndCrl instance = new CertifsAndCrl();

         instance.setDateMajCrl(systemDate);
         instance.setCertsAcRacine(certsAcRacine);

         this.setCertifsAndCrl(instance);

         this.loadCRLResources();

      }
   }

   private void setCertifsAndCrl(CertifsAndCrl certifsAndCrl) {

      this.certifsAndCrl = certifsAndCrl;
   }

   private void loadCRLResources() throws LoadCertifsAndCrlException {

      FileSystemResource repCRLs = new FileSystemResource(igcConfig
            .getRepertoireCRLs());
      List<Resource> resources = ResourceUtils.loadResources(repCRLs, null);
      List<X509CRL> crls = new ArrayList<X509CRL>();

      for (Resource crl : resources) {

         try {
            LOG.debug("loading CRL:" + crl.getFilename());
            InputStream input = crl.getInputStream();

            try {
               crls.add(SecurityUtils.loadCRL(input));
            } catch (GeneralSecurityException e) {
               LOG.error("error loading CRL: " + crl.getFilename());
               throw new LoadCertifsAndCrlException(CRL_ERROR, e);
            } finally {

               input.close();
            }
         } catch (IOException e) {

            LOG.error("error loading CRL: " + crl.getFilename());
            throw new LoadCertifsAndCrlException(CRL_ERROR, e);
         }

      }

      certifsAndCrl.setCrl(crls);

   }
}
