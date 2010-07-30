package fr.urssaf.image.commons.webservice.generate;

import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class GenerateSourceCxf {

	private final String packagePath;

	private final String wsdl;

	private final MyWSDL2Java myWSDL2Java;

	public GenerateSourceCxf(String packagePath, String wsdl) {

		myWSDL2Java = new MyWSDL2Java();

		this.wsdl = wsdl;
		this.packagePath = packagePath;

	}

	public void generate() {

		ToolContext context = new ToolContext();
		context.setPackageName(this.packagePath);

		myWSDL2Java.setArguments(new String[] { "-d", "src/main/java",
				this.wsdl });

		try {
         myWSDL2Java.run(context);
      } catch (Exception e) {
         throw new IllegalArgumentException(e);
      }

	}

	@SuppressWarnings("PMD.AtLeastOneConstructor")
	private class MyWSDL2Java extends WSDLToJava {
	   
	}
}
