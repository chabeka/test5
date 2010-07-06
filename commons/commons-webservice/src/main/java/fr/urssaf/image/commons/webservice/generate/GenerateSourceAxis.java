package fr.urssaf.image.commons.webservice.generate;

import java.util.List;

import org.apache.axis.utils.CLArgsParser;
import org.apache.axis.utils.CLOption;
import org.apache.axis.utils.Messages;
import org.apache.axis.wsdl.WSDL2Java;
import org.apache.log4j.Logger;

public class GenerateSourceAxis {

	protected static final Logger LOGGER = Logger
			.getLogger(GenerateSourceAxis.class);

	private final String packagePath;

	private final String wsdl;

	private final MyWSDL2Java myWSDL2Java;

	public GenerateSourceAxis(String packagePath, String wsdl) {

		myWSDL2Java = new MyWSDL2Java();

		this.wsdl = wsdl;
		this.packagePath = packagePath;

	}

	public void generate() {

		String[] args = new String[] { "-p" + this.packagePath,
				"-osrc/main/java", this.wsdl };

		myWSDL2Java.run(args);

	}

	private class MyWSDL2Java extends WSDL2Java {

		@SuppressWarnings("unchecked")
		@Override
		protected void run(String[] args) {

			// Parse the arguments
			CLArgsParser argsParser = new CLArgsParser(args, options);

			// Print parser errors, if any
			if (null != argsParser.getErrorString()) {

				LOGGER.error(Messages.getMessage("error01", argsParser
						.getErrorString()));
				printUsage();
			}

			// Get a list of parsed options
			List clOptions = argsParser.getArguments();
			int size = clOptions.size();

			try {

				// Parse the options and configure the emitter as appropriate.
				for (int i = 0; i < size; i++) {
					parseOption((CLOption) clOptions.get(i));
				}

				// validate argument combinations
				// 
				validateOptions();
				parser.run(wsdlURI);

			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
