package fr.urssaf.image.commons.file.lock;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.file.FileUtilTest;

public class RandomAccessFileTest {

	private static final Logger LOGGER = Logger.getLogger(FileUtilTest.class);

	private final static String FILE = "src/test/resources/randomAccessFileUtil.txt";

	@Test
	public void write() throws IOException {
		try {
			RandomAccessFileUtil.write("texte à insérer à la fin du fichier&&\n", FILE);
		} catch (IOException e) {
			fail("le test d'écriture est un échec");
		}

	}

	@Test
	public void read() {
		try {
			String text = RandomAccessFileUtil.read(FILE);
			LOGGER.debug(text);
		} catch (IOException e) {
			fail("le test de lecture est un échec");
		}
	}
}
