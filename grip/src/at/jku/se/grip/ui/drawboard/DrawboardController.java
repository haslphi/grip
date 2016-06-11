package at.jku.se.grip.ui.drawboard;

import static at.jku.se.grip.common.Constants.VALO_BLUE;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.vaadin.hezamu.canvas.Canvas;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Button;

import lombok.AllArgsConstructor;

public class DrawboardController {

	private static final String STARTPOINT_COLOR = "#17d2d7";

	private DrawboardView view = null;
	private MousePosition currentPos = new MousePosition(0, 0);
	private Canvas canvas = null;
	private List<MousePosition> path = new ArrayList<>();
	private JSONObject jsonPath = new JSONObject();
	//private JSONArray pathJason = new JSONArray();

	public DrawboardController() {
		view = new DrawboardView();
		init();
	}

	private void init() {
		canvas = view.getCanvas();
		canvas.addMouseUpListener(this::canvasMouseUpListener);
		canvas.addMouseMoveListener(this::canvasMouseMoveListener);
		canvas.addAttachListener(this::canvasAttachListener);
		// canvas.setLineCap("square");
		// canvas.setLineJoin("round");
		canvas.setStrokeStyle(VALO_BLUE);
		view.getSaveButton().addClickListener(this::saveListener);
	}

	private void saveListener(Button.ClickEvent e) {
		jsonPath.clear();
		canvasToJson();

		if (jsonPath.isEmpty()) {
			System.out.print("pathJason = Empty" + "\n");
		} else {
			System.out.print(jsonPath + "\n");
		}
	}

	public DrawboardView getView() {
		return view;
	}

	/**
	 * Restore the context if component is (re-)attached.
	 * 
	 * @param e
	 */
	private void canvasAttachListener(AttachEvent e) {
		if (path.size() > 0) {
			int pos = 0;

			// loses the stroke color if component is detached, therefore we
			// have to restore it
			canvas.setStrokeStyle(VALO_BLUE);
			canvas.beginPath();
			canvas.moveTo(path.get(pos).x, path.get(pos).y);
			canvas.setFillStyle(STARTPOINT_COLOR);

			canvas.arc(path.get(pos).x, path.get(pos).y, 5, 0, 2 * Math.PI,
					true);
			canvas.fill();

			pos++;

			while (pos < path.size()) {
				canvas.lineTo(path.get(pos).x, path.get(pos).y);
				canvas.stroke();
				pos++;
			}
		}
	}

	private void canvasMouseMoveListener(MouseEventDetails e) {
		// remember current mouse position in canvas
		currentPos.x = e.getRelativeX();
		currentPos.y = e.getRelativeY();
	}

	private void canvasMouseUpListener() {
		MousePosition pos = cloneCurrentPos();

		if (path.size() == 0) {
			canvas.beginPath();
			canvas.moveTo(pos.x, pos.y);
			canvas.setFillStyle(STARTPOINT_COLOR);

			canvas.arc(pos.x, pos.y, 5, 0, 2 * Math.PI, true);
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
		if (currentPos != null) {
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

	private void canvasToJson() {
		double xOld = path.get(0).x;
		double yOld = 660 - path.get(0).y;
		double xNew = path.get(0).x;
		double yNew = 660 - path.get(0).y;
		double distance = 0;
		double orientationOld = 0;
		double orientationNew = 0;
		double turn = 0;
		JSONArray jsonArray = new JSONArray();

		for (int i = 1; i < path.size(); i++) {
			orientationOld = orientationNew;
			xOld = xNew;
			yOld = yNew;
			xNew = path.get(i).x;
			yNew = 660 - path.get(i).y;

			// The difference between old orientation and new orientation is the
			// turn to make
			orientationNew = Math.toDegrees(Math
					.atan2(yNew - yOld, xNew - xOld));
			if (orientationNew < 0) {
				orientationNew = orientationNew + 360;
			}

			// depanding on the shortest degrees the roboter should turn left or
			// right
			if (orientationNew >= orientationOld) {
				turn = orientationNew - orientationOld;
				if (turn > 180) {
					turn = (360 - turn) * -1;
				}
			} else {
				turn = (orientationOld - orientationNew) * -1;
				if (turn < -180) {
					turn = turn + 360;
				}
			}

			// The pixels between old postion and new position are the distance
			// to drive
			distance = Math.round(Math.sqrt(Math.pow(xNew - xOld, 2)
					+ Math.pow(yNew - yOld, 2)));
			distance = Math.round(distance);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("degree", (int) Math.round(turn));
			jsonObj.put("distance", (int) Math.round(distance * 0.25));
			jsonArray.add(jsonObj);
			jsonPath.put("path", jsonArray);

			// System.out.print("turn: " + turn + "\n");
			// System.out.print("distance: " + (int) distance + "\n");
		}

	}
}
