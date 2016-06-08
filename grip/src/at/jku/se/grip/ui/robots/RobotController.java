package at.jku.se.grip.ui.robots;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;

import at.jku.se.grip.beans.Robot;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.common.NotificationPusher;
import at.jku.se.grip.dao.DaoServiceRegistry;

public class RobotController {
	
	private RobotView view = null;

    private Robot user;
    private BeanFieldGroup<Robot> formFieldBindings;
	
	public RobotController(){		
		view = new RobotView();
		refreshBeans();
		init();
	}
	
	private void init(){
		view.getFilter().addTextChangeListener(this::filterListener);
		view.getNewBeanButton().addClickListener(this::newBeanListener);
		view.getBeanList().addSelectionListener(this::selectionListener);
		view.getBeanForm().getSaveButton().addClickListener(this::saveListener);
		view.getBeanForm().getCancleButton().addClickListener(this::cancelListener);
	}
	
	private void filterListener(TextChangeEvent e) {
		refreshBeans(e.getText());
	}
	
	private void newBeanListener(ClickEvent e) {
		edit(new Robot());
	}
	
	private void selectionListener(SelectionEvent e) {
		edit((Robot) view.getBeanList().getSelectedRow()); 
	}
	
    public void saveListener(ClickEvent e) {
        try {
        	formFieldBindings.commit();
        	DaoServiceRegistry.getRobotDAO().save(user);
        	refreshBeans();

        } catch (FieldGroup.CommitException exception) {
            // Validation exceptions could be shown here
        	NotificationPusher.showValidationViolatedNotification(Page.getCurrent());
        }
    }
	
    public void cancelListener(ClickEvent e) {
    	view.getBeanList().select(null);
    	view.getBeanForm().setVisible(false);
    }
	
	public RobotView getView(){
		return view;
	}
	
	/**
	 * Refresh the grid without any.
	 * 
	 * @param filter
	 */
	public void refreshBeans() {
        refreshBeans(view.getFilter().getValue());
    }
	
	/**
	 * Refresh the grid with the given filter.
	 * 
	 * @param filter
	 */
    public void refreshBeans(String filter) {
    	List<Robot> beans = DaoServiceRegistry.getRobotDAO().findByCriteria(createFilterCriteria(filter));
    	view.getBeanList().setContainerDataSource(new BeanItemContainer<>(
                Robot.class, beans));
    	view.getBeanForm().setVisible(false);
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
    		view.getBeanList().getColumns().stream().forEach(c -> {
    			if(!c.isHidden() && !"port".equalsIgnoreCase(c.getPropertyId().toString())) {
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
    public void edit(Robot user) {
        this.user = user;
        
        if(user != null) {
            // Bind the properties of the user POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(user, view.getBeanForm());
            view.getBeanForm().getName().focus();
        }
        view.getBeanForm().setVisible(user != null);
    }
    
    public BeanFieldGroup<Robot> getFormFieldBindings(){
    	return formFieldBindings;
    }
}
