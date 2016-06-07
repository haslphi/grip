package at.jku.se.grip.ui.drawboard;

import static at.jku.se.grip.common.Constants.VALO_BLUE;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.hezamu.canvas.Canvas;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.shared.MouseEventDetails;

import lombok.AllArgsConstructor;


public class DrawboardController {
	
	private DrawboardView view = null;
	private MousePosition currentPos = new MousePosition(0, 0);
	private Canvas canvas = null;
	private List<MousePosition> path = new ArrayList<>();
	
	public DrawboardController(){
		view = new DrawboardView();
		init();
	}
	
	private void init(){
		canvas = view.getCanvas();
		canvas.addMouseUpListener(this::canvasMouseUpListener);
		canvas.addMouseMoveListener(this::canvasMouseMoveListener);
		canvas.addAttachListener(this::canvasAttachListener);
		canvas.addDetachListener(this::canvasDetachListener);
//		canvas.setLineCap("square");
//		canvas.setLineJoin("round");
		canvas.setStrokeStyle(VALO_BLUE);
	}
	
	public DrawboardView getView(){
		return view;
	}
	
	private void canvasAttachListener(AttachEvent e) {
		if(path.size() > 0) {
			canvas.saveContext();
		}
		System.out.println("CANVAS was attached.");
	}
	
	private void canvasDetachListener(DetachEvent e) {
		if(path.size() > 0) {
			canvas.restoreContext();
		}
		System.out.println("CANVAS was detached.");
	}
	
	private void canvasMouseMoveListener(MouseEventDetails e) {
		// remember current mouse position in canvas
		currentPos.x = e.getRelativeX();
		currentPos.y = e.getRelativeY();
	}
	
	private void canvasMouseUpListener() {
		MousePosition pos = cloneCurrentPos();
		
		if(path.size() == 0) {
			canvas.beginPath();
			canvas.moveTo(pos.x, pos.y);
			canvas.setFillStyle("#000000");

			canvas.arc(pos.x, pos.y, 5, 0, 2*Math.PI, true);
			canvas.fill();
		} else {
			canvas.lineTo(pos.x, pos.y);
			canvas.stroke();
		}
		path.add(pos);
	}
	
	/**
	 * Clone the current {@link MousePosition}
	 * 
	 * @return
	 */
	private MousePosition cloneCurrentPos() {
		if(currentPos != null) {
			return new MousePosition(currentPos.x, currentPos.y);
		}
		return null;
	}

	/**
	 * Simple class to save relative mouse positions in canvas.
	 * 
	 * @author Alex
	 *
	 */
	@AllArgsConstructor
	private class MousePosition {
		protected int x = 0;
		protected int y = 0;
	}
}
