package at.jku.se.grip.dao.base;

import java.util.List;

import javax.persistence.criteria.Order;

import at.jku.se.grip.beans.GenericEntity;
import at.jku.se.grip.beans.GenericPK;
import at.jku.se.grip.common.CriteriaFactory;

public interface IGenericDAO<T extends GenericEntity<? extends GenericPK>> {
	Class<T> getType();
	
	T findById(GenericPK id);
	T find(T bean);
	
	List<T> findAll();
	List<T> findAll(Order order);
	//List<T> findByCriteria(CriteriaFactory factory);
	//Integer countByCriteria(CriteriaFactory factory);
	
	T save(T bean);
	
	T delete(T bean);
}
