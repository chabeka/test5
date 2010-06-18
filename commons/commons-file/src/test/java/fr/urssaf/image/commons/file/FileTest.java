package fr.urssaf.image.commons.file;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileTest {

	private static final Logger log = Logger.getLogger(FileTest.class);

	private final static String FILE = "src/test/resources/test.txt";

	@Test
	public void write() throws IOException {
		try {
			FileUtil.write("texte à insérer à la fin du fichier&&\n", FILE);
		} catch (IOException e) {
			fail("le test d'écriture est un échec");
		}

	}

	@Test
	public void read() {
		try {
			String text = FileUtil.read(FILE);
			log.debug(text);
		} catch (IOException e) {
			fail("le test de lecture est un échec");
		}

	}
}
