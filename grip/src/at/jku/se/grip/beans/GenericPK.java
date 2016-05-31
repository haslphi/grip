package at.jku.se.grip.beans;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979744265163832608L;

	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object o);
}
