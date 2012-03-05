package fr.urssaf.image.sae.lotinstallmaj;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResource;

import fr.urssaf.image.sae.lotinstallmaj.service.MajLotService;


/**
 * Classe Main du JAR Executable 
 *
 */
public final class Main {

   private Main() {
      
   }
   
   /**
    * Méthode mainn du JAR Executable
    * 
    * @param args 
    *           arguments de la ligne de commande du JAR Executable
    */
   public static void main(String []args) {
      
      // démarrage du contexte spring
      ApplicationContext context = startContextSpring(args);
      
      MajLotService majLotService = context.getBean(
                                             "majLotServiceImpl",
                                             MajLotService.class);

      // Affichage dans la console d'une description
      // des oéprations qui seront effectuees
      showConsole(args);
      
      // Demande de confirmation de la console
      demarre(majLotService, args);
      
      
   }
   
   /**
    * Démarage du contexte Spring
    */
   private static ApplicationContext startContextSpring(String []args) {
      
      String filePathConfigSae = args[0]; 
      
      String contextConfig = "/applicationContext-sae-lotinstallmaj.xml";

      GenericApplicationContext genericContext = new GenericApplicationContext();

      BeanDefinitionBuilder appliConfigBean = BeanDefinitionBuilder
            .genericBeanDefinition(FileSystemResource.class);

      appliConfigBean.addConstructorArgValue(filePathConfigSae);

      genericContext.registerBeanDefinition(
            "saeConfigResource", 
            appliConfigBean.getBeanDefinition());

      genericContext.refresh();

      ApplicationContext context = new ClassPathXmlApplicationContext(
               new String[] { contextConfig }, genericContext);
      
      return context;
   }
   
   /**
    * Affichage dans la console des opérations effectuées
    */
   private static void showConsole(String[] args) {

      System.out.println("L'opération réalisée lors de cette mise à jour est :\r\n" +args[1]);
   }
   
   /**
    * Démarrage de la mise à jour
    */
   private static void demarre(MajLotService majLotService, String []args ) {

      majLotService.demarre(args);

   }
}
