package fr.urssaf.image.commons.springsecurity.acl.security;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.stereotype.Component;

import fr.urssaf.image.commons.springsecurity.acl.model.Person;
import fr.urssaf.image.commons.springsecurity.acl.model.Publication;

@Component
public class PublicationStrategy implements ObjectIdentityRetrievalStrategy {

   public ObjectIdentity getObjectIdentity(Object domainObject) {

      ObjectIdentity identity;

      if (domainObject instanceof Person) {
         identity = new ObjectIdentityImpl(Publication.class,
               ((Person) domainObject).getId());
      } else if (domainObject instanceof Publication) {
         identity = new ObjectIdentityImpl(Publication.class,
               ((Publication) domainObject).getAuthor().getId());
      } else {
         throw new IllegalArgumentException(domainObject
               + " n'est pas prise en compte dans publication ");
      }

      return identity;

   }

}
