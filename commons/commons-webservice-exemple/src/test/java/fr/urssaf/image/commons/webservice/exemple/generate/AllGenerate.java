package fr.urssaf.image.commons.webservice.exemple.generate;

import fr.urssaf.image.commons.webservice.exemple.document.generate.GenerateSourceDocument;
import fr.urssaf.image.commons.webservice.exemple.rpc.encoded.generate.GenerateSourceRpcEncoded;
import fr.urssaf.image.commons.webservice.exemple.rpc.literal.generate.GenerateSourceRpcLiteral;

public class AllGenerate {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		GenerateSourceDocument.generation();
		GenerateSourceRpcEncoded.generation();
		GenerateSourceRpcLiteral.generation();

	}

}
