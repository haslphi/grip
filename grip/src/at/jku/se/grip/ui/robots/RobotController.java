package at.jku.se.grip.ui.robots;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.beans.Header;
import at.jku.se.grip.beans.Robot;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.common.NotificationPusher;
import at.jku.se.grip.common.RobotUtilities;
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
		view.getRefreshButton().addClickListener(this::refreshButtonListener);
		view.getNewBeanButton().addClickListener(this::newBeanListener);
		view.getBeanList().addSelectionListener(this::selectionListener);
		view.getBeanForm().getSaveButton().addClickListener(this::saveListener);
		view.getBeanForm().getCancelButton().addClickListener(this::cancelListener);
		view.getBeanForm().getTestConnectionButton().addClickListener(this::testConnectionButtonListener);
		view.getBeanForm().getHistoryButton().addClickListener(this::historyButtonListener);
	}
	
	private void filterListener(TextChangeEvent e) {
		refreshBeans(e.getText());
	}
	
	private void refreshButtonListener(ClickEvent e) {
		refreshBeans();
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
    
    private void historyButtonListener(ClickEvent e) {
    	Robot bean = (Robot) view.getBeanList().getSelectedRow();
    	
    	if(bean != null) {
    		final Window window = new Window("History of " + bean.getName());
    		window.setWidth(800.0f, Unit.PIXELS);
    		window.setHeight(600.0f, Unit.PIXELS);
    		
    		final HorizontalLayout content = new HorizontalLayout();
    		content.setSpacing(true);
    		
    		Button changeToVersion = new Button("Change to...", FontAwesome.ARROW_RIGHT);
    		changeToVersion.addStyleName(ValoTheme.BUTTON_PRIMARY);
    		
    		Grid beanList = new Grid();
			beanList.setSizeFull();
			
	        beanList.setContainerDataSource(new BeanItemContainer<>(Robot.class));
	        beanList.removeAllColumns();
	        beanList.addColumn("name");
	        beanList.addColumn("host");
	        beanList.addColumn("port").setWidth(80.0);
	        beanList.addColumn("header").setHeaderCaption("Date").setConverter(new Converter<String, Header>(){

				/**
				 * 
				 */
				private static final long serialVersionUID = 585564464790679029L;

				@Override
				public Header convertToModel(String value, Class<? extends Header> targetType, Locale locale)
						throws com.vaadin.data.util.converter.Converter.ConversionException {
					return null;
				}

				@Override
				public String convertToPresentation(Header value, Class<? extends String> targetType, Locale locale)
						throws com.vaadin.data.util.converter.Converter.ConversionException {
					SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					return formater.format(value.getModifiedDate() != null ? value.getModifiedDate() : value.getCreatedDate());
				}

				@Override
				public Class<Header> getModelType() {
					return Header.class;
				}

				@Override
				public Class<String> getPresentationType() {
					return String.class;
				}
				
			});
	        
	        beanList.setSelectionMode(Grid.SelectionMode.SINGLE);
	        List<Robot> beans = DaoServiceRegistry.getRobotDAO().findByCriteriaWithHistory(CriteriaFactory
	        		.create()
	        		.andEquals("id.id", bean.getId().getId())
	        		.descOrder("id.history"));
	        if(beans != null && !beans.isEmpty()) {
	        	Robot last = beans.get(0);	// last in history but first in list (desc order)
	        	if(last.getVersion() == bean.getVersion()) {
	        		beans.remove(last);
	        	}
	        }
	        
	        beanList.setContainerDataSource(new BeanItemContainer<>(
	                Robot.class, beans));
	        
	        changeToVersion.addClickListener(l -> {
	        	if(beanList.getSelectedRow() != null) {
	        		Robot selected = (Robot) beanList.getSelectedRow();
	        		bean.copyAttributesFlat(selected);
	        		DaoServiceRegistry.getRobotDAO().save(bean);
	        		window.close();
	        		refreshBeans();
	        	}
	        });
	        
	        content.addComponent(beanList);
	        content.setExpandRatio(beanList, 1f);
	        content.addComponent(changeToVersion);
	        content.setSizeFull();
    		
    		window.setContent(content);
    		window.center();
    		window.setModal(true);
    		UI.getCurrent().addWindow(window);
    	}
    }
    
    private void testConnectionButtonListener(ClickEvent e) {
    	if(view.getBeanList().getSelectedRow() != null) {
    		boolean connection = RobotUtilities.testConnection((Robot) view.getBeanList().getSelectedRow());
    		if(connection) {
    			NotificationPusher.showCustomInformation(Page.getCurrent(), "Connection succesfull!", 3000, null);
    		} else {
    			NotificationPusher.showCustomError(Page.getCurrent(), "Connection failed!", "Robot can not be reached.", 3000);
    		}
    	} else {
    		NotificationPusher.showCustomWarning(Page.getCurrent(),
        			"No robot selected!",
        			"You hav to select a robot before a connection can be tested.",
        			5000,
        			Position.TOP_RIGHT);
    	}
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
    		// exclude hidden columns and the port column from searching
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
