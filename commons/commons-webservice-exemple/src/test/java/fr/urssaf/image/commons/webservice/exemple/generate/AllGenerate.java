package fr.urssaf.image.commons.webservice.exemple.generate;

import fr.urssaf.image.commons.webservice.exemple.document.generate.DocumentGenerateSource;
import fr.urssaf.image.commons.webservice.exemple.rpc.encoded.generate.RPCEncodedGenerateSource;
import fr.urssaf.image.commons.webservice.exemple.rpc.literal.generate.RPCLiteralGenerateSource;

public final class AllGenerate {
   
   private AllGenerate(){
      
   }
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
	
		DocumentGenerateSource.main(args);
		RPCEncodedGenerateSource.main(args);
		RPCLiteralGenerateSource.main(args);

	}

}
