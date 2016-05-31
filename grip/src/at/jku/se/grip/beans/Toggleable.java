package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Toggleable {

	public static final String COLUMN_IS_ENABLED = "IS_ENABLED";

	@Column(name = COLUMN_IS_ENABLED)
	private Boolean isEnabled;
	
	/**
	 * Is enabled per default
	 */
	public Toggleable() {
		isEnabled = true;
	}
}
