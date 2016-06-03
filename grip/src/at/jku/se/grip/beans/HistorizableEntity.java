package at.jku.se.grip.beans;

import static at.jku.se.grip.common.Constants.MAX_HISTORY;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;

import at.jku.se.grip.dao.DaoServiceRegistry;
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
		return super.isNew() || StringUtils.isBlank(getId().getId());
	}
	

	@Override
	public void preCreate() {
		setId(new HistoryPK((String) DaoServiceRegistry.provideUuid(), MAX_HISTORY));
		if(getHeader().getCreatedDate() == null){
			Date now = new Date();
			getHeader().setCreatedDate(now);
		}
		if(getHeader().getCreatedById() == null){
			// TODO: set current user
			//getHeader().setCreatedBy(getUser());
		}
		super.preCreate();
	}

	@Override
	public void preUpdate() {
		Date now = new Date();
		getHeader().setModifiedDate(now);
		// TODO: set current user
		//getHeader().setModifiedBy(getUser());
	}

	@Override
	public void preDelete() {
		Date now = new Date();
		getHeader().setFlaggedDeletedDate(now);
		// TODO: set current user
		//getHeader().setFlaggedDeletedBy(getUser());
	}
}
