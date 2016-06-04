package at.jku.se.grip.ui.users;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button.ClickEvent;

import at.jku.se.grip.backend.Contact;
import at.jku.se.grip.backend.ContactService;

public class UsersController {
	
	private UsersView view = null;

    private Contact contact;
    private BeanFieldGroup<Contact> formFieldBindings;
    private ContactService service = ContactService.createDemoService();
	
	public UsersController(){		
		view = new UsersView();
		refreshContacts();
		init();
	}
	
	private void init(){
		view.getFilter().addTextChangeListener(this::filterListener);
		view.getNewContactButton().addClickListener(this::newContactListener);
		view.getContactList().addSelectionListener(this::selectionListener);
		view.getContactForm().getSaveButton().addClickListener(this::saveListener);
		view.getContactForm().getCancleButton().addClickListener(this::cancelListener);
	}
	
	private void filterListener(TextChangeEvent e) {
		refreshContacts(e.getText());
	}
	
	private void newContactListener(ClickEvent e) {
//		view.getContactForm().edit(new Contact());
		edit(new Contact());
	}
	
	private void selectionListener(SelectionEvent e) {
//		view.getContactForm().edit((Contact) view.getContactList().getSelectedRow());
		edit((Contact) view.getContactList().getSelectedRow()); 
	}
	
    public void saveListener(ClickEvent e) {
        try {
        	
//        	view.getContactForm().getFormFieldBindings().commit();
        	formFieldBindings.commit();
//        	service.save(this.view.getContactForm().getContact());
        	service.save(contact);       	
        	refreshContacts();

        } catch (FieldGroup.CommitException exception) {
            // Validation exceptions could be shown here
        }
    }
	
    public void cancelListener(ClickEvent e) {
    	view.getContactList().select(null);
    	view.getContactForm().setVisible(false);
    }
	
	public UsersView getView(){
		return view;
	}
	
	public void refreshContacts() {
        refreshContacts(view.getFilter().getValue());
    }
	
    public void refreshContacts(String stringFilter) {
    	view.getContactList().setContainerDataSource(new BeanItemContainer<>(
                Contact.class, service.findAll(stringFilter)));
    	view.getContactForm().setVisible(false);
    }
    
    public void edit(Contact contact) {
        this.contact = contact;
        if(contact != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact, view.getContactForm());
            view.getContactForm().getName().focus();
        }
        view.getContactForm().setVisible(contact != null);
    }
    
    public BeanFieldGroup<Contact> getFormFieldBindings(){
    	return formFieldBindings;
    }
    
    public Contact getContact(){
    	return contact;
    }

}
