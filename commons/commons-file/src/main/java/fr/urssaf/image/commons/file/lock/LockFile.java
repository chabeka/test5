package fr.urssaf.image.commons.file.lock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

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
			String line;
			while ((line = randomAccessFile.readLine()) != null) {
				text.append(line + "\n");
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

	private void init() throws FileNotFoundException {

		this.randomAccessFile = new RandomAccessFile(file, "rw");
		this.fileChannel = randomAccessFile.getChannel();

	}

	private synchronized void release() throws IOException {

		fileLock.release();
		randomAccessFile.close();

		this.notifyAll();
		
	}

	private synchronized void lock(boolean shared) throws IOException {

		while (isLock(shared)) {
			try {
				this.wait();
			} catch (InterruptedException e) {

			}
		}
	}

	private boolean isLock(boolean shared) throws IOException {
		try {
			fileLock = fileChannel.lock(0L, Long.MAX_VALUE, shared);
		} catch (OverlappingFileLockException e) {
			return true;
		} catch (ClosedChannelException e) {
			init();
			fileLock = fileChannel.lock(0L, Long.MAX_VALUE, shared);
		}

		return false;
	}
}
