package fr.urssaf.image.commons.file;

import java.io.IOException;

public interface ReadWriteFile {

	String read() throws IOException;
	
	void write(String text) throws IOException;
}
