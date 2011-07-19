INSERT INTO acl_sid(id,principal,sid) VALUES (1,false,'ROLE_READER');

INSERT INTO acl_class(id,class) VALUES (1,'fr.urssaf.image.commons.springsecurity.acl.model.Publication');
 
INSERT INTO acl_object_identity(id,object_id_class,object_id_identity,parent_object,owner_sid,entries_inheriting) VALUES (1,1,1,null,1,false);

INSERT INTO acl_entry(id,acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) VALUES (1,1,1,1,1,true,false,false);