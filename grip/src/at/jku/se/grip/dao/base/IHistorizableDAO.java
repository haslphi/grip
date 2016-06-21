package at.jku.se.grip.dao.base;

import java.util.List;

import at.jku.se.grip.beans.HistorizableEntity;
import at.jku.se.grip.common.CriteriaFactory;

public interface IHistorizableDAO<H extends HistorizableEntity> extends IGenericDAO<H> {

	H findById(String uuid);
	
	List<H> findAllWithoutHistory();
	
	@Override
	List<H> findByCriteria(CriteriaFactory factory);
	List<H> findByCriteriaWithHistory(CriteriaFactory factory);
	
	@Override
	H save(H bean);
	@Override
	H delete(H bean);
}
