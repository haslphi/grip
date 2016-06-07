package at.jku.se.grip.ui.login;

import java.security.GeneralSecurityException;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.beans.User;
import at.jku.se.grip.common.Constants;
import at.jku.se.grip.common.NotificationPusher;
import at.jku.se.grip.dao.DaoServiceRegistry;
import at.jku.se.grip.security.CryptoService;
import at.jku.se.grip.ui.events.LoginEvent;

public class LoginController {

	private LoginView view = null;

	public LoginController() {
		view = new LoginView();
		init();
	}

	private void init() {
		view.getSignInButton().addClickListener(this::signIn);
	}

	private void signIn(Button.ClickEvent event) {
		String username = view.getUsernameTextField().getValue();
		String password = view.getPasswordPasswordField().getValue();
		User user = null;
		
		if(StringUtils.isNoneBlank(username, password)) {
			try {
				user = DaoServiceRegistry
						.getUserDAO()
						.findByUsernameAndPassword(username, CryptoService.getInstance().encrypt(password, Constants.CRYPTO_KEY));
			} catch (GeneralSecurityException e) {
				NotificationPusher.showError(Page.getCurrent(), new Exception(), null);
				e.printStackTrace();
			}
		}
		if(user != null && !user.isNew() && !user.isDeleted()) {	
			GripUI.getEventBus().post(new LoginEvent(true, user));	
		} else {
			showLoginDeclinedNotification();
			GripUI.getEventBus().post(new LoginEvent(false, null));			
		}
	}

	public LoginView getView() {
		return view;
	}
	
	private void showLoginDeclinedNotification() {
		String caption = "User does not exist or username/password is wrong!";
		NotificationPusher.showCustomError(Page.getCurrent(), null, caption, null);
//		Notification notif = new Notification("User does not exist or username/password is wrong!", Notification.Type.ERROR_MESSAGE);
//		notif.setPosition(Position.BOTTOM_CENTER);
//		notif.show(Page.getCurrent());
//		notif.setDelayMsec(3000);
	}
}
