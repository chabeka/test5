package fr.urssaf.image.commons.dao.spring.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public final class CriteriaUtil {

	private CriteriaUtil() {
	}

	/**
	 * initialise les critères de pagination
	 * 
	 * @param criteria
	 *            criteria à initialiser
	 * @param firstResult
	 *            index du premier objet
	 * @param maxResult
	 *            nombre de résultats
	 */
	public static void resultats(Criteria criteria,
			int firstResult, int maxResult) {
		criteria.setMaxResults(maxResult);
		criteria.setFirstResult(firstResult);
	}

	/**
	 * initialise les critères de tri
	 * 
	 * @param criteria
	 *            criteria à initialiser
	 * @param order
	 *            colonne à trier
	 * @param inverse
	 *            sens du tri
	 */
	public static void order(Criteria criteria, String order,
			boolean inverse) {

		if (order != null) {
			if (!inverse) {
				criteria.addOrder(Order.asc(order));
			} else {
				criteria.addOrder(Order.desc(order));
			}
		}
	}

}
