package at.jku.se.grip.beans;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntity<ID extends GenericPK> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7939838273153004262L;
	
	public abstract ID getId();
	public abstract void setId(ID id);
	
	public boolean isNew() {
		return getId() == null;
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
