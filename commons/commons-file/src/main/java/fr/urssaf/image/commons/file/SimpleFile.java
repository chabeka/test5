package fr.urssaf.image.commons.file;

import java.io.File;
import java.io.IOException;

public class SimpleFile implements ReadWriteFile {

	private File file;

	public SimpleFile(String file) {
		this(new File(file));
	}

	public SimpleFile(File file) {
		this.file = file;
	}

	@Override
	public String read() throws IOException {
		return FileUtil.read(file);
	}

	@Override
	public void write(String text) throws IOException {
		FileUtil.write(text, file);
	}

}
