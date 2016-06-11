package at.jku.se.grip.ui.robots;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.common.ValidatorFactory;

@SuppressWarnings("serial")
public class RobotForm extends CustomComponent {

	private FormLayout mainLayout = null;
	private VerticalLayout actions = null;
	private HorizontalLayout saveCancelButtons = null;
    private Button saveButton = null;
    private Button cancelButton = null;
    private TextField name = null;
    private TextField host = null;
    private TextField port = null;
    private TextArea description = null;
    
    public RobotForm() {  	
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
			actions.addComponent(getName());
			actions.addComponent(getHost());
			actions.addComponent(getPort());
			actions.addComponent(getRobotDescription());
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
	
	public Button getSaveButton(){
		if(saveButton == null) {
			saveButton = new Button("Save");
		}
		return saveButton;
	}	
	
	public Button getCancleButton(){
		if(cancelButton == null) {
			cancelButton = new Button("Cancel");
		}
		return cancelButton;
	}
	
	public TextField getName(){
		if(name == null) {
			name = new TextField("Name");
			name.setNullRepresentation("");
			name.addValidator(ValidatorFactory.createStringLenghtValidator(name.getCaption(), 1, 200, false));
		}
		return name;
	}
	
	private TextField getHost(){
		if(host == null) {
			host = new TextField("Host");
			host.setIcon(FontAwesome.GLOBE);
			host.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			host.setNullRepresentation("");
			host.addValidator(ValidatorFactory.createStringLenghtValidator(host.getCaption(), 1, 255, false));
		}
		return host;
	}
	
	private TextField getPort(){
		if(port == null) {
			port = new TextField("Port");
			port.setIcon(FontAwesome.PLUG);
			port.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			port.setNullRepresentation("");
			port.addValidator(ValidatorFactory.createIntegerRangeValidator(port.getCaption(), 0, 65535));
		}
		return port;
	}
	
	private TextArea getRobotDescription(){
		if(description == null) {
			description = new TextArea("Description");
			description.setIcon(FontAwesome.FILE_TEXT);
			description.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			description.setSizeFull();
			description.setNullRepresentation("");
			description.addValidator(ValidatorFactory.createStringLenghtValidator(description.getCaption(), 0, 4000, true));
		}
		return description;
	}
	
}