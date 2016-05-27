package at.jku.se.grip;

import javax.servlet.annotation.WebServlet;

import org.vaadin.hezamu.canvas.Canvas;
import org.vaadin.hezamu.canvas.Canvas.CanvasMouseMoveListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Button;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import elemental.json.JsonArray;

@SuppressWarnings("serial")
@Theme("grip")
public class GripUI extends UI {
	private Canvas canvas;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GripUI.class, widgetset="at.jku.se.grip.widgetset.GripWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		final TextField name = new TextField("Type your name here:");
		final MyComponent mycomponent = new MyComponent();
		
		// Set the value from server-side
		mycomponent.setValue("Server-side value");
		
		// Process a value input by the user from the client-side
		mycomponent.addValueChangeListener(new MyComponent.ValueChangeListener() {
			
			public void valueChange() {
				Notification.show("Value: " + mycomponent.getValue());
			}
		});
		
		layout.setMargin(true);
		setContent(layout);
		//layout.addComponent(new JsLabel("Hello World!"));

		Button button = new Button("Click Me");
		button.addClickListener(e -> {
			layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
		});
		
		Button changeButton = new Button("Change Me");
		//changeButton.addClickListener(e -> mycomponent.setValue("second"));
		
		layout.addComponent(name);
		layout.addComponent(button);
		layout.addComponent(mycomponent);
		
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
	}
	
	private class MousePosition {
		int x = 0;
		int y = 0;
	}
}