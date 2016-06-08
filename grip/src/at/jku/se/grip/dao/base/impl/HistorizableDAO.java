package at.jku.se.grip.dao.base.impl;

import static at.jku.se.grip.common.Constants.MAX_HISTORY;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.PropertyUtils;

import at.jku.se.grip.beans.GenericPK;
import at.jku.se.grip.beans.HistorizableEntity;
import at.jku.se.grip.beans.HistoryPK;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.dao.base.IHistorizableDAO;

public abstract class HistorizableDAO<H extends HistorizableEntity> extends GenericDAO<H> implements IHistorizableDAO<H> {

	@Override
	public H findById(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public H findById(GenericPK id) {
		H found = super.findById(id);
		return found != null && found.isDeleted() ? null : found;
	}
	
	@Override
	public List<H> findByCriteria(CriteriaFactory factory) {
		factory.andIsNull("header.flaggedDeletedDate").andEquals("id.history", MAX_HISTORY);
		return super.findByCriteria(factory);
	}

	/*@Override
	public Integer countByCriteria(CriteriaFactory factory) {
		factory.isNull("header.flaggedDeletedDate")
		.isNull("header.flaggedDeletedBy");
		return super.countByCriteria(factory);
	}*/
	
	public H save(H bean) {
		//UpdateType updateType = null;
		boolean successful = false;
		H current = null;
		beginSession();
		try {
			if (!bean.isNew()) {
				current = findInEM(getType(), bean.getId());
			}

			if (bean.isNew()) {
				//updateType = UpdateType.ADD;
				beginTransaction();
				bean.preCreate();
				getEM().persist(bean);
				getEM().flush();	// flush the session, so the newly saved instance is known in the database within the session
				commitTransaction();
			} else {
				//updateType = UpdateType.UPDATE;
				H histBean = getType().getConstructor().newInstance();
				PropertyUtils.copyProperties(histBean, current);
				histBean.setId(new HistoryPK(current.getId().getId(), System.currentTimeMillis()));
				histBean.setVersion(null);

				// create updated bean
				beginTransaction();
				bean.preUpdate();
				getEM().persist(histBean);
				// merge
				H savedBean = getEM().merge(bean);
				getEM().flush();
				bean = savedBean;
				commitTransaction();
			}
			successful = true;
		} catch (EntityExistsException e) {
			rollbackTransaction();
			throw e;
		} catch (PersistenceException e) {
			rollbackTransaction();
			throw e;
		} catch (Exception e) {
			rollbackTransaction();
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			if(!successful && current == null) {
				bean.setId(null);
			}
			closeSession();
		}

		return bean;
	}
	
	@Override
	protected H removeBean(H bean) {
		beginSession();
		try {
			beginTransaction();
		bean = getEM().merge(bean);
		commitTransaction();
		closeSession();
		} catch(Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return bean;
	}
	
}
