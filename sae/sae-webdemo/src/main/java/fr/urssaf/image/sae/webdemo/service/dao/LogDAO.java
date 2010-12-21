package fr.urssaf.image.sae.webdemo.service.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.urssaf.image.sae.webdemo.modele.Log;

/**
 * DAO des traces d'explotation<br>
 * 
 * 
 */
@Repository
public class LogDAO {

   /**
    * Renvoie la liste complètes des traces
    * 
    * @return traces d'exploitation
    */
   public final List<Log> find() {

      List<Log> logs = new ArrayList<Log>();
      logs.add(factory(0, new Date(), 5, "probleme 1", "action 1", "infos 1"));
      logs.add(factory(1, new Date(), 10, "probleme 2", "action 2", "infos 2"));

      return logs;
   }

   private Log factory(long idseq, Date horodatage, int occurences,
         String probleme, String action, String infos) {

      Log log = new Log();
      log.setAction(action);
      log.setHorodatage(horodatage);
      log.setIdseq(idseq);
      log.setInfos(infos);
      log.setOccurences(occurences);
      log.setProbleme(probleme);

      return log;
   }
}
