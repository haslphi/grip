package at.jku.se.grip.ui;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.beans.Drawing;
import at.jku.se.grip.ui.drawboard.DrawboardController;
import at.jku.se.grip.ui.events.LogoutEvent;
import at.jku.se.grip.ui.events.OpenDrawingEvent;
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
		// register this instance to the eventbus
		GripUI.getEventBus().register(this);
		
		// set user to the navigation
		view.getNavigatorView().getUserLabel().setValue("User:"
				+ " <span class=\"valo-menu-badge\">" + GripUI.getUser().getUsername()
                + "</span>");
		
		// switch to initial panel
		switchToOverview();
		
		// add listener
		view.getNavigatorView().getOverviewButton().addClickListener(this::overview);
		view.getNavigatorView().getDrawboardButton().addClickListener(this::drawboard);
		view.getNavigatorView().getRobotsButton().addClickListener(this::robots);
		view.getNavigatorView().getUsersButton().addClickListener(this::users);
		view.getNavigatorView().getSignOutButton().addClickListener(this::signOut);
	}
	
	/* LISTENERS */
	private void overview (Button.ClickEvent event){
		switchToOverview();
	}
	
	private void drawboard(Button.ClickEvent event) {
		switchToDrawboard(null);
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
			overviewController.saveNote();
		}
		// send logout event on eventbus
		GripUI.getEventBus().post(new LogoutEvent());
	}
	
	// Subscribe to eventbus
	@Subscribe
	public void listenOpenDrawing(OpenDrawingEvent event) {
		switchToDrawboard(event.getBean());
	}
	
	/**
	 * remove current component from view and start/switch to overview panel
	 */
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
	
	/**
	 * remove current component from view and start/switch to drawboard panel
	 */
	private void switchToDrawboard(Drawing bean){
		if(drawboardController == null){
			drawboardController = new DrawboardController(bean);			
		} else {
			drawboardController.setDrawing(bean);
		}
		if(actComponent != null){
			view.removeComponent(actComponent);
		}
		actComponent = drawboardController.getView();
		view.addComponent(actComponent);
		view.setExpandRatio(actComponent, 1.0f);
	}
	
	/**
	 * remove current component from view and start/switch to robots panel
	 */
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
	
	/**
	 * remove current component from view and start/switch to users panel
	 */
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
	
	/**
	 * Getter for the panel view
	 * 
	 * @return
	 */
	public ApplicationView getView(){
		return view;
	}
	
}
