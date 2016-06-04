package at.jku.se.grip.ui.users;

import at.jku.se.grip.backend.Contact;
import at.jku.se.grip.backend.ContactService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class UsersView extends CustomComponent {

	private HorizontalLayout mainLayout = null;
	private HorizontalLayout actions = null;
	private VerticalLayout left = null;
	private TextField filter = null;
	private Grid contactList = null;
	private Button newContact = null;
    private ContactForm contactForm = null;
    
    ContactService service = ContactService.createDemoService();
	
	public UsersView(){
		super();
		init();
	}
	
	private void init(){
		this.setCompositionRoot(getMainLayout());
		this.setSizeFull();
		refreshContacts();
	}
	
	public HorizontalLayout getMainLayout(){
		if (mainLayout == null){
			mainLayout = new HorizontalLayout();
			mainLayout.setSizeFull();
			
			mainLayout.addComponent(getLeft());
			mainLayout.addComponent(getContactForm());
	        mainLayout.setExpandRatio(left, 5);
	        mainLayout.setExpandRatio(contactForm, 1);
		}				
		return mainLayout;
	}
	
	public VerticalLayout getLeft(){
		if(left == null){
	        left = new VerticalLayout();
	        left.setSizeFull();
	        
	        left.addComponent(getActions());
	        left.addComponent(getContactList());
	        
	        left.setExpandRatio(contactList, 1);
		}
		return left;
	}
	
	private HorizontalLayout getActions(){
		if(actions == null) {
			actions = new HorizontalLayout();
	        actions.setWidth("100%");
	        
	        actions.addComponent(getFilter());
	        actions.addComponent(getNewContactButton());	        
	        
	        actions.setExpandRatio(filter, 1);	        
		}
		return actions;
	}
	
	private TextField getFilter(){
		if(filter == null) {
			filter = new TextField();
	        filter.setWidth("100%");
		}
		return filter;
	}
	
	public Button getNewContactButton(){
		if(newContact == null) {
			newContact = new Button("New contact");
		}
		return newContact;
	}
	
	public ContactForm getContactForm(){
		if(contactForm == null) {
			contactForm = new ContactForm();
		}
		return contactForm;
	}
	
	public Grid getContactList(){
		if(contactList == null) {
			contactList = new Grid();
			contactList.setSizeFull();
			
	        contactList.setContainerDataSource(new BeanItemContainer<>(Contact.class));
	        contactList.setColumnOrder("firstName", "lastName", "email");
	        contactList.removeColumn("id");
	        contactList.removeColumn("birthDate");
	        contactList.removeColumn("phone");
	        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		}
		return contactList;
	}
	
    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        contactList.setContainerDataSource(new BeanItemContainer<>(
                Contact.class, service.findAll(stringFilter)));
        contactForm.setVisible(false);
    }
}
