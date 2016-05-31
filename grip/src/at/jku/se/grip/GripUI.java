package at.jku.se.grip;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import at.jku.se.grip.ui.ApplicationView;
import at.jku.se.grip.ui.view.LoginScreen;

@SuppressWarnings("serial")
@Theme("grip")
public class GripUI extends UI {
	//private Canvas canvas;
	public String user;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GripUI.class, widgetset="at.jku.se.grip.widgetset.GripWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		/*
		final HorizontalLayout layout = new HorizontalLayout();
	    layout.setMargin(true);
	    layout.setSpacing(true);
	  
	    LoginScreen.buildLoginScreen(layout);
	    setContent(layout);
	    */

		setSizeFull();
		
		ApplicationView view = new ApplicationView();
		setContent(view);
	}

}