package at.jku.se.grip;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.vaadin.hezamu.canvas.Canvas;
import org.vaadin.hezamu.canvas.Canvas.CanvasMouseMoveListener;
import org.vaadin.viritin.fields.MTable;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import at.jku.se.grip.beans.User;
import elemental.json.JsonArray;

public class TMPBackup {
	private static final String PERSISTENCE_UNIT_NAME = "people";
	private static EntityManagerFactory factory;
	private Canvas canvas;
	
	public TMPBackup() {
		MTable<User> userTable = new MTable<User>();
		final VerticalLayout layout = new VerticalLayout();
		
		JavaScript.getCurrent().addFunction("com.example.foo.myfunc",
				new JavaScriptFunction() {
			@Override
			public void call(JsonArray arguments) {
				String message = arguments.getString(0);
				int    value   = (int) arguments.getNumber(1);
				Notification.show("Message: " + message +
						", value: " + value);
			}
		});

		Link link = new Link("Send Message", new ExternalResource(
				"javascript:com.example.foo.myfunc(prompt('Message'), 42)"));
		layout.addComponent(link);

		// Instantiate the component and add it to your UI
		layout.addComponent(canvas = new Canvas());

		// Draw a 20x20 filled rectangle with the upper left corner
		// in coordinate 10,10. It will be filled with the default
		// color which is black.
		//canvas.fillRect(10, 10, 20, 20);

		boolean pathInWork = false;
		MousePosition position = new MousePosition();
		canvas.addMouseMoveListener(new CanvasMouseMoveListener() {
			@Override
			public void onMove(MouseEventDetails mouseDetails) {
				System.out.println("Mouse moved relative at "
						+ mouseDetails.getRelativeX() + ","
						+ mouseDetails.getRelativeY());
				position.x = mouseDetails.getRelativeX();
				position.y = mouseDetails.getRelativeY();
				System.out.println("Mouse moved at "
						+ mouseDetails.getClientX() + ","
						+ mouseDetails.getClientY());
			}
		});
		canvas.addMouseDownListener(() -> {
			canvas.beginPath();
			canvas.moveTo(0, 0);
		});
		canvas.addMouseUpListener(() -> {
			canvas.lineTo(position.x, position.y);
			canvas.stroke();
		});

		userTable.setBeans(populatePersonTable());
	}

	private class MousePosition {
		int x = 0;
		int y = 0;
	}
	public static List<User> populatePersonTable() {
		List<User> result = new ArrayList<>();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		cq.where();
		TypedQuery<User> query = em.createQuery(cq);

		result = query.getResultList();
		if(!result.isEmpty()) {
			User user = result.get(0);
			user.setPassword("testPwd");

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

		/*
	CriteriaBuilder cb = em.getCriteriaBuilder();
	// Query for a List of objects.
	CriteriaQuery<User> cq = cb.createQuery(User.class);
	Root<User> e = cq.from(User.class);
	cq.where(cb.like(e.get("lastName"), "Knopf_3%"));
	TypedQuery<User> query = em.createQuery(cq);
	return query.getResultList();
		 */
	}
}
