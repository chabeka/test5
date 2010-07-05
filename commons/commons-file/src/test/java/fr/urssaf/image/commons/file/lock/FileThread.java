package fr.urssaf.image.commons.file.lock;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.file.ReadWriteFile;
import fr.urssaf.image.commons.file.SimpleFile;
import fr.urssaf.image.commons.file.lock.LockFile;
import fr.urssaf.image.commons.util.date.DateUtil;

public class FileThread extends Thread {

	private int numero;

	private static final Logger LOGGER = Logger.getLogger(FileThread.class);

	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss", Locale.getDefault());

	private final static String FILE = "src/test/resources/lock.txt";

	private ReadWriteFile file;

	private static final int THREAD = 10;

	private static final int CYCLE = 5;

	private final int pause = 100;

	private static final boolean LOCK = true;

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
		this.file = file;
		this.numero = numero;
	}

	@Override
	public void run() {
		try {
			this.executer();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

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
			file.write(now() + " thread n°" + numero + " écrit la ligne n°" + i
					+ "\n");
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

	private static String now() {

		return DateUtil.date(new Date(), DATE_FORMAT);
	}
}
