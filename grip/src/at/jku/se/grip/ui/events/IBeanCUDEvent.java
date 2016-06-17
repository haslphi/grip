package at.jku.se.grip.ui.events;

import at.jku.se.grip.beans.GenericEntity;
import at.jku.se.grip.beans.GenericPK;
import at.jku.se.grip.common.UpdateType;

/**
 * Create/Update/Delete Event for persistence Beans
 * 
 * @author Philipp
 *
 */
public interface IBeanCUDEvent {
	
	public GenericEntity<? extends GenericPK> getBean();
	public UpdateType getUpdateType();

}
