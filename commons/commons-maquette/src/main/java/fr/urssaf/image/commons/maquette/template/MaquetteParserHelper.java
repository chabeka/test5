package fr.urssaf.image.commons.maquette.template;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;

import org.apache.commons.lang.StringEscapeUtils;


/**
 * Fonctions utilitaires pour le parsing des sources HTML
 *
 */
public final class MaquetteParserHelper {

   
   private MaquetteParserHelper() {
      
   }
   
   
   /**
    * Ecrit le contenu d'une balise dans un document HTML 
    * 
    * @param document le document HTML
    * @param balise la balise dont il faut écrire le contenu
    * @param contenu le contenu à écrire
    * @param escapeHtml un flag indiquant s'il faut transformer les caractères
    *        spéciaux en leurs équivalents HTML
    */
   protected static void ecritContenuBalise(
         OutputDocument document,
         final Element balise,
         final String contenu,
         final Boolean escapeHtml) {
      
      int start = balise.getContent().getBegin() ;
      int end = balise.getContent().getEnd() ;
      
      String contenuOk = contenu;
      if (escapeHtml) {
         contenuOk = StringEscapeUtils.escapeHtml(contenuOk);
      }
      
      document.replace(start, end, contenuOk) ;
      
   }
   

   /**
    * Ecrit la valeur d'un attribut d'une balise dans un document HTML
    * 
    * @param document le document HTML
    * @param balise la balise dans laquelle se trouve l'attribut à écrire
    * @param nomAttribut le nom de l'attribut à écrire
    * @param valeurAttribut la valeur de l'attribut
    * @param escapeHtml un flag indiquant s'il faut transformer les caractères
    *        spéciaux en leurs équivalents HTML
    */
   protected static void ecritAttributBalise(
         OutputDocument document,
         final Element balise,
         final String nomAttribut,
         final String valeurAttribut,
         final Boolean escapeHtml) {
      
      Attributes attributes = balise.getAttributes() ;
      
      int start = attributes.get(nomAttribut).getValueSegment().getBegin();
      int end = attributes.get(nomAttribut).getValueSegment().getEnd() ;
      
      String valeurAttributOk = valeurAttribut;
      if (escapeHtml) {
         valeurAttributOk = StringEscapeUtils.escapeHtml(valeurAttributOk);
      }
      
      document.replace(start, end, valeurAttributOk) ;
      
   }
   
}
