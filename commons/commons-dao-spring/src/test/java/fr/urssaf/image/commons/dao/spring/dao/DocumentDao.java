package fr.urssaf.image.commons.dao.spring.dao;

import java.util.List;


import fr.urssaf.image.commons.dao.spring.modele.Document;
import fr.urssaf.image.commons.dao.spring.service.EntityCountDao;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;
import fr.urssaf.image.commons.dao.spring.service.EntityModifyDao;

/**
 * 
 * @author Bertrand BARAULT
 * 
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface DocumentDao extends EntityModifyDao<Document>,
		EntityIdDao<Document, Integer>, EntityFindDao<Document>, EntityCountDao {

	List<Document> findSQL(int firstResult, int maxResults, String order, boolean inverse);

	List<Document> findHQL(int firstResult, int maxResults, String order, boolean inverse);
	
	List<Document> findEtats(int firstResult, int maxResults, String order, boolean inverse);
	
	List<Document> findOrderBy(int firstResult, int maxResults, String table,String order, boolean inverse);
	
	void saveSQL(Document document);

	Document findSQL(Integer idUnique);

	void deleteSQL(Document document);

	void updateSQL(Document document);

	Document findHQL(Integer idUnique);

	List<Document> findByHQL();

	List<Document> findBySQL();

	List<Document> findByCriteria();
	
	List<Document> findByHQLWithEtats();

	List<Document> findByCriteriaWithEtats();
	
	void scroll();

}
