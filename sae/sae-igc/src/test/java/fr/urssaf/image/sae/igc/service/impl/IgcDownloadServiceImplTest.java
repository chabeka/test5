package fr.urssaf.image.sae.igc.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.urssaf.image.sae.igc.exception.IgcDownloadException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;

@SuppressWarnings( { "PMD.MethodNamingConventions" })
@Ignore
public class IgcDownloadServiceImplTest {

   private IgcDownloadServiceImpl service;

   @Before
   public void before() {

      this.service = new IgcDownloadServiceImpl();
   }

   @Test
   public void telechargeCRLs_success() throws IgcDownloadException {

      IgcConfig igcConfig = new IgcConfig();
      Integer crlsNumber = service.telechargeCRLs(igcConfig);

      assertEquals("erreur sur le nombre d'urls à télécharger", Integer
            .valueOf(5), crlsNumber);

   }

   @Test(expected = IgcDownloadException.class)
   public void telechargeCRLs_failure() throws IgcDownloadException {

      IgcConfig igcConfig = new IgcConfig();
      service.telechargeCRLs(igcConfig);

   }
}
