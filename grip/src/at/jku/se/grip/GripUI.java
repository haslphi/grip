package at.jku.se.grip;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.common.GripEntityManager;
import at.jku.se.grip.ui.ApplicationController;
import at.jku.se.grip.ui.events.LoginEvent;
import at.jku.se.grip.ui.events.LogoutEvent;
import at.jku.se.grip.ui.login.LoginController;

@SuppressWarnings("serial")
@Theme("grip")
@Title("GRIP")
public class GripUI extends UI {
	public static User user;
	private LoginController loginController = null;
	private ApplicationController applicationController = null;
	private static EventBus eventBus = null;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GripUI.class, widgetset = "at.jku.se.grip.widgetset.GripWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				// use to inform component which need manual resizing
				
			}
		});

		// set the UI size to max
		setSizeFull();
		
		// initialize the grip entity manager with the factory (takes some time to load)
		GripEntityManager.getInstance();
		
		// register this class to the eventbus
		eventBus = new EventBus();
		eventBus.register(this);

		switchBySession();
	}
	
	/**
	 * Get the EventBus for the current session.
	 * 
	 * @return
	 */
	public static EventBus getEventBus() {
		return eventBus;
	}
	
	/**
	 * Get the current logged on user.
	 * 
	 * @return
	 */
	public static User getUser() {
		return user;
	}

	@Subscribe
	public void listenLogin(LoginEvent event){
		System.out.println("Login Event arrived: Login " + (event.isValidLogin() ? "accepted" : "declined."));
		if(event.isValidLogin()){
			user = event.getUser();
			VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
			switchToApplication();			
		}
	}

	@Subscribe
	public void listenLogout(LogoutEvent event) {
		VaadinSession.getCurrent().setAttribute(User.class.getName(), null);
		switchToLogin();
	}
	
	/**
	 * Decide if the login or application view has to be shown whether the user is already logged in.
	 */
	private void switchBySession() {
		User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        if (user != null) {
        	switchToApplication();
        } else {
        	switchToLogin();
        }
	}

	private void switchToApplication() {
		applicationController = new ApplicationController();
		setContent(applicationController.getView());
		loginController = null;
	}
	
	private void switchToLogin() {
		user = null;
		loginController = new LoginController();
		setContent(loginController.getView());
		
		// focus username text field (after view was attached)
		loginController.getView().getUsernameTextField().focus();

		applicationController = null;
	}

}