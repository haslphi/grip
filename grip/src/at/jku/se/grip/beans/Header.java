package at.jku.se.grip.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1251866687310950960L;
	
	public static final String COLUMN_DT_CREATED = "DT_CREATED";
	public static final String COLUMN_DT_MODIFIED = "DT_MODIFIED";
	public static final String COLUMN_DT_FLAGGED_DELETED = "DT_FLAGGED_DELETED";
	public static final String COLUMN_CREATED_BY_ID = "FK_CREATED_BY_ID";
	public static final String COLUMN_CREATED_BY_HISTORY = "FK_CREATED_BY_HISTORY";
	public static final String COLUMN_MODIFIED_BY_ID = "FK_MODIFIED_BY_ID";
	public static final String COLUMN_MODIFIED_BY_HISTORY = "FK_MODIFIED_BY_HISTORY";
	public static final String COLUMN_FLAGGED_DELETED_BY_ID = "FK_FLAGGED_DELETED_BY_ID";
	public static final String COLUMN_FLAGGED_DELETED_BY_HISTORY = "FK_FLAGGED_DELETED_BY_HISTORY";
	
	@Column(name = COLUMN_DT_CREATED, insertable = true, updatable = false, nullable = false)
	private Date createdDate;

	@Column(name = COLUMN_DT_MODIFIED)
	private Date modifiedDate;

	@Column(name = COLUMN_DT_FLAGGED_DELETED)
	private Date flaggedDeletedDate;
	
	// foreign key name cannot be defined, because its used in more objects -> multiple constraint names
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = COLUMN_CREATED_BY_ID, referencedColumnName = HistoryPK.COLUMN_ID, insertable = true, updatable = false),
		@JoinColumn(name = COLUMN_CREATED_BY_HISTORY, referencedColumnName = HistoryPK.COLUMN_HISTORY, insertable = true, updatable = false)
	})
	private User createdBy;
	
	//@Column(name = COLUMN_CREATED_BY_ID, length = 32, insertable = false, updatable = false)
	//private String createdById;

	// foreign key name cannot be defined, because its used in more objects -> multiple constraint names
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = COLUMN_MODIFIED_BY_ID, referencedColumnName = HistoryPK.COLUMN_ID, insertable = true, updatable = true),
		@JoinColumn(name = COLUMN_MODIFIED_BY_HISTORY, referencedColumnName = HistoryPK.COLUMN_HISTORY, insertable = true, updatable = true)
	})
	private User modifiedBy;

	//@Column(name = COLUMN_MODIFIED_BY_ID, length = 32, insertable = false, updatable = false)
	//private String modifiedById;

	// foreign key name cannot be defined, because its used in more objects -> multiple constraint names
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = COLUMN_FLAGGED_DELETED_BY_ID, referencedColumnName = HistoryPK.COLUMN_ID, insertable = true, updatable = true),
		@JoinColumn(name = COLUMN_FLAGGED_DELETED_BY_HISTORY, referencedColumnName = HistoryPK.COLUMN_HISTORY, insertable = true, updatable = true)
	})
	private User flaggedDeletedBy;

	//@Column(name = COLUMN_FLAGGED_DELETED_BY_ID, length = 32, insertable = false, updatable = false)
	//private String flaggedDeletedById;
}
