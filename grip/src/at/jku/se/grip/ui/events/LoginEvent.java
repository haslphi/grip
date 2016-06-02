package at.jku.se.grip.ui.events;

public class LoginEvent {
	boolean validLogin = false;
	
	public LoginEvent(boolean validLogin){
		this.validLogin = validLogin;
	}
	
	public boolean getValidLogin(){
		return validLogin;
	}
	
}