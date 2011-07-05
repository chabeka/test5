package fr.urssaf.image.sae.ecde.service.impl;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;


/**
 * 
 * Classe EcdeFileServiceImpl qui implémente EcdeFileService
 * 
 * Classe d'implémentation du service EcdeFileService.
 * Cette classe est un singleton et peut être accessible par le mécanisme d'injection IOC
 * avec l'annotation @Autowired
 * 
 *
 */


@Service
public class EcdeFileServiceImpl implements EcdeFileService {

   
   @Override
   public final URI convertFileToURI(File ecdeFile, List<EcdeSource> sources) throws EcdeBadFileException{
      // TODO 
      return null;
   }

   @Override
   public final File convertURIToFile(URI ecdeURL, List<EcdeSource> sources) throws EcdeBadURLException, EcdeBadURLFormatException{
      // TODO 
      return null;
   }

}
