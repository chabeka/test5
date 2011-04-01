package fr.urssaf.image.commons.xml;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 * Permet de géréer un document XML avec JDom
 */
public class JDOMDocumentUtils {

   /**
    * Le DOM
    */
   private Document dom;
   
   /**
    * Le noeud racine
    */
   private Element racine;
   
   /**
    * Le get du DOM
    * 
    * @return Le DOM
    */
   public final Document getDom() {
      return dom;
   }

   /**
    * Le get de l'Element racine du DOM
    * 
    * @return Element racine
    */
   public final Element getRacine() {
      return racine;
   }

   /**
    * Le seteur de la racine
    * 
    * @param racine L'élément racine à insérer
    */
   public final void setRacine(Element racine) {
      this.racine = racine;
      dom.setRootElement(racine);
   }

   /**
    * Constructeur simple
    */
   public JDOMDocumentUtils(){
      
      dom=new Document();
      
   }
   
   /**
    * Constructeur avec ajout de la racine du noeud
    * 
    * @param nomRacine Nom du node racine
    */
   public JDOMDocumentUtils(String nomRacine) {
      dom=new Document();
      racine= new Element(nomRacine);
      dom.setRootElement(racine);
   }
   
   /**
    * Sauve le dom dans le fichier donné en UTF8
    * 
    * @param cheminFichier Le chemin du fichier
    * 
    * @throws IOException Si problème de gestion de fichier
    */
   public final void sauve(String cheminFichier) throws IOException {
      File file = new File(cheminFichier);
      JDOMUtil.writeToFile(dom, file, "UTF8");
   }
   
   /**
    * Lecture du DOM à partir d'un fichier au format UTF8
    * 
    * @param cheminFichier    Le chemin du fichier
    * 
    * @throws JDOMException   Le fichier xml est mal structuré
    * @throws IOException     Le fichier n'est pas lisible
    * 
    */
   public final void lit(String cheminFichier) throws JDOMException, IOException {
      File file = new File(cheminFichier);
      dom = JDOMUtil.readFromFile(file,"UTF8");
      racine = dom.getRootElement();
   }
   
   
   /**
    * Creation d'un Element avec ses attributs
    *
    * @param pere    Le noeud père
    * @param nom     Le nom de l'élément à créer
    * @param contenu Le contenu textuel de l'élément (peut être null)
    * @param attrib  La liste des attributs (peut être null)
    * 
    * @return L'élement créé
    */
   public final Element createElement( Element pere, String nom, String contenu, Map<String, String> attrib){
      // Crée l'élément
      Element elt = new Element(nom);
      
      // Si le contenu textuel existe l'ajouter
      if ( StringUtils.isNotEmpty(contenu)){
         elt.addContent(contenu);
      }
      
      // Ajoute l'élément au père
      pere.addContent(elt);
      
      // Création des attributs
      if ( attrib != null ) {         
         for ( Map.Entry<String, String> entry : attrib.entrySet() ){
            elt.setAttribute(entry.getKey(),entry.getValue());
         }
      }
      return elt;
   }
}
