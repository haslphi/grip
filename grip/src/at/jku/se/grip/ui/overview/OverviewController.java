package at.jku.se.grip.ui.overview;


public class OverviewController {
	
	private OverviewView view = null;
	
	public OverviewController(){
		view = new OverviewView();
		init();
	}
	
	private void init(){
		
	}
	
	public OverviewView getView(){
		return view;
	}

}
