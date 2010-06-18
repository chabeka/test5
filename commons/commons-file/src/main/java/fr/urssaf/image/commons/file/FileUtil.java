package fr.urssaf.image.commons.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public final class FileUtil {

	private FileUtil() {

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

		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(file, true), "ISO-8859-1");
		try {
			writer.write(text);
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

		BufferedReader buff = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), encoding));
		StringBuffer text = new StringBuffer();
		try {
			String line;
			while ((line = buff.readLine()) != null) {
				text.append(line + "\n");
			}
			return text.toString();
		} finally {
			buff.close();

		}

	}

}
