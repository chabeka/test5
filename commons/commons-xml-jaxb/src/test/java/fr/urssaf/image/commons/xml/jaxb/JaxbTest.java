package fr.urssaf.image.commons.xml.jaxb;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.Test;

public class JaxbTest {

   private static final String REPERTORY;

   static {
      REPERTORY = SystemUtils.getJavaIoTmpDir().getAbsolutePath();
   }

   private ObjectFactory factory;

   @Before
   public void before() {
      factory = new ObjectFactory();
   }

   private DocumentType createDocument(int id, String titre, Date date) {
      DocumentType document = factory.createDocumentType();
      document.id = id;
      document.titre = titre;
      // document.date = date;

      return document;
   }

   private AuteurType createAuteur(int id, String nom) {
      AuteurType auteur = factory.createAuteurType();
      auteur.id = id;
      auteur.nom = nom;

      return auteur;
   }

   private EtatType createEtat(int id, String libelle, Date date) {
      EtatType etat = factory.createEtatType();
      etat.id = id;
      etat.libelle = libelle;
      // etat.date = date;

      return etat;
   }

   @Test
   public void marshall() throws JAXBException {

      Documents docs = factory.createDocuments();

      AuteurType auteur0 = this.createAuteur(0, "auteur 0");
      AuteurType auteur1 = this.createAuteur(1, "auteur 1");

      EtatType etat0 = this.createEtat(0, "open", new Date());
      EtatType etat1 = this.createEtat(1, "close", new Date());
      EtatType etat2 = this.createEtat(2, "open", new Date());

      DocumentType document0 = this.createDocument(0, "titre 0", new Date());
      DocumentType document1 = this.createDocument(1, "titre 1", new Date());

      document0.setAuteur(auteur0);
      document1.setAuteur(auteur1);

      document0.getEtat().add(etat0);
      document0.getEtat().add(etat1);
      document1.getEtat().add(etat2);

      docs.getDocument().add(document0);
      docs.getDocument().add(document1);

      JAXBContext context = JAXBContext.newInstance(Documents.class);

      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

      m.marshal(docs, System.out);

      File output = new File(FilenameUtils.concat(REPERTORY, "documents.xml"));

      m.marshal(docs, output);

   }

   @Test
   public void unmarshall() throws JAXBException {

      JAXBContext context = JAXBContext.newInstance(Documents.class);
      Unmarshaller u = context.createUnmarshaller();

      File input = new File("src/test/resources/documents.xml");
      Documents docs = (Documents) u.unmarshal(input);

      assertDocument(docs.document.get(0), 0, "titre 0", 0, "auteur 0");
      assertEtat(docs.document.get(0).getEtat(), 0, 0, "open");
      assertEtat(docs.document.get(0).getEtat(), 1, 1, "close");

      assertDocument(docs.document.get(1), 1, "titre 1", 1, "auteur 1");
      assertEtat(docs.document.get(1).getEtat(), 0, 2, "open");
   }

   private void assertDocument(DocumentType document, int id, String titre,
         int idAuteur, String nomAuteur) {

      assertEquals(new Integer(id), document.id);
   }

   private void assertEtat(List<EtatType> etats, int index, int id,
         String libelle) {

      assertEquals(new Integer(id), etats.get(index).id);
      assertEquals(libelle, etats.get(index).libelle);
   }

}
