package fr.urssaf.image.commons.springsecurity.acl.dao;

import java.util.List;

import fr.urssaf.image.commons.springsecurity.acl.model.Publication;

public interface PublicationDAO {

   @SuppressWarnings("PMD.ShortVariable")
   Publication find(int id);
   
   @SuppressWarnings("PMD.ShortVariable")
   Publication findById(int id);

   List<Publication> find();

   List<Publication> findByAuthor(int idAuthor);

   void save(Publication publication);
   
   void update(Publication publication);

}
