package at.jku.se.grip;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.annotation.WebServlet;

import org.vaadin.viritin.fields.MTable;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.common.Constants;
import at.jku.se.grip.dao.impl.UserDAO;
import at.jku.se.grip.ui.ApplicationController;
import at.jku.se.grip.ui.ApplicationView;
import at.jku.se.grip.ui.events.LoginEvent;
import at.jku.se.grip.ui.events.LogoutEvent;
import at.jku.se.grip.ui.login.LoginController;
import at.jku.se.grip.ui.users.ContactForm;
import at.jku.se.grip.ui.users.UsersController;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("grip")
public class GripUI extends UI {
	//private Canvas canvas;
	public String user;
	private LoginController loginController = null;
	private ApplicationController applicationController = null;
	private static EventBus eventBus = null;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GripUI.class, widgetset="at.jku.se.grip.widgetset.GripWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		setSizeFull();
		
		eventBus = new EventBus();
		eventBus.register(this);
		
		//LoginEvent x = new LoginEvent(true);
		//eventBus.post(x);
		
		switchToLogin();
		
		//testing(view);
		
		//testUserView();

	}
	
	public static EventBus getEventBus(){
		return eventBus;
	}
	
	@Subscribe
	public void listenLogin(LoginEvent event){
		System.out.println("Event arrived " + event.getValidLogin());
		if(event.getValidLogin()){
			switchToApplication();			
		}
	}
	
	@Subscribe
	public void listenLogout(LogoutEvent event){
		switchToLogin();	
	}
	
	private void switchToApplication(){
		applicationController = new ApplicationController();
		setContent(applicationController.getView());
		loginController = null;
	}
	
	private void switchToLogin(){
		loginController = new LoginController();
		setContent(loginController.getView());
		applicationController = null;
	}
	
	private void testUserView(){
		UsersController usersController= new UsersController();
		setContent(usersController.getView());
	}
	
	public static void testing(ApplicationView view) {
		MTable<User> userTable = new MTable<User>();
		
		//userTable.setBeans(populatePersonTable());
		List<User> users = getUserByDAO();
		userTable.setBeans(users);
		
		view.getMainComponentContainer().addComponent(userTable);
	}
	
	private static List<User> getUserByDAO() {
		UserDAO userDao = new UserDAO();
		return userDao.findAll();
	}
	
	private  static List<User> populatePersonTable() {
		List<User> result = new ArrayList<>();
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(Constants.DEFAULT_PERSISTENCE_IDENTIFIER);
		EntityManager em = factory.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		//Root<User> root = cq.from(User.class);
		//cq.where(cb.like(root.get("id.id"), "2345"));
		cq.where();
		TypedQuery<User> query = em.createQuery(cq);

		result = query.getResultList();
		if(!result.isEmpty()) {
			User user = result.get(0);
			user.setPassword("testPwd2");

			try {
				em.getTransaction().begin();
				em.merge(user);
				em.getTransaction().commit();
			} catch (Exception e) {
				if(em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				e.printStackTrace();
			}
		}

		return result;
	}

}