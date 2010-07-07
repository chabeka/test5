package fr.urssaf.image.commons.file.lock;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.file.ReadWriteFile;
import fr.urssaf.image.commons.file.SimpleFile;

public class FileThread extends Thread {

	private final int numero;

	private static final Logger LOGGER = Logger.getLogger(FileThread.class);

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss", Locale.getDefault());

	private final static String FILE = "src/test/resources/lock.txt";

	private final ReadWriteFile file;

	private static final int THREAD = 20;

	private static final int CYCLE = 50;

	private static int pause = 100;

	private static final boolean LOCK = true;

	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public static void main(String args[]) throws IOException {
		ReadWriteFile file = null;
		if (LOCK) {
			file = new LockFile(FILE);
		} else {
			file = new SimpleFile(FILE);
		}

		for (int i = 0; i < THREAD; i++) {
			FileThread thread = new FileThread(file, i);
			thread.start();
		}
	}

	public FileThread(ReadWriteFile file, int numero) {
		super();
		this.file = file;
		this.numero = numero;
	}

	@Override
	public void run() {
		try {
			this.executer();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public void executer() throws IOException {
		LOGGER.info("DEBUT DE N°" + numero);
		for (int i = 0; i < CYCLE; i++) {

			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(10);
			try {

				Thread.sleep(pause * randomInt);

			} catch (InterruptedException ie) {
			}

			LOGGER.debug("n°" + numero + " attend pour écrire");
			file.write(DATE_FORMAT.format(new Date()) + " thread n°" + numero
					+ " écrit la ligne n°" + i + "\n");
			LOGGER.debug("n°" + numero + " a fini d'écrire");

			randomGenerator = new Random();
			randomInt = randomGenerator.nextInt(10);
			try {

				Thread.sleep(pause * randomInt);

			} catch (InterruptedException ie) {
			}

			LOGGER.debug("n°" + numero + " attend pour lire");
			file.read();
			LOGGER.debug("n°" + numero + " a fini de lire");

		}

		LOGGER.info("FIN DE N°" + numero);

	}

}
