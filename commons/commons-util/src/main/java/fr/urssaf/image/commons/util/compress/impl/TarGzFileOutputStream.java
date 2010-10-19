package fr.urssaf.image.commons.util.compress.impl;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CheckedOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class TarGzFileOutputStream {

   private final String tgz;

   private final TarOutputStream tarOutputStream;

   public TarGzFileOutputStream(String tgz, String fileName) {
      this.tgz = tgz;
      tarOutputStream = new TarOutputStream(fileName);
   }

   public final void setExtensions(String... extensions) {
      tarOutputStream.setExtensions(extensions);
   }

   public final String compress() throws IOException {

      // création d'un buffer d'écriture
      ByteArrayOutputStream tarDest = new ByteArrayOutputStream();
      try {
         tarOutputStream.compress(tarDest);

         // gzip du fichier
         FileOutputStream dest = new FileOutputStream(tgz);
         try {

            CheckedOutputStream checksum = ArchiveUtil.crc32(dest);
            try {
   
               // création d'un buffer d'écriture
               BufferedOutputStream buff = new BufferedOutputStream(checksum);
               try {
      
                  // création d'un flux d'écriture pour la compression
                  GzipCompressorOutputStream out = new GzipCompressorOutputStream(buff);
                  try {
         
                     // création d'un buffer d'écriture
                     tarDest.writeTo(out);
         
                  } finally {
                     // fermeture du flux d'écriture
                     if (out!=null) {
                        out.close();
                     }
                  }
         
                  return Long.toHexString(checksum.getChecksum().getValue());
                  
               }
               finally {
                  if (buff!=null) {
                     buff.close();
                  }
               }
               
            }
            finally  {
               if (checksum!=null) {
                  checksum.close();
               }
            }
            
         }
         finally {
            if (dest!=null) {
               dest.close();
            }
         }

      } finally {
         // fermeture du flux d'écriture
         if (tarDest!=null) {
            tarDest.close();
         }
      }

   }

}
