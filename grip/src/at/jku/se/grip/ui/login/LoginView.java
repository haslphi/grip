package at.jku.se.grip.ui.login;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends GridLayout {
	
	private VerticalLayout loginFormVerticalLayout = null;
	private HorizontalLayout fields = null;
	private CssLayout labels = null;
	private TextField username = null;
	private PasswordField password = null;
	private Button signin = null;	
	private Label welcome = null;
//	private Label title = null;
	
	public LoginView(){
		super();
		init();
	}
	
	private void init(){
		this.addComponent(getLoginForm());
		this.setSizeFull();
		this.setComponentAlignment(loginFormVerticalLayout, Alignment.MIDDLE_CENTER);
	}
	
	private VerticalLayout getLoginForm(){
		if(loginFormVerticalLayout == null) {
			loginFormVerticalLayout = new VerticalLayout();
			loginFormVerticalLayout.setSizeUndefined();
			loginFormVerticalLayout.setSpacing(true);
	        Responsive.makeResponsive(loginFormVerticalLayout);
	        loginFormVerticalLayout.addStyleName("login-panel");
	        
	        loginFormVerticalLayout.addComponent(getLabelsHorizontalLayout());
	        loginFormVerticalLayout.addComponent(getFieldsHorizontalLayout());
		}
		return loginFormVerticalLayout;
	}

	private HorizontalLayout getFieldsHorizontalLayout(){
		if(fields == null){
			fields = new HorizontalLayout();
	        fields.setSpacing(true);
	        fields.addStyleName("fields");
	        
	        fields.addComponent(getUsernameTextField());
	        fields.addComponent(getpasswordPasswordField());
	        fields.addComponent(getSignInButton());
			
		}
		return fields;
	}
	
	private CssLayout getLabelsHorizontalLayout(){
		if(labels == null){
			labels = new CssLayout();
	        labels.addStyleName("labels");

			labels.addComponent(getWelcomeLabel());
//			labels.addComponent(getTitleLabel());			
		}
		return labels;
	}
	
	public TextField getUsernameTextField(){
		if(username == null){
			username = new TextField();
	        username.setIcon(FontAwesome.USER);
	        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		}
		return username;
	}
	
	public PasswordField getpasswordPasswordField(){
		if(password == null){
			password = new PasswordField();
	        password.setIcon(FontAwesome.LOCK);
	        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		}
		return password;
	}
	
	public Button getSignInButton(){
		if(signin == null){
			signin = new Button("Sign In");
	        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
	        signin.setClickShortcut(KeyCode.ENTER);
	        signin.focus();
		}
		return signin;
	}
	
	private Label getWelcomeLabel(){
		if(welcome == null){
			welcome = new Label(" GRIP");
	        welcome.setSizeUndefined();
	        welcome.addStyleName(ValoTheme.LABEL_H1);
	        welcome.addStyleName(ValoTheme.LABEL_COLORED);
		}
		return welcome;
	}
	

	
//	private Label getTitleLabel(){
//		if(title == null){
//			title = new Label("Anmeldefenster");
//	        title.setSizeUndefined();
//	        title.addStyleName(ValoTheme.LABEL_H3);
//	        title.addStyleName(ValoTheme.LABEL_LIGHT);
//		}
//		return title;
//	}
	
}
