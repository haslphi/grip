package at.jku.se.grip.ui.users;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import at.jku.se.grip.beans.User;

@SuppressWarnings("serial")
public class UserView extends CustomComponent {

	private HorizontalLayout mainLayout = null;
	private HorizontalLayout actions = null;
	private VerticalLayout left = null;
	private TextField filter = null;
	private Grid userList = null;
	private Button newUser = null;
	private Button refreshButton = null;
    private UserForm userForm = null;
	
	public UserView(){
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
			
			mainLayout.addComponent(getLeft());
			mainLayout.addComponent(getUserForm());
	        mainLayout.setExpandRatio(left, 2.5f);
	        mainLayout.setExpandRatio(userForm, 1);
		}				
		return mainLayout;
	}
	
	public VerticalLayout getLeft(){
		if(left == null){
	        left = new VerticalLayout();
	        left.setSizeFull();
	        
	        left.addComponent(getActions());
	        left.addComponent(getUserList());
	        
	        left.setExpandRatio(userList, 1);
		}
		return left;
	}
	
	private HorizontalLayout getActions(){
		if(actions == null) {
			actions = new HorizontalLayout();
	        actions.setWidth("100%");
	        
	        actions.addComponent(getFilter());
	        actions.addComponent(getRefreshButton());
	        actions.addComponent(getNewUserButton());	
	        
	        actions.setExpandRatio(filter, 1);	        
		}
		return actions;
	}
	
	public TextField getFilter(){
		if(filter == null) {
			filter = new TextField();
	        filter.setWidth("100%");
	        filter.setInputPrompt("Find User...");
		}
		return filter;
	}
	
	public Button getNewUserButton(){
		if(newUser == null) {
			newUser = new Button("New contact");
			newUser.setIcon(FontAwesome.PLUS);
		}
		return newUser;
	}
	
	public Button getRefreshButton() {
		if(refreshButton == null) {
			refreshButton = new Button();
			refreshButton.setIcon(FontAwesome.REFRESH);
		}
		return refreshButton;
	}
	
	public UserForm getUserForm(){
		if(userForm == null) {
			userForm = new UserForm();
		}
		return userForm;
	}
	
	public Grid getUserList(){
		if(userList == null) {
			userList = new Grid();
			userList.setSizeFull();
			
	        userList.setContainerDataSource(new BeanItemContainer<>(User.class));
	        userList.removeAllColumns();
	        userList.addColumn("firstName");
	        userList.addColumn("lastName");
	        userList.addColumn("username");
	        userList.addColumn("email");
	        userList.setColumnOrder("firstName", "lastName", "username", "email");
	        
	        userList.setSelectionMode(Grid.SelectionMode.SINGLE);
		}
		return userList;
	}
	
}
