package at.jku.se.grip.ui.users;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.common.NotificationPusher;
import at.jku.se.grip.dao.DaoServiceRegistry;

public class UserController {
	
	private UserView view = null;

    private User user;
    private BeanFieldGroup<User> formFieldBindings;
	
	public UserController(){		
		view = new UserView();
		refreshUsers();
		init();
	}
	
	private void init(){
		view.getFilter().addTextChangeListener(this::filterListener);
		view.getNewUserButton().addClickListener(this::newUserListener);
		view.getUserList().addSelectionListener(this::selectionListener);
		view.getUserForm().getSaveButton().addClickListener(this::saveListener);
		view.getUserForm().getCancleButton().addClickListener(this::cancelListener);
	}
	
	private void filterListener(TextChangeEvent e) {
		refreshUser(e.getText());
	}
	
	private void newUserListener(ClickEvent e) {
		edit(new User());
	}
	
	private void selectionListener(SelectionEvent e) {
		edit((User) view.getUserList().getSelectedRow()); 
	}
	
    public void saveListener(ClickEvent e) {
        try {
        	formFieldBindings.commit();
        	DaoServiceRegistry.getUserDAO().save(user);
        	refreshUsers();

        } catch (FieldGroup.CommitException exception) {
            // Validation exceptions could be shown here
        	NotificationPusher.showValidationViolatedNotification(Page.getCurrent());
        }
    }
	
    public void cancelListener(ClickEvent e) {
    	view.getUserList().select(null);
    	view.getUserForm().setVisible(false);
    }
	
	public UserView getView(){
		return view;
	}
	
	/**
	 * Refresh the grid without any.
	 * 
	 * @param filter
	 */
	public void refreshUsers() {
        refreshUser(view.getFilter().getValue());
    }
	
	/**
	 * Refresh the grid with the given filter.
	 * 
	 * @param filter
	 */
    public void refreshUser(String filter) {
    	List<User> beans = DaoServiceRegistry.getUserDAO().findByCriteria(createFilterCriteria(filter));
    	view.getUserList().setContainerDataSource(new BeanItemContainer<>(
                User.class, beans));
    	view.getUserForm().setVisible(false);
    }
    
    /**
     * Create a factory for all current visible columns and the give filter string.
     * 
     * @param filter 
     * @return
     */
    private CriteriaFactory createFilterCriteria(String filter) {
    	CriteriaFactory factory = CriteriaFactory.create();
    	if(StringUtils.isNotBlank(filter)) {
    		view.getUserList().getColumns().stream().forEach(c -> {
    			if(!c.isHidden()) {
    				factory.orLike(c.getPropertyId().toString(), filter);
    			}
    		});
    	}
    	return factory;
    }
    
    /**
     * Set the user form visible.<br>
     * Set the user to the form.<br>
     * Make the current selected user editable.
     * 
     * @param user
     */
    public void edit(User user) {
        this.user = user;
        
        if(user != null) {
            // Bind the properties of the user POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(user, view.getUserForm());
            view.getUserForm().getFirstName().focus();
        }
        view.getUserForm().setVisible(user != null);
    }
    
    public BeanFieldGroup<User> getFormFieldBindings(){
    	return formFieldBindings;
    }
}
