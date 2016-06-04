package at.jku.se.grip.ui.events;

import lombok.Getter;
import at.jku.se.grip.beans.User;

@Getter
public class LoginEvent {
	private boolean validLogin = false;
	private User user = null;
	
	public LoginEvent(boolean validLogin, User user){
		this.validLogin = validLogin;
		this.user = user;
	}
}