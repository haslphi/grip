package at.jku.se.grip;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.annotation.WebServlet;

import org.vaadin.viritin.fields.MTable;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.common.Constants;
import at.jku.se.grip.dao.impl.UserDAO;
import at.jku.se.grip.ui.ApplicationView;

@SuppressWarnings("serial")
@Theme("grip")
public class GripUI extends UI {
	//private Canvas canvas;
	public String user;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GripUI.class, widgetset="at.jku.se.grip.widgetset.GripWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		/*
		final HorizontalLayout layout = new HorizontalLayout();
	    layout.setMargin(true);
	    layout.setSpacing(true);
	  
	    LoginScreen.buildLoginScreen(layout);
	    setContent(layout);
	    */

		setSizeFull();
		
		ApplicationView view = new ApplicationView();
		setContent(view);
		
		testing(view);
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