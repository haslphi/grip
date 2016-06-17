package at.jku.se.grip.ui.users;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.common.ValidatorFactory;

@SuppressWarnings("serial")
public class UserForm extends CustomComponent {

	private FormLayout mainLayout = null;
	private VerticalLayout actions = null;
	private HorizontalLayout saveCancelButtons = null;
    private Button saveButton = null;
    private Button cancelButton = null;
    private TextField firstName = null;
    private TextField lastName = null;
    private TextField username = null;
    private TextField email = null;
    private PasswordField password = null;
    
    public UserForm() {  	
    	super();
    	init();
    }
    
	private void init(){
		this.setCompositionRoot(getMainLayout());
	}
	
	private FormLayout getMainLayout(){
		if (mainLayout == null){
			mainLayout = new FormLayout();
			mainLayout.setMargin(true);
			
			mainLayout.addComponent(getActions());
			
		}				
		return mainLayout;
	}
	
	private VerticalLayout getActions(){
		if(actions == null) {
			actions = new VerticalLayout();
			actions.setSpacing(true);			
			actions.addComponent(getSaveCancelButtons());
			actions.addComponent(getFirstName());
			actions.addComponent(getLastName());
			actions.addComponent(getUsername());
			actions.addComponent(getEmail());
			actions.addComponent(getPassword());
		}
		return actions;
	}
	
	private HorizontalLayout getSaveCancelButtons(){
		if(saveCancelButtons == null) {
			saveCancelButtons = new HorizontalLayout();
			saveCancelButtons.setSpacing(true);
			saveCancelButtons.addComponent(getSaveButton());
			saveCancelButtons.addComponent(getCancelButton());
		}
		return saveCancelButtons;
	}
	
	public Button getSaveButton(){
		if(saveButton == null) {
			saveButton = new Button("Save");
			saveButton.setIcon(FontAwesome.FLOPPY_O);
			saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		}
		return saveButton;
	}	
	
	public Button getCancelButton(){
		if(cancelButton == null) {
			cancelButton = new Button("Cancel");
			cancelButton.setIcon(FontAwesome.BAN);
			cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		}
		return cancelButton;
	}
	
	public TextField getFirstName(){
		if(firstName == null) {
			firstName = new TextField("First Name");
			firstName.setNullRepresentation("");
			firstName.addValidator(ValidatorFactory.createStringLenghtValidator(firstName.getCaption(), 1, 255, false));
		}
		return firstName;
	}
	
	private TextField getLastName(){
		if(lastName == null) {
			lastName = new TextField("Last Name");
			lastName.setNullRepresentation("");
			lastName.addValidator(ValidatorFactory.createStringLenghtValidator(lastName.getCaption(), 1, 200, false));
		}
		return lastName;
	}
	
	private TextField getUsername(){
		if(username == null) {
			username = new TextField("Username");
			username.setIcon(FontAwesome.USER);
			username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			username.setNullRepresentation("");
			username.addValidator(ValidatorFactory.createStringLenghtValidator(username.getCaption(), 1, 200, false));
		}
		return username;
	}
	
	private TextField getEmail(){
		if(email == null) {
			email = new TextField("Email");
			email.setIcon(FontAwesome.AT);
			email.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			email.setNullRepresentation("");
			email.addValidator(ValidatorFactory.createEmailValidator());
			email.addValidator(ValidatorFactory.createStringLenghtValidator(email.getCaption(), 1, 200, false));
		}
		return email;
	}
	
	public PasswordField getPassword(){
		if(password == null){
			password = new PasswordField("Password");
	        password.setIcon(FontAwesome.LOCK);
	        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	        username.setNullRepresentation("");
	        password.addValidator(ValidatorFactory.createStringLenghtValidator(password.getCaption(), 1, 200, false));
		}
		return password;
	}
}
