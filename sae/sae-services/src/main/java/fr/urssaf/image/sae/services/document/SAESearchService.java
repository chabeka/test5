package fr.urssaf.image.sae.services.document;

import java.util.List;

import fr.urssaf.image.sae.exception.SAESearchServiceEx;
import fr.urssaf.image.sae.model.SAELuceneCriteria;
import fr.urssaf.image.sae.model.UntypedDocument;

/**
 * Fournit l’ensemble des services pour la recherche.<BR />
 * 
 * @author rhofir.
 */
public interface SAESearchService {

   /**
    * Service pour l'opération <b>Recherche</b>
    * 
    * @param saeLuceneCriteria
    *           : un objet de type {@linkplain SAELuceneCriteria}
    * @return Une liste de type {@link UntypedDocument}.
    * @throws SAESearchServiceEx
    *            Exception levée lorsque la recherche ne se déroule pas bien.
    */
   List<UntypedDocument> search(SAELuceneCriteria saeLuceneCriteria)
         throws SAESearchServiceEx;

}