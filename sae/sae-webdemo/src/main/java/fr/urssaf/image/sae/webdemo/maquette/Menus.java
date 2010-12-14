package fr.urssaf.image.sae.webdemo.maquette;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.urssaf.image.commons.maquette.definition.IMenu;
import fr.urssaf.image.commons.maquette.exception.MenuException;
import fr.urssaf.image.commons.maquette.tool.MenuItem;

/**
 * Menu de l'IHM maquette<br>
 * La configuration correspond à dans le fichier <code>web.xml</code>
 * 
 * <pre>
 * &lt;init-param>
 *          &lt;param-name>implementationIMenu</param-name>
 *          &lt;param-value>fr.urssaf.image.sae.webdemo.maquette.BoitesGauches</param-value>
 * &lt;/init-param>
 * 
 * </pre>
 * 
 * @see IMenu
 */
public class Menus implements IMenu{

   @Override
   public final String getBreadcrumb(HttpServletRequest request) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public final List<MenuItem> getMenu(HttpServletRequest request)
         throws MenuException {
      // TODO Auto-generated method stub
      return null;
   }

}
