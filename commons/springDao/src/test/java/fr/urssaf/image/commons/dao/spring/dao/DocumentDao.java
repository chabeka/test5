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
public interface DocumentDao extends EntityModifyDao<Document>,
		EntityIdDao<Document, Integer>, EntityFindDao<Document>, EntityCountDao {

	public List<Document> findSQL(int firstResult, int maxResults, String order, boolean inverse);

	public List<Document> findHQL(int firstResult, int maxResults, String order, boolean inverse);
	
	public List<Document> findEtats(int firstResult, int maxResults, String order, boolean inverse);
	
	public List<Document> findOrderBy(int firstResult, int maxResults, String table,String order, boolean inverse);
	
	public void saveSQL(Document document);

	public Document findSQL(Integer id);

	public void deleteSQL(Document document);

	public void updateSQL(Document document);

	public Document findHQL(Integer id);

	public List<Document> findByHQL();

	public List<Document> findBySQL();

	public List<Document> findByCriteria();
	
	public List<Document> findByHQLWithEtats();

	public List<Document> findByCriteriaWithEtats();
	
	public void scroll();

}
