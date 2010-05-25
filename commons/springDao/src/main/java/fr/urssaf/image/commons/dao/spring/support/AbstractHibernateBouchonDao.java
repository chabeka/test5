package fr.urssaf.image.commons.dao.spring.support;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.service.EntityCountDao;
import fr.urssaf.image.commons.dao.spring.service.EntityFindDao;
import fr.urssaf.image.commons.dao.spring.service.EntityIdDao;
import fr.urssaf.image.commons.dao.spring.service.EntityModifyDao;

@Transactional(readOnly = true)
public abstract class AbstractHibernateBouchonDao<T, I extends Serializable>
		implements EntityModifyDao<T>, EntityIdDao<T, I>, EntityFindDao<T>,
		EntityCountDao {

	private Map<I, T> data = new HashMap<I, T>();

	@Override
	public T find(I id) {
		return data.get(id);
	}

	@Override
	public T get(I id) {
		return data.get(id);
	}

	@Override
	public int count() {
		return data.size();
	}

	@Override
	public List<T> find(int firstResult, int maxResult, String order,
			boolean inverse) {
		return this.find();
	}

	@Override
	public void delete(T obj) {
		data.remove(getId(obj));

	}

	@Override
	public void save(T obj) {
		data.put(getId(obj), obj);

	}

	@Override
	public void update(T obj) {
		data.put(getId(obj), obj);

	}

	protected abstract I getId(T obj);

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find() {
		return (List<T>) Arrays.asList(data.values().toArray());
	}

	@Override
	public List<T> find(String order) {
		return this.find();
	}

	@Override
	public List<T> find(String order, boolean inverse) {
		return this.find();
	}

}
