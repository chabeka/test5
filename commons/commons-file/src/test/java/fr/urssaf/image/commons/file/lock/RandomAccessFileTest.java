package fr.urssaf.image.commons.file.lock;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.urssaf.image.commons.file.FileTest;
import fr.urssaf.image.commons.file.lock.RandomAccessFileUtil;

public class RandomAccessFileTest {

	private static final Logger log = Logger.getLogger(FileTest.class);

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
			log.debug(text);
		} catch (IOException e) {
			fail("le test de lecture est un échec");
		}
	}
}
