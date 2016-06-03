package at.jku.se.grip.ui.users;

import com.vaadin.client.widgets.Grid;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.backend.Contact;
import at.jku.se.grip.backend.ContactService;
import at.jku.se.grip.ui.events.LoginEvent;


public class UsersController {
	
	private UsersView view = null;
    ContactService service = ContactService.createDemoService();
	
	public UsersController(){
		view = new UsersView();
		init();
	}
	
	private void init(){
		view.getContactList().addSelectionListener(this::selectionListener);
		//view.getNewContactButton().addClickListener(this::newContact);
		//view.getContactList().addSelectionListener(this::contactList);
	}
	
	private void newContact (Button.ClickEvent event) {
		
	}
	
	private void selectionListener(SelectionEvent e) {
		if(view.getContactList().getSelectedRow() != null) {
			// TODO: bind user to contact form
			view.getContactForm().setVisible(true);
		}
	}
	
	private void contactList () {

	}
	
	public UsersView getView(){
		return view;
	}

}
