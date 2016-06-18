package at.jku.se.grip.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.ui.drawboard.DrawboardController;
import at.jku.se.grip.ui.events.LogoutEvent;
import at.jku.se.grip.ui.overview.OverviewController;
import at.jku.se.grip.ui.robots.RobotController;
import at.jku.se.grip.ui.users.UserController;

public class ApplicationController {
	private UserController usersController = null;
	private RobotController robotController = null;
	private DrawboardController drawboardController = null; 
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
		view.getNavigatorView().getDrawboardButton().addClickListener(this::drawboard);
		view.getNavigatorView().getRobotsButton().addClickListener(this::robots);
		view.getNavigatorView().getUsersButton().addClickListener(this::users);
		view.getNavigatorView().getSignOutButton().addClickListener(this::signOut);
	}
	
	private void overview (Button.ClickEvent event){
		switchToOverview();
	}
	
	private void drawboard(Button.ClickEvent event) {
		switchToDrawboard();
	}
	
	private void robots(Button.ClickEvent event) {
		switchToRobots();
	}
	
	private void users (Button.ClickEvent event){
		switchToUsers();
	}
	
	private void signOut (Button.ClickEvent event){
		// save Notes before logout
		if(overviewController != null) {
			//overviewController.
		}
		GripUI.getEventBus().post(new LogoutEvent());
	}
	
	private void switchToOverview(){
		if(overviewController == null){
			overviewController = new OverviewController();			
		}
		if(actComponent != null){
			view.removeComponent(actComponent);
		}
		actComponent = overviewController.getView();
		view.addComponent(actComponent);
		view.setExpandRatio(actComponent, 1.0f);
	}
	
	private void switchToDrawboard(){
		if(drawboardController == null){
			drawboardController = new DrawboardController();			
		}
		if(actComponent != null){
			view.removeComponent(actComponent);
		}
		actComponent = drawboardController.getView();
		view.addComponent(actComponent);
		view.setExpandRatio(actComponent, 1.0f);
	}
	
	private void switchToRobots(){
		if(robotController == null){
			robotController = new RobotController();			
		}
		if(actComponent != null){
			view.removeComponent(actComponent);
		}
		actComponent = robotController.getView();
		view.addComponent(actComponent);
		view.setExpandRatio(actComponent, 1.0f);
	}
	
	private void switchToUsers(){
		if(usersController == null){
			usersController = new UserController();			
		}
		if(actComponent != null){
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
