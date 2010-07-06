package fr.urssaf.image.commons.webservice.generate;

import org.apache.axis2.wsdl.WSDL2Java;

public class GenerateSourceAxis2 {

	private final String packagePath;

	private final String wsdl;

	public GenerateSourceAxis2(String packagePath, String wsdl) {
		
		this.wsdl = wsdl;
		this.packagePath = packagePath;

	}

	@SuppressWarnings("PMD.SignatureDeclareThrowsException")
	public void generate() throws Exception
	{

		String[] args = new String[] {this.wsdl,"-p" + this.packagePath,
				"-osrc/main/java"};

		WSDL2Java.main(args);

	}

}
