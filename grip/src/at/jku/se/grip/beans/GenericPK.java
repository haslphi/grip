package at.jku.se.grip.beans;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Generic primary key class, to force all descendants to override hashCode and equals method.
 * 
 * @author Philipp
 *
 */
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
