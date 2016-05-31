package at.jku.se.grip.ui;

import org.vaadin.viritin.layouts.MHorizontalLayout;

import at.jku.se.grip.ui.navigator.NavigatorView;

public class ApplicationView extends MHorizontalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5798163901372154343L;
	
	private NavigatorView navigatorView = null;
	private MHorizontalLayout mainHorizontalLayout = null;
	
	public ApplicationView() {
		super();
		init();
	}
	
	private void init() {
		this.setWidth("100%");
		this.setHeight("100%");
		this.addComponent(getNavigatorView());
		this.add(getMainHorizontalLayout());
	}
	
	public NavigatorView getNavigatorView() {
		if(navigatorView == null) {
			navigatorView = new NavigatorView();
			navigatorView.setWidth("200px");
		}
		return navigatorView;
	}
	
	public MHorizontalLayout getMainHorizontalLayout() {
		if(mainHorizontalLayout == null) {
			mainHorizontalLayout = new MHorizontalLayout();
			mainHorizontalLayout.setWidth("100%");
			mainHorizontalLayout.setHeight("100%");
		}
		return mainHorizontalLayout;
	}

}
