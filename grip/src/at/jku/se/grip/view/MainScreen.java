package at.jku.se.grip.view;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class MainScreen extends HorizontalLayout {
	static Component newScreen;
	static Component oldScreen; 
	
	public static void buildMainScreen(ComponentContainer layout, String name) {

        Component dashboard = buildDashboard(layout);
        layout.addComponent(dashboard);   
		        
	}
	
    private static Component buildDashboard(final ComponentContainer layout) {
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");
        menuContent.setSpacing(true);
        
        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildOverview());
        menuContent.addComponent(buildPerformer(layout));
        menuContent.addComponent(buildEditor(layout));
        menuContent.addComponent(buildLogout(layout));
        
    	return menuContent;
    }
    
    private static Component buildTitle() {
        Label logo = new Label("<strong>GRIP</strong>", ContentMode.HTML);       
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }
      
    private static Component buildOverview() {
        Button overviewButton = new Button("Übersicht");
        overviewButton.setSizeUndefined();
        overviewButton.setWidth("100px");      
        return overviewButton;       
    }  
    
    private static Component buildPerformer(final ComponentContainer layout) {
        Button performerButton = new Button("Ausführen");
        performerButton.setSizeUndefined();
        performerButton.setWidth("100px");
        
        performerButton.addClickListener(new ClickListener() {
        	@Override
            public void buttonClick(final ClickEvent event) {
        		
        		newScreen = buildDashboard(layout);
        		layout.removeComponent(oldScreen);
                layout.addComponent(newScreen);

        	}
        });
        
        return performerButton;       
    }
    
    private static Component buildEditor(final ComponentContainer layout) {
        Button editorButton = new Button("Editieren");
        editorButton.setSizeUndefined();
        editorButton.setWidth("100px");
        
        editorButton.addClickListener(new ClickListener() {
        	@Override
            public void buttonClick(final ClickEvent event) {
        		
        		newScreen = buildDashboard(layout);
        		layout.removeComponent(oldScreen);
                layout.addComponent(newScreen);

        	}
        });
        
        return editorButton;       
    }
    
    private static Component buildLogout(final ComponentContainer layout) {
        Button logoutButton = new Button("Abmelden");
        logoutButton.setSizeUndefined();
        logoutButton.setWidth("100px"); 
        
        logoutButton.addClickListener(new ClickListener() {
        	@Override
            public void buttonClick(final ClickEvent event) {
        		layout.removeAllComponents();
        	    LoginScreen.buildLoginScreen(layout);
        	}
        });
        
        return logoutButton;       
    }

}
