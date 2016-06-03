package at.jku.se.grip.dao.base;

import at.jku.se.grip.beans.HistorizableEntity;

public interface IHistorizableDAO<H extends HistorizableEntity> extends IGenericDAO<H> {

	H findById(String uuid);
	
	@Override
	H save(H bean);
	@Override
	H delete(H bean);
}
