INSERT INTO acl_sid(id,principal,sid) VALUES (1,true,'1');
INSERT INTO acl_sid(id,principal,sid) VALUES (2,true,'2');
INSERT INTO acl_sid(id,principal,sid) VALUES (3,true,'3');
INSERT INTO acl_sid(id,principal,sid) VALUES (4,true,'4');
INSERT INTO acl_sid(id,principal,sid) VALUES (5,true,'5');
INSERT INTO acl_sid(id,principal,sid) VALUES (6,true,'6');

INSERT INTO acl_class(id,class) VALUES (1,'fr.urssaf.image.commons.springsecurity.acl.model.Publication');
 
INSERT INTO acl_object_identity(id,object_id_class,object_id_identity,parent_object,owner_sid,entries_inheriting) VALUES (1,1,1,null,1,false);
INSERT INTO acl_object_identity(id,object_id_class,object_id_identity,parent_object,owner_sid,entries_inheriting) VALUES (2,1,2,null,2,false);

INSERT INTO acl_entry(id,acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) VALUES (1,1,1,1,2,true,true,true);
INSERT INTO acl_entry(id,acl_object_identity,ace_order,sid,mask,granting,audit_success,audit_failure) VALUES (2,2,1,2,2,true,true,true);
