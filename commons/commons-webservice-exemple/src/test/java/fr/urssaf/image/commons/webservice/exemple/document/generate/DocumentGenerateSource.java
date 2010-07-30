package fr.urssaf.image.commons.webservice.exemple.document.generate;

import fr.urssaf.image.commons.webservice.exemple.generate.GenerateCxf;
import fr.urssaf.image.commons.webservice.exemple.generate.GenerateUtil;

public final class DocumentGenerateSource extends GenerateCxf {
	
   private DocumentGenerateSource(){
      super();
   }
   
	public static void main(String[] args){

		GenerateUtil.execute("documentGenerate");

	}
}
