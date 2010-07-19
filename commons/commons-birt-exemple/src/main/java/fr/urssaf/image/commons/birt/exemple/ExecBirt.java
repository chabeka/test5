package fr.urssaf.image.commons.birt.exemple;


import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;

import fr.urssaf.image.commons.birt.BirtEngineFactory;
import fr.urssaf.image.commons.birt.BirtRender;
import fr.urssaf.image.commons.birt.exception.MissingConstructorParamBirtException;
import fr.urssaf.image.commons.birt.exception.MissingKeyInHashMapBirtEngineFactoryException;
import fr.urssaf.image.commons.birt.exception.NoEngineBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NoInstanceBirtEngineException;
import fr.urssaf.image.commons.birt.exception.NullFactoryBirtEngineException;

/**
 * Servlet implementation class ExecBirt
 */
public class ExecBirt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final Logger logger = Logger.getLogger( ExecBirt.class.getName() );
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecBirt() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	   // Préparation des paramètres à envoyer au moteur de rendu pour la génération du rapport
		HashMap<Object, Object> paramValues = new HashMap<Object, Object>() ;
		paramValues.put("monParametreTitreDePage", "Titre de ma page");
		paramValues.put("CustomerNumberParam", 200);
		
		try {
		   // Démarrage du serveur Birt
		   HashMap<String, Object> engineFactoryParams = new HashMap<String,Object>();
		   engineFactoryParams.put(BirtEngineFactory.key.SERVLET_CONTEXT , this.getServletContext()) ;
		   engineFactoryParams.put(BirtEngineFactory.key.LOG_PATH, this.getServletContext().getRealPath("./logs"));
		   BirtEngineFactory.getBirtEngineInstance( BirtEngineFactory.WEB_APP, engineFactoryParams ) ;
		   
			logger.info("Démarrage du moteur de rendu");
			BirtRender renderer = new BirtRender(
			      this.getServletContext().getRealPath("./outputpath/"), 
			      "output-birt" );
			logger.info("Moteur de rendu démarré");
			
			logger.info("Démarrage traitement de rendu");
			renderer.doRender(
			      this.getServletContext().getRealPath("./reports/monPremierRapport.rptdesign"),
			      BirtRender._MODE_PDF_, 
			      paramValues) ;
			logger.info("Le traitement de rendu est terminé, le fichier doit être disponible.\n");
			
		} catch (MissingConstructorParamBirtException e) {
			logger.fatal( e );
			e.printStackTrace();
		} catch (BirtException e) {
		   logger.info("Le serveur Birt n'a pas pu démarrer");
			logger.fatal( e );
			e.printStackTrace();
		} catch (NoInstanceBirtEngineException e) {
		   logger.info("Le moteur de rendu n'a pas pu démarrer car aucune instance du BirtEngine n'existe");
         logger.fatal( e );
         e.printStackTrace();
      } catch (NoEngineBirtEngineException e) {
         logger.info("Le serveur Birt n'est pas démarré");
         logger.fatal( e );
         e.printStackTrace();
      } catch (NullFactoryBirtEngineException e) {
         logger.info("Le serveur Birt n'a pas pu démarrer");
         logger.fatal( e );
         e.printStackTrace();
      } catch (MissingKeyInHashMapBirtEngineFactoryException e) {
         logger.info("Erreur d'utilisation de la factory BirtEngine");
         logger.fatal( e );
         e.printStackTrace();
      }
		
	}

}
