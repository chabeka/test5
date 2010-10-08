package fr.urssaf.image.commons.maquette.theme;

import java.io.Serializable;

import javax.servlet.FilterConfig;

/**
 * Représente un thème graphique de la maquette.<br>
 * <br>
 * Classe abstraite que chaque thème doit dériver.
 *
 */
public abstract class AbstractMaquetteTheme implements Serializable {
   
   
   private static final long serialVersionUID = 3801358133753378008L;
   
   
   private final FilterConfig filterConfig;
   
   
   /**
    * Le chemin de l'image du logo principal (positionnée en haut à gauche).<br>
    * <br>
    * Typiquement le logo du pôle Image avec les bonnes couleurs de fond
    * et de transparence<br>
    * <br>
    * Correspond au paramètre "logoPrincipal" du web.xml<br>
    * 
    * @return Le chemin de l'image du logo principal
    * 
    */
   public abstract String getMainLogo() ;

   
   /**
    * Le chemin de l'image du logo de l'application (positionnée en haut à droite).<br>
    * <br>
    * Correspond au paramètre "appLogo" du web.xml<br>
    * <br>
    * Exemple : "Rhododendron.bmp" où le fichier est dans /WEB_INF/Rhododendron.bmp
    * 
    * @return Le chemin de l'image du logo de l'application (positionnée en haut à droite).
    * 
    */
   public abstract String getAppLogo();
   


   /**
    * CSS de la couleur de fond de la fenêtre<br>
    * <br>
    * Correspond au paramètre "cssMainBackgroundColor" du web.xml<br>
    * <br>
    * Exemple : "#FF0000"
    * 
    * @return CSS de la couleur de fond de la fenêtre
    * 
    */
   public abstract String getCssMainBackgroundColor();


   /**
    * CSS de la couleur de fond de la zone où s'affiche le contenu 
    * applicatif (zone centrale et menu de gauche)<br>
    * <br>
    * Correspond au paramètre "cssContentBackgroundColor" du web.xml<br>
    * Exemple : "lightblue"
    * 
    * @return CSS de la couleur de fond de la zone où s'affiche le contenu 
    * applicatif (zone centrale et menu de gauche)
    * 
    */
   public abstract String getCssContentBackgroundColor();


   /**
    * CSS de la couleur de fond de la zone entourant le contenu 
    * applicatif<br>
    * <br>
    * Correspond au paramètre "cssHeaderBackgroundColor" du web.xml<br>
    * <br>
    * Exemple : "cyan"
    * 
    * @return CSS de la couleur de fond de la zone entourant le contenu 
    * applicatif
    * 
    */
   public abstract String getCssHeaderBackgroundColor();


   /**
    * CSS de l'image du dégradé du header (1920*1px)<br>
    * <br>
    * Correspond au paramètre "cssHeaderBackgroundImg" du web.xml<br>
    * <br>
    * Exemple : img/degrade_haut.png
    * 
    * @return CSS de l'image du dégradé du header (1920*1px)
    * 
    */
   public abstract String getCssHeaderBackgroundImg();


   /**
    * CSS de l'image de la même couleur que le {@link #cssHeaderBackgroundColor} 
    * pour simuler la colonne de gauche (140*1px)<br>
    * <br>
    * Correspond au paramètre "cssLeftcolBackgroundImg" du web.xml<br>
    * <br>
    * Exemple : "img/degrade_gauche.png"
    * 
    * @return CSS de l'image de la même couleur que le {@link #cssHeaderBackgroundColor} 
    * pour simuler la colonne de gauche (140*1px)
    * 
    */
   public abstract String getCssLeftcolBackgroundImg();


   /**
    * CSS de la couleur des polices pour les textes en dehors de la 
    * zone de contenu (appTitle, appCopyright, titres des boîtes de gauche)<br>
    * <br>
    * Correspond au paramètre "cssMainFontColor" du web.xml<br>
    * <br>
    * Exemple : "purple"
    * 
    * @return CSS de la couleur des polices pour les textes en dehors de la 
    * zone de contenu (appTitle, appCopyright, titres des boîtes de gauche)
    * 
    */
   public abstract String getCssMainFontColor() ;


   /**
    * CSS de la couleur des polices pour les textes de la zone de contenu<br>
    * <br>
    * Correspond au paramètre "cssContentFontColor" du web.xml<br>
    * <br>
    * Exemple : "gray"
    * 
    * @return CSS de la couleur des polices pour les textes de la zone de contenu
    * 
    */
   public abstract String getCssContentFontColor() ;


   /**
    * CSS de la couleur de fond des boîtes contextuelles de la colonne de gauche<br>
    * <br>
    * Correspond au paramètre "cssInfoboxBackgroundColor" du web.xml<br>
    * <br>
    * Exemple : "magenta"
    * 
    * @return CSS de la couleur de fond des boîtes contextuelles de la colonne de gauche
    * 
    */
   public abstract String getCssInfoboxBackgroundColor() ;


   /**
    * CSS de la couleur de fond du menu sélectionné, fil d'Ariane et 
    * titre des boîtes contextuelles<br>
    * <br>
    * Correspond au paramètre "cssSelectedMenuBackgroundColor" du web.xml<br>
    * <br>
    * Exemple : "yellow"
    * 
    * @return CSS de la couleur de fond du menu sélectionné, fil d'Ariane et 
    * titre des boîtes contextuelles
    * 
    */
   public abstract String getCssSelectedMenuBackgroundColor();


   /**
    * CSS de la couleur des polices pour les liens de la première ligne du menu<br>
    * <br>
    * Correspond au paramètre "cssMenuFirstRowFontColor" du web.xml<br>
    * <br>
    * Exemple : "black"
    * 
    * @return CSS de la couleur des polices pour les liens de la première ligne du menu
    * 
    */
   public abstract String getCssMenuFirstRowFontColor();


   /**
    * CSS de la couleur des polices pour les liens du menu<br>
    * <br>
    * Correspond au paramètre "cssMenuLinkFontColor" du web.xml<br>
    * <br>
    * Exemple : "green"
    * 
    * @return CSS de la couleur des polices pour les liens du menu
    * 
    */
   public abstract String getCssMenuLinkFontColor();


   /**
    * CSS de la couleur des polices pour les liens survolés du menu<br>
    * <br>
    * Correspond au paramètre "cssMenuLinkHoverFontColor" du web.xml<br>
    * <br>
    * Exemple : "yellow"
    * 
    *  @return CSS de la couleur des polices pour les liens survolés du menu
    *  
    */
   public abstract String getCssMenuLinkHoverFontColor();
   
   
   
   /**
    * Constructeur
    * 
    * @param filterConfig la configuration du filtre
    */
   public AbstractMaquetteTheme(FilterConfig filterConfig) {
      this.filterConfig = filterConfig;
   }
   
   
   /**
    * Renvoie une valeur de la configuration du filtre
    * 
    * @param name le nom de la valeur à renvoyé
    * @return la valeur (chaîne vide ou renseignée, jamais de null)
    */
   @SuppressWarnings("PMD.ConfusingTernary")
   protected final String getFilterValue(String name) {
      
      String valeur ;
      
      if (name==null) {
         valeur = "";
      }
      else if (filterConfig!=null) {
         valeur = filterConfig.getInitParameter(name);
      }
      else {
         valeur = "";
      }
      
      if (valeur==null) {
         valeur = "";
      }
      else {
         valeur = valeur.trim();
      }
      
      return valeur;
      
   }
   
}
