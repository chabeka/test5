package fr.urssaf.image.commons.file.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

public final class XMLUtil {

	private XMLUtil(){
		
	}
	
	public static void write(Document doc, String fichier) throws IOException{
		OutputFormat outf = new OutputFormat();
		outf.setIndent(0);
		outf.setIndenting(true);
		outf.setEncoding("UTF-8");
		
		FileOutputStream fileOutputStream = new FileOutputStream(new File(fichier));
		XMLSerializer xmls = new XMLSerializer(fileOutputStream, outf);
		xmls.serialize(doc);
		fileOutputStream.close();
		
	}
}
