package at.jku.se.grip.ui.overview;

import org.vaadin.hezamu.canvas.Canvas;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import at.jku.se.grip.backend.ContactService;

@SuppressWarnings("serial")
public class OverviewView extends CustomComponent {

	private HorizontalLayout mainLayout = null;
	private HorizontalLayout canvasLayout = null;
    private FormLayout selectMenuFormLayout = null;   
    private ComboBox selectPathComboBox = null;   
	private HorizontalLayout selectDeleteButtons = null;
    private Button selectButton = null;
    private Button deleteButton = null;
    private TextField pathName = null;
	private HorizontalLayout saveCancelButtons = null;
    private Button saveButton = null;
    private Button cancelButton = null;
    private Canvas canvas = null;	
	
	private VerticalLayout left = null;
    
    ContactService service = ContactService.createDemoService();
	
	public OverviewView(){
		super();
		init();
	}
	
	private void init(){
		this.setCompositionRoot(getMainLayout());
		this.setSizeFull();
	}
	
	public HorizontalLayout getMainLayout(){
		if (mainLayout == null){
			mainLayout = new HorizontalLayout();
			mainLayout.setSizeFull();
	
			mainLayout.addComponent(getSelectMenu());
			mainLayout.addComponent(getCanvasLayout());
			
//			mainLayout.addComponent(getCanvas());
			
	        mainLayout.setExpandRatio(selectMenuFormLayout, 1);
	        mainLayout.setExpandRatio(canvasLayout, 3);
		}				
		return mainLayout;
	}
	
	public HorizontalLayout getCanvasLayout(){
		if(canvasLayout == null){
			canvasLayout = new HorizontalLayout();
			canvasLayout.setWidth("440px");
			canvasLayout.setHeight("330px");
			//canvasLayout.addComponent(getCanvas());
			//canvasLayout.addStyleName("well");
			canvasLayout.addStyleName("card");
		}
		return canvasLayout;
	}
	
	public VerticalLayout getLeft(){
		if(left == null){
	        left = new VerticalLayout();
	        left.setSizeFull();
	        
		}
		return left;
	}
	
	public FormLayout getSelectMenu(){
		if(selectMenuFormLayout == null) {
			selectMenuFormLayout = new FormLayout();
			selectMenuFormLayout.setMargin(true);
			
			selectMenuFormLayout.addComponent(getSelectPath());			
			selectMenuFormLayout.addComponent(getSelectDeleteButtons());
			selectMenuFormLayout.addComponent(getPathName());			
			selectMenuFormLayout.addComponent(getSaveCancelButtons());

		}
		return selectMenuFormLayout;
	}
	
	private ComboBox getSelectPath(){
		if(selectPathComboBox == null) {
			selectPathComboBox = new ComboBox();
			selectPathComboBox.addStyleName("vertical");
			selectPathComboBox.setInputPrompt("Select path");
		}
		return selectPathComboBox;
	}

	
	private HorizontalLayout getSelectDeleteButtons(){
		if(selectDeleteButtons == null) {
			selectDeleteButtons = new HorizontalLayout();
			selectDeleteButtons.setSpacing(true);
			selectDeleteButtons.addComponent(getSelectButton());
			selectDeleteButtons.addComponent(getDeleteButton());
		}
		return selectDeleteButtons;
	}
	
	private TextField getPathName(){
		if(pathName == null) {
			pathName = new TextField();
			pathName.setInputPrompt("Pathname");
		}
		return pathName;
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
	
	private Button getSaveButton(){
		if(saveButton == null) {
			saveButton = new Button("Save");
		}
		return saveButton;
	}	
	
	private Button getSelectButton(){
		if(selectButton == null) {
			selectButton = new Button("Select");
		}
		return selectButton;
	}
	
	private Button getDeleteButton(){
		if(deleteButton == null) {
			deleteButton = new Button("Delete");
		}
		return deleteButton;
	}	
	
	private Button getCancelButton(){
		if(cancelButton == null) {
			cancelButton = new Button("Cancel");
		}
		return cancelButton;
	}
	
	private Canvas getCanvas(){
		if(canvas == null) {
			canvas = new Canvas();
			canvas.setSizeFull();
		}
		return canvas;
	}
	
}
