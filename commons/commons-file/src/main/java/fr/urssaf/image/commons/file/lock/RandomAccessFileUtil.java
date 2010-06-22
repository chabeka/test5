package fr.urssaf.image.commons.file.lock;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public final class RandomAccessFileUtil {

	private RandomAccessFileUtil() {

	}

	public static void write(String text, String file) throws IOException {

		write(text, new File(file));
	}

	public static void write(String text, String file, String encoding)
			throws IOException {

		write(text, new File(file), encoding);
	}

	public static void write(String text, File file) throws IOException {

		write(text, file, Charset.defaultCharset().name());

	}

	public static void write(String text, File file, String encoding)
			throws IOException {

		RandomAccessFile writer = new RandomAccessFile(
				file, "rw");
		try {
			writer.seek(file.length());
			writer.write(text.getBytes(encoding));
		} finally {
			writer.close();
		}

	}

	public static String read(String file) throws IOException {
		return read(new File(file));
	}

	public static String read(String file, String encoding) throws IOException {
		return read(new File(file), encoding);
	}

	public static String read(File file) throws IOException {
		return read(file, Charset.defaultCharset().name());
	}

	public static String read(File file, String encoding) throws IOException {

		RandomAccessFile reader = new RandomAccessFile(
				file, "r");
		
		StringBuffer text = new StringBuffer();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				text.append(line + "\n");
			}
			return text.toString();
		} finally {
			reader.close();

		}

	}

}
