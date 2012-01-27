/**
 * 
 */
package fr.urssaf.image.sae.integration.ihmweb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.CalcTempFormulaire;

/**
 * 
 * 
 */
@Controller
@RequestMapping(value = "calcTemp")
public class CalcTempController {

   private SimpleDateFormat SIMPLE_DF = new SimpleDateFormat("dd/MM/yyyy");

   /**
    * Retourne la page par défaut
    * 
    * @return la page à afficher
    */
   @RequestMapping(method = RequestMethod.GET)
   public final String getDefaultView() {
      return "calcTemp";
   }

   /**
    * Renvoie la date par défaut
    * 
    * @return la date
    */
   @ResponseBody
   @RequestMapping(method = RequestMethod.GET, params = "action=getDate")
   public final HashMap<String, Object> getDefaultDate() {

      String currentDate = SIMPLE_DF.format(new Date());

      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("success", true);
      map.put("startDate", currentDate);

      return map;

   }

   /**
    * Renvoie la date calculée
    * 
    * @return la date
    */
   @ResponseBody
   @RequestMapping(method = RequestMethod.POST)
   public final HashMap<String, Object> getDefaultDate(
         CalcTempFormulaire formulaire) {

      HashMap<String, Object> map = new HashMap<String, Object>();
      String message = null;
      boolean succes = false;

      if (formulaire.getStartDate() == null) {
         message = "La date de début est vide";
      } else if (formulaire.getTime() < 1) {
         message = "La durée est vide ou inférieure ou égale à 0";
      } else {

         try {
            Date startDate = SIMPLE_DF.parse(formulaire.getStartDate());

            Date endDate = DateUtils.addDays(startDate, formulaire.getTime());
            String value = SIMPLE_DF.format(endDate);
            map.put("endDate", value);
            succes = true;
         } catch (ParseException e) {
            message = "Date invalide";
         }
      }

      map.put("message", message);
      map.put("success", succes);

      return map;

   }

}
