package fr.urssaf.image.commons.file.lock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import fr.urssaf.image.commons.file.ReadWriteFile;

public class LockFile implements ReadWriteFile {

	private RandomAccessFile randomAccessFile;

	private FileChannel fileChannel;
	private FileLock fileLock;
	private File file;

	private static final Logger LOGGER = Logger.getLogger(LockFile.class);

	public LockFile(String file) throws IOException {
		this(new File(file));
	}

	public LockFile(File file) throws IOException {
		this.file = file;
		init();
	}

	@Override
	public String read() throws IOException {

		lock(true);

		LOGGER.trace("lecture de " + file.getName());

		StringBuffer text = new StringBuffer();
		try {
			String line = randomAccessFile.readLine();
			while (line != null) {
				text.append(line);
				text.append(StringEscapeUtils.escapeJava("n"));
				line = randomAccessFile.readLine();
			}
			return text.toString();
		} finally {

			release();
			LOGGER.trace("fin de lecture de " + file.getName());

		}

	}

	@Override
	public void write(String text) throws IOException {

		lock(false);

		LOGGER.trace("écriture sur " + file.getName());

		try {
			randomAccessFile.seek(randomAccessFile.length());
			randomAccessFile.write(text.getBytes());

		} finally {

			release();
			LOGGER.trace("fin d'écriture de " + file.getName());

		}

	}

	/**
	 * méthode pour initialiser le randomAccessFile en rw et le fileChannel
	 * 
	 * @throws FileNotFoundException
	 */
	private void init() throws FileNotFoundException {

		this.randomAccessFile = new RandomAccessFile(file, "rw");
		this.fileChannel = randomAccessFile.getChannel();

	}

	/**
	 * libération du verrou et fermeture du fichier
	 * 
	 * @throws IOException
	 */
	private void release() throws IOException {

		synchronized (this) {
			fileLock.release();
			randomAccessFile.close();

			this.notifyAll();
		}

	}

	/**
	 * instruction d'attente pour l'obtention du fichier
	 * 
	 * @param shared
	 * @throws IOException
	 */
	private void lock(boolean shared) throws IOException {
		synchronized (this) {
			while (isLock(shared)) {
				try {
					this.wait();
				} catch (InterruptedException e) {

				}
			}
		}
	}

	/**
	 * Condition pour obtenir le verrou sur le fichier
	 * 
	 * @param shared
	 *            partagé en mode lecture et exclusif en mode écriture
	 * @return
	 * @throws IOException
	 */
	private boolean isLock(boolean shared) throws IOException {

		boolean lock = false;

		try {
			fileLock = fileChannel.lock(0L, Long.MAX_VALUE, shared);
		} catch (OverlappingFileLockException e) {
			lock = true;
		} catch (ClosedChannelException e) {
			init();
			fileLock = fileChannel.lock(0L, Long.MAX_VALUE, shared);
		}

		return lock;
	}
}
