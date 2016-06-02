package at.jku.se.grip.ui;

public class ApplicationController {

	private ApplicationView view = null;
	
	public ApplicationController() {
		view = new ApplicationView();
		init();
	}
	
	private void init(){
		
	}
	
	public ApplicationView getView(){
		return view;
	}
	
}
