package fr.urssaf.image.commons.webservice.exemple.rpc.encoded.generate;

import fr.urssaf.image.commons.webservice.exemple.generate.GenerateCxf;
import fr.urssaf.image.commons.webservice.exemple.generate.GenerateUtil;

public final class RPCEncodedGenerateSource extends GenerateCxf{
   
   private RPCEncodedGenerateSource(){
      super();
   }

	public static void main(String[] args) {

		GenerateUtil.execute("rpcEncodedGenerate");

	}
}
