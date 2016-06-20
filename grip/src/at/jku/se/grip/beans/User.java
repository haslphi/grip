package at.jku.se.grip.beans;

import java.security.GeneralSecurityException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import at.jku.se.grip.common.Constants;
import at.jku.se.grip.common.UpdateType;
import at.jku.se.grip.dao.DaoServiceRegistry;
import at.jku.se.grip.security.CryptoService;
import at.jku.se.grip.ui.events.IBeanCUDEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "GRIP_USER", uniqueConstraints={@UniqueConstraint(columnNames = {User.COLUMN_USERNAME , HistoryPK.COLUMN_HISTORY})})
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"passwordDecrypted", "password"})
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
	
	@Transient
	private String passwordDecrypted;
	
	@Override
	public void preCreate() {
		try {
			password = CryptoService.getInstance().encrypt(password, Constants.CRYPTO_KEY);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		super.preCreate();
	}
	
	@Override
	public void preUpdate() {
		try {
			password = CryptoService.getInstance().encrypt(password, Constants.CRYPTO_KEY);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		super.preUpdate();
	}
	
	@Override
	public void postCreate() {
		Note note = new Note();
		note.setNoteUser(this);
		DaoServiceRegistry.getNoteDAO().save(note);
		super.postCreate();
	}
	
	@Override
	public IBeanCUDEvent createEvent(UpdateType updateType) {
		// sending of an event for this bean is currently not needed
		//return new UserPersistenceEvent(this, updateType);
		return null;
	}
	
	/**
	 * Event that can be sent when a {@link Robot} bean is created/updated/deleted.
	 */
	@AllArgsConstructor
	public static class UserPersistenceEvent implements IBeanCUDEvent {
		private User bean = null;
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
