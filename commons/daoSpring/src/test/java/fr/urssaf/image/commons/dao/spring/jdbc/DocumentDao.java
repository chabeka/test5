package fr.urssaf.image.commons.dao.spring.jdbc;


import fr.urssaf.image.commons.dao.spring.modele.Document;
import fr.urssaf.image.commons.dao.spring.service.EntityCountDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;
import fr.urssaf.image.commons.dao.spring.service.EntityModifyDao;

public interface DocumentDao extends EntityIdDao<Document, Integer>,
		EntityModifyDao<Document>, EntityCountDao {

}
