package at.jku.se.grip.ui;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

import at.jku.se.grip.ui.login.LoginView;
import at.jku.se.grip.ui.navigator.NavigatorView;

public class ApplicationView extends HorizontalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5798163901372154343L;
	
	private NavigatorView navigatorView = null;
	public LoginView loginView= null;
	private ComponentContainer mainComponentContainer = null;
	
	public ApplicationView() {
		super();
		init();
	}
	
	private void init() {
		this.setSizeFull();
		//this.addComponent(getLoginview());
		this.addComponent(getNavigatorView());		
	}
	
	public LoginView getLoginview(){
		if(loginView==null){
			loginView = new LoginView();
		}
		return loginView;
	}
	
	public NavigatorView getNavigatorView() {
		if(navigatorView == null) {
			navigatorView = new NavigatorView();
		}
		return navigatorView;
	}
	
	public ComponentContainer getMainComponentContainer() {
		if(mainComponentContainer == null) {
			mainComponentContainer = new CssLayout();
			mainComponentContainer.addStyleName("view-content");
			mainComponentContainer.setSizeFull();
		}
		return mainComponentContainer;
	}

}
