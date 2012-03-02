package fr.urssaf.image.sae.ordonnanceur.support;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSources;

/**
 * Support pour les traitements de capture en masse
 * 
 * 
 */
@Component
public class CaptureMasseSupport {

   private static final String CAPTURE_MASSE_ECDE = "capture.masse.sommaire";

   public static final String CAPTURE_MASSE_JN = "capture_masse";

   private final EcdeSources ecdeSources;

   /**
    * 
    * @param ecdeSources
    *           liste des {@link EcdeSource} configurées pour l'ordonnanceur
    */
   @Autowired
   public CaptureMasseSupport(EcdeSources ecdeSources) {

      this.ecdeSources = ecdeSources;
   }

   /**
    * Filtre les instances des traitements de masse pour ne récupérer que ceux
    * concernant les capture en masse pour l'ECDE local.<br>
    * <br>
    * Un traitement de capture en masse indique dans son {@link org.springframework.batch.core.JobParameter} '
    * {@value #CAPTURE_MASSE_ECDE}' l'URL ECDE du fichier sommaire.xml.<br>
    * A chaque URL ECDE correspond une configuration de {@link EcdeSource} pour
    * l'ordonnanceur.<br>
    * <br>
    * La méthode {@link EcdeSource#isLocal()} indique si l'URL ECDE est local
    * pour le CNP courant. <br>
    * <ul>
    * <li>{@link EcdeSource#isLocal()} renvoie <code>true</code> : le traitement
    * concerne l'ECDE local</li>
    * <li>{@link EcdeSource#isLocal()} renvoie <code>false</code> : le
    * traitement ne concerne pas l'ECDE local</li>
    * <li>si aucune instance de {@link EcdeSource} n'est trouvée : le traitement
    * ne concerne pas l'ECDE local</li>
    * </ul>
    * 
    * 
    * @param jobInstances
    *           traitements de masse
    * @return traitements de capture en masse filtrés
    */
   public final List<JobInstance> filtrerJobInstanceByECDELocal(
         List<JobInstance> jobInstances) {

      @SuppressWarnings("unchecked")
      List<JobInstance> jobCaptureMasse = (List<JobInstance>) CollectionUtils
            .select(jobInstances, new Predicate() {

               @Override
               public boolean evaluate(Object object) {

                  JobInstance jobInstance = (JobInstance) object;

                  return evaluateCaptureMasse(jobInstance);
               }

            });

      return jobCaptureMasse;
   }

   /**
    * Filtre les exécutions de traitements de masse pour ne récupérer que ceux
    * concernant les capture en masse pour l'ECDE local.<br>
    * <br>
    * Le filtrage est identique à celui de
    * {@link #filtrerJobInstanceByECDELocal(List)}
    * 
    * 
    * @param jobExecutions
    *           traitements de masse
    * @return traitements de capture en masse filtrés
    */
   public final List<JobExecution> filtrerJobExecutionByECDELocal(
         Collection<JobExecution> jobExecutions) {

      @SuppressWarnings("unchecked")
      List<JobExecution> jobCaptureMasse = (List<JobExecution>) CollectionUtils
            .select(jobExecutions, new Predicate() {

               @Override
               public boolean evaluate(Object object) {

                  JobInstance jobInstance = ((JobExecution) object)
                        .getJobInstance();

                  return evaluateCaptureMasse(jobInstance);
               }

            });

      return jobCaptureMasse;
   }

   private boolean evaluateCaptureMasse(JobInstance jobInstance) {

      boolean isLocal = evaluateLocal(jobInstance);

      boolean isCaptureMasse = CAPTURE_MASSE_JN
            .equals(jobInstance.getJobName());

      return isLocal && isCaptureMasse;
   }

   private boolean evaluateLocal(JobInstance jobInstance) {

      boolean isLocal = false;

      String ecdeParameter = jobInstance.getJobParameters().getString(
            CAPTURE_MASSE_ECDE);

      if (ecdeParameter != null) {

         try {
            URI ecdeURL = URI.create(ecdeParameter);

            EcdeSource ecdeSource = loadEcdeSource(ecdeURL);

            if (ecdeSource != null) {
               isLocal = ecdeSource.isLocal();
            }

         } catch (IllegalArgumentException e) {

            // cas où ecdeParameter ne respecte pas RFC 2396
            // (Cf. http://www.ietf.org/rfc/rfc2396.txt)

            isLocal = false;

         }

      }

      return isLocal;
   }

   private EcdeSource loadEcdeSource(URI ecdeURL) {

      EcdeSource source = null;

      for (EcdeSource ecdeSource : ecdeSources.getSources()) {

         if (StringUtils.equals(ecdeURL.getAuthority(), ecdeSource.getHost())) {

            source = ecdeSource;
            break;

         }
      }

      return source;
   }

}
