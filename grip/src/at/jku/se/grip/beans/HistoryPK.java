package at.jku.se.grip.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Embeddable
public class HistoryPK extends GenericPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8632013743662803965L;
	
	public static final String COLUMN_ID = "PK_ID";
	public static final String COLUMN_HISTORY = "PK_HISTORY";
	
	@Column(name = COLUMN_ID, length = 32, nullable = false)
	private String id;
	
	@Column(name = COLUMN_HISTORY, nullable = false)
	private Long history;

	
}
