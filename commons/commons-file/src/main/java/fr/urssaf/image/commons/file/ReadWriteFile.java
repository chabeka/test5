package fr.urssaf.image.commons.file;

import java.io.IOException;

public interface ReadWriteFile {

	public String read() throws IOException;
	
	public void write(String text) throws IOException;
}
