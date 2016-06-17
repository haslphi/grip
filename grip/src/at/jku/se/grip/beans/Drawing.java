package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@AllArgsConstructor
@ToString(exclude = "jsonPath")
public class Drawing extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2245864364629429145L;

	public static final String COLUMN_JASON_PATH = "JSON_PATH";
	public static final String COLUMN_NAME = "NAME";
	
	@Column(name = COLUMN_NAME)
	private String name;
	
	@Column(name = COLUMN_JASON_PATH)
	@Lob
	private String jsonPath;
	
}
