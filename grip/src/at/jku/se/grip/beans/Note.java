package at.jku.se.grip.beans;

import static at.jku.se.grip.common.Constants.MAX_HISTORY;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import at.jku.se.grip.dao.DaoServiceRegistry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NOTE", uniqueConstraints={@UniqueConstraint(columnNames = {Note.COLUMN_NOTE_USER_ID, HistoryPK.COLUMN_HISTORY})})
@Getter
@Setter
@NoArgsConstructor
public class Note extends GenericEntity<HistoryPK> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7249109117013417502L;
	
	public static final String COLUMN_NOTE_USER_ID = "FK_NOTE_USER_ID";
	public static final String COLUMN_NOTE_USER_HISTORY = "FK_NOTE_USER_HISTORY";
	public static final String COLUMN_NOTES = "NOTES";
	
	@EmbeddedId
	private HistoryPK id;
	
	// foreign key name cannot be defined, because its used in more objects -> multiple constraint names
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = COLUMN_NOTE_USER_ID, referencedColumnName = HistoryPK.COLUMN_ID, insertable = true, updatable = false, nullable = false),
		@JoinColumn(name = COLUMN_NOTE_USER_HISTORY, referencedColumnName = HistoryPK.COLUMN_HISTORY, insertable = true, updatable = false, nullable = false)
	})
	private User noteUser;
	
	@Column(name = COLUMN_NOTES)
	private String notes;
	
	@Override
	public void preCreate() {
		setId(new HistoryPK((String) DaoServiceRegistry.provideUuid(), MAX_HISTORY));
		super.preCreate();
	}
}
