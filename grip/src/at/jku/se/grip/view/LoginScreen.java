package at.jku.se.grip.view;

import at.jku.se.grip.event.Login;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginScreen {
	
    public static void buildLoginScreen(final ComponentContainer layout) {   	
        Component loginForm = buildLoginForm(layout);
        layout.addComponent(loginForm);
    }
    
    private static Component buildLoginForm(final ComponentContainer layout) {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields(layout));
        
        return loginPanel;
    }
    
    private static Component buildLabels() {
    	CssLayout labels = new CssLayout();

        Label welcome = new Label("GRIP");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H2);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);
        
        return labels;
    }
    
    private static Component buildFields(final ComponentContainer layout) {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Benutzername");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Passwort");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Anmelden");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
        signin.focus();

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
        
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	
            	if(Login.verifyPassword(username.getValue(), password.getValue())){

    	            layout.removeAllComponents();
    	            MainScreen.buildMainScreen(layout, username.getValue());
            		
            	} else{
            		Notification notification = new Notification("Der Name oder das Passwort ist falsch");
                    notification.setHtmlContentAllowed(true);
                    notification.setStyleName("tray dark small closable login-help");
                    notification.setPosition(Position.BOTTOM_CENTER);
                    notification.setDelayMsec(20000);
                    notification.show(Page.getCurrent());
            	}            	
            }
        });
        
        return fields;
    }
		
}
