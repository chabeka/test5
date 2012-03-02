package fr.urssaf.image.sae.ordonnanceur.support.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.JobInstance;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import fr.urssaf.image.sae.ordonnanceur.exception.OrdonnanceurRuntimeException;
import fr.urssaf.image.sae.ordonnanceur.support.TraitementLauncherSupport;
import fr.urssaf.image.sae.ordonnanceur.util.LauncherUtils;

/**
 * Support pour le lancement d'un traitement de capture en masse
 * 
 * 
 */
public class CaptureMasseLauncherSupportImpl implements
      TraitementLauncherSupport {

   private final String executable;

   private final File saeConfigResource;

   private static final String XMX = "500";

   private static final String XMS = "500";

   /***
    * 
    * @param executable
    *           exécutable du traitement de capture en masse
    * @param saeConfigResource
    *           fichier de configuration générale du SAE
    */
   public CaptureMasseLauncherSupportImpl(String executable,
         Resource saeConfigResource) {

      Assert.notNull(executable, "'executable' is required");
      Assert.notNull(saeConfigResource, "'saeConfigResource' is required");

      this.executable = executable;
      try {
         this.saeConfigResource = saeConfigResource.getFile();
      } catch (IOException e) {

         throw new OrdonnanceurRuntimeException(
               "Erreur lors de la lecture du fichier de configuration du SAE.",
               e);
      }

   }

   @Override
   public final void lancerTraitement(JobInstance captureMasse) {

      String idTraitement = captureMasse.getJobParameters().getString(
            "capture.masse.idTraitement");

      // remplacement de _UUID_TO_REPLACE
      String command = StringUtils.replace(executable, "_UUID_TO_REPLACE",
            idTraitement);

      // remplacement de _XMX_TO_REPLACE
      command = StringUtils.replace(command, "_XMX_TO_REPLACE", XMX);

      // remplacement de _XMS_TO_REPLACE
      command = StringUtils.replace(command, "_XMS_TO_REPLACE", XMS);

      // les trois arguments sont dans l'ordre
      // 1 - le nom du traitement : captureMasse
      // 2 - identifiant du traitement de capture en masse
      // 3 - Le chemin complet du fichier de configuration globale du SAE
      // 4 - UUID du contexte LOGBACK en cours

      LauncherUtils.launch(command, "captureMasse", captureMasse.getId(),
            saeConfigResource.getAbsolutePath(), idTraitement);
   }

}
