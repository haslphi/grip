package at.jku.se.grip.beans;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class VersionableEntity<ID extends GenericPK> extends GenericEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8496726560116769302L;
	
	@Version
	private Integer version;
	
	@Embedded
	private Header header = new Header();
	
	@Override
	public boolean isNew() {
		return super.isNew() && version != null;
	}
	
	public boolean isDeleted() {
		return header != null && header.getFlaggedDeletedBy() != null && header.getFlaggedDeletedDate() != null;
	}
}
