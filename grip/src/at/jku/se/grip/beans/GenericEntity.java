package at.jku.se.grip.beans;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import at.jku.se.grip.enums.UpdateType;
import at.jku.se.grip.ui.events.IBeanCUDEvent;

/**
 * Root entity for all persistence entities.
 * 
 * @author Philipp
 *
 * @param <ID> a generic primary key
 */
@MappedSuperclass
public abstract class GenericEntity<ID extends GenericPK> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7939838273153004262L;
	
	public abstract ID getId();
	public abstract void setId(ID id);
	
	/**
	 * Check if this is a new bean, or if the bean is already persisted.
	 * 
	 * @return
	 */
	public boolean isNew() {
		return getId() == null;
	}
	
	/**
	 * Override to do some things (e.g. initialization of custom id) directly before persisting the bean.
	 */
	public void preCreate() {
	}
	
	/**
	 * Override to do some things directly before merging the bean.
	 */
	public void preUpdate() {
	}
	
	/**
	 * Override to do some things directly before removing the bean.
	 */
	public void preDelete() {
	}
	
	/**
	 * Override to do some things directly after adding bean.
	 */
	public void postCreate() {
	}
	
	/**
	 * Create a CUD Event of a certain type.
	 * Override and return a non null bean if you want the send an event over the EventBus.
	 * 
	 * @param updateType
	 * @return
	 */
	public IBeanCUDEvent createEvent(UpdateType updateType) {
		// sending of an event for a bean is per default "turned off" (null is returned)
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericEntity<?> other = (GenericEntity<?>) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}
