package at.jku.se.grip.ui.view;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.ui.events.Login;

@SuppressWarnings("serial")
public final class DashboardMenu extends CustomComponent {

    public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	
    public DashboardMenu() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
        
        setCompositionRoot(buildContent());
    }
    
    private Component buildContent() {
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
        menuContent.addComponent(buildPerformer());
        menuContent.addComponent(buildEditor());
        menuContent.addComponent(buildLogout());
    	return menuContent;
    }
    
    private Component buildTitle() {
        Label logo = new Label("<strong>GRIP</strong>", ContentMode.HTML);       
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }
      
    private Component buildOverview() {
        Button overviewButton = new Button("Übersicht");
        overviewButton.setSizeUndefined();
        overviewButton.setWidth("100px");      
        return overviewButton;       
    }  
    
    private Component buildPerformer() {
        Button performerButton = new Button("Ausführen");
        performerButton.setSizeUndefined();
        performerButton.setWidth("100px"); 
        return performerButton;       
    }
    
    private Component buildEditor() {
        Button editorButton = new Button("Editieren");
        editorButton.setSizeUndefined();
        editorButton.setWidth("100px"); 
        return editorButton;       
    }
    
    private Component buildLogout() {
        Button logoutButton = new Button("Abmelden");
        logoutButton.setSizeUndefined();
        logoutButton.setWidth("100px");
        return logoutButton;       
    }
     	
}
