package at.jku.se.grip.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class HistorizableEntity extends VersionableEntity<HistoryPK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7891004754802807555L;
	
	@EmbeddedId
	private HistoryPK id;
	
	@Override
	public boolean isNew() {
		return getId() != null && StringUtils.isNotBlank(getId().getId());
	}

}
