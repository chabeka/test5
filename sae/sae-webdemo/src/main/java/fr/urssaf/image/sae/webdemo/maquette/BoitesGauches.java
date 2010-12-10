package fr.urssaf.image.sae.webdemo.maquette;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.urssaf.image.commons.maquette.definition.ILeftCol;
import fr.urssaf.image.commons.maquette.tool.InfoBoxItem;

public class BoitesGauches implements ILeftCol{

   @Override
   public final List<InfoBoxItem> getInfoBox(HttpServletRequest request) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public final String getLienDeconnexion(HttpServletRequest request) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public final String getNomApplication(HttpServletRequest request) {
      return "SAE - Application web de démonstration";
   }

   @Override
   public final String getNomUtilisateur(HttpServletRequest request) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public final String getRoleUtilisateur(HttpServletRequest request) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public final String getVersionApplication(HttpServletRequest request) {
      return "0.1";
   }

}
