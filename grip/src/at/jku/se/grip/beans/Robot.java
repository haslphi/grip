package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.jku.se.grip.common.UpdateType;
import at.jku.se.grip.ui.events.IBeanCUDEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ROBOT", uniqueConstraints={@UniqueConstraint(columnNames = {Robot.COLUMN_NAME, HistoryPK.COLUMN_HISTORY})})
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "description")
public class Robot extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5321846261943219726L;
	
	public static final String COLUMN_NAME = "NAME";
	private static final String COLUMN_HOST = "HOST";
	private static final String COLUMN_PORT = "PORT";
	private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	
	@Column(name = COLUMN_NAME, length = 200, nullable = false)
	private String name;
	
	@Column(name = COLUMN_HOST)
	private String host;
	
	@Column(name = COLUMN_PORT)
	private Integer port;
	
	@Column(name = COLUMN_DESCRIPTION, length = 4000)
	private String description;
	
	@Override
	public IBeanCUDEvent createEvent(UpdateType updateType) {
		return new RobotPersistenceEvent(this, updateType);
	}
	
	/**
	 * Event that can be sent when a {@link Robot} bean is created/updated/deleted.
	 */
	@AllArgsConstructor
	public static class RobotPersistenceEvent implements IBeanCUDEvent {
		private Robot bean = null;
		private UpdateType updateType = null;

		@Override
		public GenericEntity<? extends GenericPK> getBean() {
			return bean;
		}

		@Override
		public UpdateType getUpdateType() {
			return updateType;
		}
		
	}

}
