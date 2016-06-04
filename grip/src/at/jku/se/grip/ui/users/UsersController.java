package at.jku.se.grip.ui.users;

import com.vaadin.event.SelectionEvent;
import at.jku.se.grip.backend.ContactService;


public class UsersController {
	
	private UsersView view = null;
    ContactService service = ContactService.createDemoService();
	
	public UsersController(){
		view = new UsersView();
		init();
	}
	
	private void init(){
		view.getContactList().addSelectionListener(this::selectionListener);
	}
	
	private void selectionListener(SelectionEvent e) {
		if(view.getContactList().getSelectedRow() != null) {
			// TODO: bind user to contact form
			view.getContactForm().setVisible(true);
		}
	}
	
	public UsersView getView(){
		return view;
	}

}
