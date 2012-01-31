package fr.urssaf.image.commons.spring.batch.support;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

import fr.urssaf.image.commons.spring.batch.support.stax.BibliothequeXMLSupport;

public final class BibliothequeXMLFactory {

   private final BibliothequeXMLSupport support;

   public BibliothequeXMLFactory() {

      support = new BibliothequeXMLSupport();
   }

   public void createBibliothequeXML(int countItems) {

      File libraryFile = new File(SystemUtils.getJavaIoTmpDir(),
            "batch-exemple/bibliotheque_" + countItems + ".xml");

      support.writer(libraryFile, countItems);
   }

   public static void main(String[] args) {

      BibliothequeXMLFactory factory = new BibliothequeXMLFactory();

      factory.createBibliothequeXML(100000);

   }

}
