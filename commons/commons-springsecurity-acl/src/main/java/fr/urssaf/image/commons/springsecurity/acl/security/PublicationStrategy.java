package fr.urssaf.image.commons.springsecurity.acl.security;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

import fr.urssaf.image.commons.springsecurity.acl.model.Publication;

public class PublicationStrategy implements ObjectIdentityRetrievalStrategy {

   public ObjectIdentity getObjectIdentity(Object domainObject) {

      return new ObjectIdentityImpl(Publication.class, (Integer) domainObject);
   }

}
