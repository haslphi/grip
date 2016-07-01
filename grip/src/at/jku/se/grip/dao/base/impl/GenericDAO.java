package at.jku.se.grip.dao.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Order;
import javax.transaction.TransactionRolledbackException;

import com.google.common.eventbus.EventBus;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.beans.GenericEntity;
import at.jku.se.grip.beans.GenericPK;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.dao.base.IGenericDAO;
import at.jku.se.grip.enums.UpdateType;
import at.jku.se.grip.ui.events.IBeanCUDEvent;
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
		TypedQuery<T> query = CriteriaFactory.create().createCriteria(getEM(), getType());
		
		return query.getResultList();
	}
	
	public List<T> findByCriteria(CriteriaFactory factory) {
		List<T> list = new ArrayList<T>();
		
		beginSession();
		
		try {
			TypedQuery<T> query = factory.createCriteria(getEM(), getType());
			list = query.getResultList();
		} finally {
			closeSession();
		}
		return list;
	}
	
	@Override
	public T save(T bean) {
		UpdateType type = UpdateType.UPDATE;

		if(bean.isNew()) {
			type = UpdateType.ADD;
		}
		return mergeBean(bean, type);
	}
	
	@Override
	public T delete(T bean) {
		UpdateType type = UpdateType.DELETE;
		bean = mergeBean(bean, type);
		return bean;
	}

	protected T findInEM(Class<T> clazz, GenericPK id) {
		T entity = null;
		
		if(clazz != null && id != null) {
			entity = getEM().find(clazz, id);
		}
		return entity;
	}
	
	protected T mergeBean(T bean, UpdateType type) {
		//StopWatch sw = new StopWatch();
		//sw.start();

		beginSession();
		try {
			beginTransaction();
			if (UpdateType.ADD.equals(type)) {
				bean.preCreate();
				getEM().persist(bean);
				getEM().flush();
				bean.postCreate();
			} else if (UpdateType.UPDATE.equals(type)) {
				T savedBean = getEM().merge(bean);
				getEM().flush();
				bean = savedBean;
			} else if (UpdateType.DELETE.equals(type)) {
				bean = removeBean(bean);
			}

			commitTransaction();
		} catch (EntityExistsException e) {
			rollbackTransaction();
			throw e;
		} catch (TransactionRolledbackException e) {
		} catch (Exception e) {
			rollbackTransaction();
			throw e;
		}
		finally {
			closeSession();
		}
		
		sendCUDEvent(bean, type);

		return bean;
	}
	
	protected T removeBean(T bean) {
		// should not be necessary with EclipseLink, removing of detached beans should be possible
		//bean = findInEM(getType(), bean.getId());
		bean = getEM().merge(bean);
		getEM().remove(bean);
		
		sendCUDEvent(bean, UpdateType.DELETE);
		
		return bean;
	}
	
	/**
	 * Send an {@link IBeanCUDEvent} over the {@link EventBus}
	 * 
	 * @param bean
	 * @param updateType
	 */
	protected void sendCUDEvent(T bean, UpdateType updateType) {
		IBeanCUDEvent event = bean.createEvent(UpdateType.UPDATE);
		if(event != null) {
			GripUI.getEventBus().post(bean.createEvent(UpdateType.UPDATE));
		}
	}
}
