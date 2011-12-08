package fr.urssaf.image.sae.webservices.factory;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import fr.urssaf.image.sae.igc.util.TextUtils;
/**
 * 
 * Manager pour le composant technique sae-webservices.
 * 
 *
 */
public class WebServicesManager {
   
   @SuppressWarnings("PMD.LongVariable")
   public static final String SAE_CONFIG_REQUIRED = "Le contenu de la variable JNDI SAE_Fichier_Configuration n’est pas renseigné, alors que c’est obligatoire !";

   @SuppressWarnings("PMD.LongVariable")
   public static final String SAE_CONFIG_NOTEXIST = "Le fichier de configuration générale indiqué par la variable JNDI SAE_Fichier_Configuration est introuvable (${0})";

   
   /**
    * Méthode de mise dans le conteneur de l'objet PropertyPlaceholderConfigurer<br>
    * pour la configuration générale du projet SAE.
    * 
    * @param saeConfigResource
    * @return PropertyPlaceholderConfigurer
    *         objet contenant le chemin complet du fichier de configuration générale
    *         à charger
    */
   public PropertyPlaceholderConfigurer load(FileSystemResource saeConfigResource/*,
                                             FileSystemResource... fileSysRes*/
                                             ) {
      
      // vérifie que la variable JNDI
      // (pointant vers le chemin complet du fichier de configuration générale)
      // est bien définie
      Assert.hasText(saeConfigResource.getPath(), SAE_CONFIG_REQUIRED);

      // vérifie que cette variable JNDI 
      // pointe bien sur un chemin complet de fichier
      Assert.isTrue(saeConfigResource.getFile().isFile(), TextUtils.getMessage(
            SAE_CONFIG_NOTEXIST, saeConfigResource.getPath()));

      Resource[] listSystemRes = new Resource[] {saeConfigResource};
      
//      for(FileSystemResource fileSystemResource : fileSysRes){
//         listSystemRes = (Resource[]) ArrayUtils.add(listSystemRes, fileSystemResource);
//      }
      PropertyPlaceholderConfigurer property = new PropertyPlaceholderConfigurer();
      property.setLocations(listSystemRes);
      return property;
   }
   
}
