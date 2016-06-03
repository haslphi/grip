package at.jku.se.grip.ui;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.ui.events.LogoutEvent;
import at.jku.se.grip.ui.overview.OverviewController;
import at.jku.se.grip.ui.overview.OverviewView;
import at.jku.se.grip.ui.users.UsersController;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

public class ApplicationController {
	private UsersController usersController = null;
	private OverviewController overviewController = null;
	private Component actComponent = null; 

	private ApplicationView view = null;
	
	public ApplicationController() {
		view = new ApplicationView();
		init();
	}
	
	private void init(){
		switchToOverview();
		view.getNavigatorView().getOverviewButton().addClickListener(this::overview);
		view.getNavigatorView().getUsersButton().addClickListener(this::users);
		view.getNavigatorView().getSignOutButton().addClickListener(this::signOut);
	}
	
	private void overview (Button.ClickEvent event){
		switchToOverview();
	}
	
	private void users (Button.ClickEvent event){
		switchToUsers();
	}
	
	private void signOut (Button.ClickEvent event){
		GripUI.getEventBus().post(new LogoutEvent());
	}
	
	private void switchToOverview(){
		if(overviewController==null){
			overviewController = new OverviewController();			
		}
		if(actComponent!=null){
			view.removeComponent(actComponent);
		}
		actComponent = overviewController.getView();
		view.addComponent(actComponent);
	}
	
	private void switchToUsers(){
		if(usersController==null){
			usersController = new UsersController();			
		}
		if(actComponent!=null){
			view.removeComponent(actComponent);
		}
		actComponent = usersController.getView();
		view.addComponent(actComponent);
		view.setExpandRatio(actComponent, 1.0f);
	}
	
	public ApplicationView getView(){
		return view;
	}
	
}
