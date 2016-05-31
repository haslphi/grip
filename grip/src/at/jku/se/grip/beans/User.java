package at.jku.se.grip.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GRIP_USER")
@Getter
@Setter
@NoArgsConstructor
public class User extends HistorizableEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6249065915791024487L;
	
	public static final String COLUMN_USERNAME = "USERNAME";
	public static final String COLUMN_FIRSTNAME = "FIRSTNAME";
	public static final String COLUMN_LASTNAME = "LASTNAME";
	public static final String COLUMN_EMAIL = "EMAIL";
	public static final String COLUMN_PASSWORD = "ENCRYPTED_PASSWORD";

	@Column(name = COLUMN_USERNAME, nullable = false)
	private String username;
	
	@Column(name = COLUMN_FIRSTNAME, length = 200, nullable = false)
	private String firstName;

	@Column(name = COLUMN_LASTNAME, length = 200, nullable = false)
	private String lastName;

	@Column(name = COLUMN_EMAIL, length = 200, nullable = false)
	private String email;
	
	@Column(name = COLUMN_PASSWORD, length = 255)
	private String password;
}
