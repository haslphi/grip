package at.jku.se.grip.dao.base.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;

import at.jku.se.grip.beans.GenericEntity;
import at.jku.se.grip.beans.GenericPK;
import at.jku.se.grip.dao.base.IGenericDAO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class GenericDAO<T extends GenericEntity<? extends GenericPK>> extends AbstractDAO implements IGenericDAO<T> {

	@Override
	public T findById(GenericPK id) {
		if(id == null) {
			return null;
		}
		try {
			beginSession();
			T bean = findInEM(getType(), id);
			return bean;
		} catch (EntityNotFoundException e) {
			return null;
		} catch (Exception e) {
			//throw wrapSQLErrorToCASError(e);
			getEM().clear();
			throw e;
		} finally {
			closeSession();
		}
	}
	
	@Override
	public T find(T bean) {
		if (bean == null || bean.getId() == null) {
			return null;
		}
		return findById(bean.getId());
	}
	
	@Override
	public List<T> findAll() {
		return findAll(null);
	}

	@Override
	public List<T> findAll(Order order) {
		// TODO: create CriteriaFactory and use this factory to call findByCriteria with created factor inclusive order
		CriteriaBuilder cb = getEM().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getType());
		//cq.orderBy(order);
		cq.where();
		TypedQuery<T> query = getEM().createQuery(cq);
		
		return query.getResultList();
		
		
		//return findByCriteria(CriteriaFactory.getInstance().addOrder(order));
	}

	protected T findInEM(Class<T> clazz, GenericPK id) {
		T entity = null;
		
		if(clazz != null && id != null) {
			entity = getEM().find(clazz, id);
		}
		return entity;
	}
}
