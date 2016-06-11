package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DRAWING")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "jsonPath")
public class Drawing extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2245864364629429145L;

	private static final String COLUMN_JASON_PATH = "JSON_PATH";
	private static final String COLUMN_NAME = "NAME";
	
	@Column(name = COLUMN_NAME)
	private String name;
	
	@Column(name = COLUMN_JASON_PATH)
	@Lob
	private String jsonPath;
	
}
