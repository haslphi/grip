package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Lob;

public class Drawing extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2245864364629429145L;

	private static final String COLUMN_JASON_PATH = "JSON_PATH";
	
	@Column(name = COLUMN_JASON_PATH)
	@Lob
	private String jsonPath;
	
}
