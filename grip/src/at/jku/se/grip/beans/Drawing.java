package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.vaadin.server.ThemeResource;

import at.jku.se.grip.enums.UpdateType;
import at.jku.se.grip.ui.events.IBeanCUDEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DRAWING", uniqueConstraints={@UniqueConstraint(columnNames = {Drawing.COLUMN_NAME, HistoryPK.COLUMN_HISTORY})})
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "jsonPath")
public class Drawing extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2245864364629429145L;

	private static final ThemeResource ICON = new ThemeResource("img/ic_open_in_new_24px.png");
	
	public static final String COLUMN_JASON_PATH = "JSON_PATH";
	public static final String COLUMN_NAME = "NAME";
	public static final String COLUMN_PATH_LENGTH = "PATH_LENGTH";
	
	@Column(name = COLUMN_NAME, nullable = false)
	private String name;
	
	@Column(name = COLUMN_JASON_PATH, nullable = false)
	@Lob
	private String jsonPath;
	
	@Column(name = COLUMN_PATH_LENGTH, nullable = false)
	private Integer pathLength;
	
	@Transient
	ThemeResource icon = ICON;
	
	public Drawing(String name, String jsonPath, Integer pathLength) {
		this.name = name;
		this.jsonPath = jsonPath;
		this.pathLength = pathLength;
	}
	
	@Override
	public IBeanCUDEvent createEvent(UpdateType updateType) {
		return new DrawingPersistenceEvent(this, updateType);
	}
	
	/**
	 * Event that can be sent when a {@link Drawing} bean is created/updated/deleted.
	 */
	@AllArgsConstructor
	public static class DrawingPersistenceEvent implements IBeanCUDEvent {
		private Drawing bean = null;
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