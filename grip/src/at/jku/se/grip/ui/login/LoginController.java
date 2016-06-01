package at.jku.se.grip.ui.login;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.ui.events.LoginEvent;

import com.vaadin.ui.Button;

public class LoginController {
	
	private LoginView view = null;
	
	public LoginController(){
		view = new LoginView();
		init();
	}

	private void init(){
		view.getSignInButton().addClickListener(this::signIn);
		//view.getSignInButton().addClickListener(e -> signIn(e));
	}
	
	private void signIn (Button.ClickEvent event) {
		String username = view.getUsernameTextField().getValue();
		String password = view.getpasswordPasswordField().getValue();
		
		if(username!=null && password!=null){
			GripUI.getEventBus().post(new LoginEvent("user".equals(username)&&"pwd".equals(password)));		
		} else {
			GripUI.getEventBus().post(new LoginEvent(false));			
		}
	}
	
	public LoginView getView(){
		return view;
	}
	
}
