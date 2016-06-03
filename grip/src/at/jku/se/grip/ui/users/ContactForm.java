package at.jku.se.grip.ui.users;

import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class ContactForm extends CustomComponent {

	private FormLayout mainLayout = null;
	private VerticalLayout actions = null;
	private HorizontalLayout saveCancelButtons = null;
    private Button saveButton = null;
    private Button cancelButton = null;
    private TextField firstName = null;
    private TextField lastName = null;
    private TextField phone = null;
    private TextField email = null;
    private DateField birthDate = null;

    public ContactForm() {  	
    	super();
    	init();
    }
    
	private void init(){
		this.setCompositionRoot(getLoginForm());
	}
	
	private FormLayout getLoginForm(){
		if (mainLayout == null){
			mainLayout = new FormLayout();
			//mainLayout.setSizeUndefined();
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
			actions.addComponent(getPhone());
			actions.addComponent(getEmail());
			actions.addComponent(getBirthDate());
		}
		return actions;
	}
	
	private HorizontalLayout getSaveCancelButtons(){
		if(saveCancelButtons == null) {
			saveCancelButtons = new HorizontalLayout();
			saveCancelButtons.setSpacing(true);
			saveCancelButtons.addComponent(getSaveButton());
			saveCancelButtons.addComponent(getCancleButton());
		}
		return saveCancelButtons;
	}
	
	private Button getSaveButton(){
		if(saveButton == null) {
			saveButton = new Button("Save");
		}
		return saveButton;
	}	
	
	private Button getCancleButton(){
		if(cancelButton == null) {
			cancelButton = new Button("Cancel");
		}
		return cancelButton;
	}
	
	private TextField getFirstName(){
		if(firstName == null) {
			firstName = new TextField("First name");
		}
		return firstName;
	}
	
	private TextField getLastName(){
		if(lastName == null) {
			lastName = new TextField("Last name");
		}
		return lastName;
	}
	
	private TextField getPhone(){
		if(phone == null) {
			phone = new TextField("Phone");
		}
		return phone;
	}
	
	private TextField getEmail(){
		if(email == null) {
			email = new TextField("Email");
		}
		return email;
	}
	
	private DateField getBirthDate(){
		if(birthDate == null) {
			birthDate = new DateField("Birth date");
		}
		return birthDate;
	}

}
