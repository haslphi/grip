package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ROBOT")
public class Robot extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5321846261943219726L;
	
	private static final String COLUMN_NAME = "NAME";
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

}