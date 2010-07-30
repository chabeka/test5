package fr.urssaf.image.commons.webservice.exemple.rpc.literal.generate;

import fr.urssaf.image.commons.webservice.exemple.generate.GenerateCxf;
import fr.urssaf.image.commons.webservice.exemple.generate.GenerateUtil;

public final class RPCLiteralGenerateSource extends GenerateCxf{
   
   private RPCLiteralGenerateSource(){
      super();
   }

	public static void main(String[] args) {

		GenerateUtil.execute("rpcLiteralGenerate");

	}
}
