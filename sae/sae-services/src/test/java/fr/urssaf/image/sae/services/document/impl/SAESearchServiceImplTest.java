package fr.urssaf.image.sae.services.document.impl;

import junit.framework.Assert;

import org.junit.Test;

import fr.urssaf.image.sae.exception.SAESearchServiceEx;
import fr.urssaf.image.sae.model.SAELuceneCriteria;
import fr.urssaf.image.sae.services.impl.CommonsServices;

public class SAESearchServiceImplTest extends CommonsServices {
   /**
    * Test du service :
    * {@link fr.urssaf.image.sae.services.document.SAESearchService#search(SAELuceneCriteria)
    * search}
    */
   @Test
   public final void search() throws SAESearchServiceEx {
      SAELuceneCriteria sAELuceneCriteria = new SAELuceneCriteria();
      final String lucene = String.format("%s:%s", "_uuid", "999999");
      sAELuceneCriteria.setLimit(10);
      sAELuceneCriteria.setLuceneQuery(lucene);
      Assert.assertNotNull(getSaeServiceProvider().getSaeDocumentService()
            .search(sAELuceneCriteria));
   }

}
