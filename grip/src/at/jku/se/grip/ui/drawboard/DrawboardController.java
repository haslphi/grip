package at.jku.se.grip.ui.drawboard;



import static at.jku.se.grip.common.Constants.CANVAS_HEIGHT;
import static at.jku.se.grip.common.Constants.VALO_BLUE;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vaadin.hezamu.canvas.Canvas;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.beans.Drawing;
import at.jku.se.grip.beans.Robot;
import at.jku.se.grip.beans.Robot.RobotPersistenceEvent;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.common.LogUtilities;
import at.jku.se.grip.common.NotificationPusher;
import at.jku.se.grip.common.RobotUtilities;
import at.jku.se.grip.dao.DaoServiceRegistry;
import at.jku.se.grip.enums.FloorType;
import lombok.AllArgsConstructor;
import lombok.val;

public class DrawboardController {

	private static final String STARTPOINT_COLOR = "#17d2d7";

	private DrawboardView view = null;
	private MousePosition currentPos = new MousePosition(0, 0);
	private Canvas canvas = null;
	private List<MousePosition> path = new ArrayList<>();
	private Drawing selected = null;

	public DrawboardController(Drawing initialDrawing) {
		view = new DrawboardView();
		selected = initialDrawing;
		init();
	}
	
	private void init() {
		canvas = view.getCanvas();
		
		// add Listener
		canvas.addMouseUpListener(this::canvasMouseUpListener);
		canvas.addMouseMoveListener(this::canvasMouseMoveListener);
		canvas.addAttachListener(this::canvasAttachListener);
		canvas.setStrokeStyle(VALO_BLUE);
		view.getSaveButton().addClickListener(this::saveListener);
		view.getSelectButton().addClickListener(this::selectListener);
		view.getClearButton().addClickListener(this::clearListener);
		view.getDeleteButton().addClickListener(this::deleteListener);
		view.getExecuteButton().addClickListener(this::executeListener);
		view.getStopButton().addClickListener(this::stopListener);
		
		// fill ComboBoxes
		refreshPaths(false);
		refreshRobots(false);
		
		// register to the eventbus
		GripUI.getEventBus().register(this);
		
		// set initial drawing
		if(selected != null && !selected.isNew()) {
			view.getSelectPathComboBox().setValue(selected);
			selectDrawing();
		}
	}
	
	public DrawboardView getView() {
		return view;
	}
	
	/**
	 * Refresh the drawing beans in the path combobox.
	 */
	private void refreshPaths(boolean keepSelected) {
		refreshPaths(keepSelected, null);
	}
	
	/**
	 * Refresh the drawing beans in the path combobox.
	 */
	private void refreshPaths(boolean keepSelected, Drawing select) {
		Object selected = view.getSelectRobotComboBox().getValue();
		List<Drawing> beans = DaoServiceRegistry.getDrawingDAO().findByCriteria(CriteriaFactory.create().ascOrder("name"));
    	view.getSelectPathComboBox().setContainerDataSource(new BeanItemContainer<>(
                Drawing.class, beans));
    	if(beans.contains(select)) {
    		view.getSelectPathComboBox().setValue(select);
    	} else if(keepSelected) {
    		view.getSelectRobotComboBox().select(selected);	
    	}
	}
	
	/**
	 * Refresh the robot beans in the robot combobox.
	 */
	private void refreshRobots(boolean keepSelected) {
		Object selected = view.getSelectRobotComboBox().getValue();
		List<Robot> beans = DaoServiceRegistry.getRobotDAO().findByCriteria(CriteriaFactory.create());
    	view.getSelectRobotComboBox().setContainerDataSource(new BeanItemContainer<>(
                Robot.class, beans));
    	if(keepSelected) {
    		view.getSelectRobotComboBox().select(selected);	
    	}
	}
	
	/**
	 * Listen to {@link RobotPersistenceEvent}s of {@link Robot} beans.
	 * 
	 * @param bean
	 */
	@Subscribe
	public void listenRobotPersistenceAction(RobotPersistenceEvent bean) {
		refreshRobots(true);
	}
	
	private void selectListener(ClickEvent e) {
		selectDrawing();
	}
	
	private void clearListener(ClickEvent e) {
		selected = null;
		view.getSelectPathComboBox().setEnabled(true);
		view.getSelectButton().setEnabled(true);
		view.getDeleteButton().setEnabled(true);
		view.getSaveButton().setEnabled(true);
		view.getPathName().setEnabled(true);
		view.getPathName().setValue("");
		
		path = new ArrayList<>();
		
		canvas.clear();
	}
	
	private void deleteListener(ClickEvent e) {
		Drawing value = (Drawing) view.getSelectPathComboBox().getValue();
		
		// check if path is chosen
		if(value != null && !value.isNew()) {
			DaoServiceRegistry.getDrawingDAO().delete(value);
			refreshPaths(false);
		} else {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "No path is chosen.", "Choose a path!", 2000, Position.TOP_CENTER);
			return;
		}
	}
	
	private void stopListener(Button.ClickEvent e) {
		Robot bean = (Robot) view.getSelectRobotComboBox().getValue();
		
		boolean connected = false;

		try (Socket clientSocket = new Socket()) {
			String host = bean.getHost();
			Integer port = bean.getPort();
			clientSocket.connect(new InetSocketAddress(host, port == null ? 80 : port), 3000);
			clientSocket.setSoTimeout(3000);

			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeBytes("STOP");
			String response = reader.readLine();
			//				while (!response.contains(".")) {
			//					response = reader.readLine();
			//					System.out.println(response);
			//				}
			reader.close();
			connected = StringUtils.isNotBlank(response);
			LogUtilities.log().debug("Socket Response: " + response);
		} catch (SocketTimeoutException ex) {
			System.out.println("Timeout exeption!");
		} catch (IOException ex) {
			System.out.println("IOException occured!");
		} catch (Exception ex) {
			System.out.println("Server Connection failed!");
		}
	}
	
	private void executeListener(Button.ClickEvent e) {
		Robot robot = (Robot) view.getSelectRobotComboBox().getValue();
		FloorType type = (FloorType) view.getFloorTypeComboBox().getValue();
		
		// check if path is not empty
		if(path == null || path.size() <= 0) {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "Draw or select a path first.", "Path missing!", 2000, Position.TOP_CENTER);
			return;
		}
		// check if robot is selected
		if(robot == null || robot.isNew()) {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "Select a robot first.", "Robot missing!", 2000, Position.TOP_CENTER);
			return;
		}
		// check if floor type is not empty
		if(type == null) {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "Select a floor type first.", "Floor type missing!", 2000, Position.TOP_CENTER);
			return;
		}
		
		// check connection
		boolean connection = RobotUtilities.testConnection(robot);
		if(!connection) {
			NotificationPusher.showCustomError(Page.getCurrent(), "Connection failed!", "Robot can not be reached.", 3000);
			return;
		}
		
		//Socket clientSocket = null;
		try (Socket clientSocket = new Socket(robot.getHost(), robot.getPort() == null ? 80 : robot.getPort())) {
			//				String urlPath = "GET /setJson?json=";
			//				String json = "{\"sequence\":[{\"left\":-0.5,\"right\":-0.5},{\"left\":0.5,\"right\":0.5}]}";
			String json = pathListToJsonDirections(false).toJSONString();
			LogUtilities.log().debug(json);
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			//BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeBytes(json);
			//				outToServer.writeBytes(urlPath + json);
			//				String response = "";
			//				while (!response.contains(".")) {
			//					response = reader.readLine();
			//					System.out.println(response);
			//				}
			//				reader.close();
			//				System.out.println(response);

			//				response = reader.readLine();
			//				System.out.println(response);
			//outToServer.writeBytes(pathListToJsonDirections(false).toJSONString());

		} catch (IOException ex) {
			System.out.println("IOException occured!");
		} catch (Exception ex) {
			System.out.println("Server Connection failed!");
		}
	}
	
	/**
	 * Convert path to json and save in DB.
	 * 
	 * @param e
	 */
	private void saveListener(Button.ClickEvent e) {
		// path name must not be blank
		if(StringUtils.isBlank(view.getPathName().getValue())) {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "Insert a path name.", "Name is missing!", 2000, Position.TOP_CENTER);
			return;
		}
		// check if path is not empty
		if(path == null || path.size() <= 0) {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "Draw or select a path first.", "Path missing!", 2000, Position.TOP_CENTER);
			return;
		}
		
		if(selected == null) {
			JSONObject directions = pathListToJsonDirections(true);
			JSONObject jsonPath = pathListToJsonPath();
			if(jsonPath != null && !jsonPath.isEmpty()) {
				Drawing drawing = new Drawing(view.getPathName().getValue(),
						jsonPath.toJSONString(),
						((Integer) directions.get("distance")).intValue());
				drawing  = DaoServiceRegistry.getDrawingDAO().save(drawing);

				view.getSelectPathComboBox().setEnabled(true);
				view.getSelectButton().setEnabled(true);
				view.getDeleteButton().setEnabled(true);
				view.getSaveButton().setEnabled(true);
				view.getPathName().setEnabled(true);
				view.getPathName().setValue("");
				
				// set drawing to combobox and select it
				selected = drawing;
				refreshPaths(false, selected);
				selectDrawing();
			}
		}
	}

	/**
	 * Restore the context if component is (re-)attached.
	 * 
	 * @param e
	 */
	private void canvasAttachListener(AttachEvent e) {
		drawPath(path);
	}
	
	/**
	 * Select the current (in the ComboBox) chosen drawing and set it to the canvas.
	 */
	private void selectDrawing() {
		selected = (Drawing) view.getSelectPathComboBox().getValue();
		
		// check if path is selected
		if(selected != null && !selected.isNew()) {
			view.getSelectPathComboBox().setEnabled(false);
			view.getSelectButton().setEnabled(false);
			view.getDeleteButton().setEnabled(false);
			view.getSaveButton().setEnabled(false);
			view.getPathName().setEnabled(false);
			
			drawPath(path = jsonPathToPathList(selected.getJsonPath()));
		} else {
			NotificationPusher.showCustomWarning(Page.getCurrent(), "No path is chosen.", "Choose a path!", 2000, Position.TOP_CENTER);
			return;
		}
	}
	
	/**
	 * Set the Drawing as the chosen one in the ComboBox and the Canvas.
	 */
	public void setDrawing(Drawing bean) {
		if(bean != null && !bean.isNew()) {
			selected = bean;
			view.getSelectPathComboBox().setValue(selected);
			selectDrawing();
		}
	}
	
	/**
	 * Draw all lines defined by the {@link MousePosition}s in the list.
	 * 
	 * @param path
	 */
	private void drawPath(List<MousePosition> path) {
		if (path != null && path.size() > 0) {
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
		if(selected == null || selected.isNew()) {
			MousePosition pos = null;
			try {
				pos = currentPos.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}

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

			view.getSelectPathComboBox().setEnabled(false);
			view.getSelectButton().setEnabled(false);
			view.getDeleteButton().setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject pathListToJsonPath() {
		JSONObject jsonPath = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for(val pos : path) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("x", pos.x);
			jsonObj.put("y", pos.y);
			jsonArray.add(jsonObj);
		}
		jsonPath.put("path", jsonArray);
		
		return jsonPath;
	}
	
	@SuppressWarnings("unchecked")
	private List<MousePosition> jsonPathToPathList(String jsonPathString) {
		JSONParser parser = new JSONParser();
		JSONObject jsonPath = null;
		JSONArray jsonArray = null;
		List<MousePosition> path = new ArrayList<>();
		try {
			jsonPath = (JSONObject) parser.parse(jsonPathString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(jsonPath != null) {
			jsonArray = (JSONArray) jsonPath.get("path");
			
			if(jsonArray != null) {
				jsonArray.forEach(e -> {
					JSONObject jsonObj = (JSONObject) e;
					path.add(new MousePosition(((Long) jsonObj.get("x")).intValue(), ((Long) jsonObj.get("y")).intValue()));
				});
			}
		}
		
		return path;
	}

	@SuppressWarnings("unchecked")
	private JSONObject pathListToJsonDirections(boolean calcDistanceOnly) {
		double xOld = path.get(0).x;
		double yOld = CANVAS_HEIGHT - path.get(0).y;
		double xNew = path.get(0).x;
		double yNew = CANVAS_HEIGHT - path.get(0).y;
		double distance = 0;
		double orientationOld = 0;
		double orientationNew = 0;
		double turn = 0;
		JSONObject jsonDirections = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		int distanceOnly = 0;

		for (int i = 1; i < path.size(); i++) {
			orientationOld = orientationNew;
			xOld = xNew;
			yOld = yNew;
			xNew = path.get(i).x;
			yNew = CANVAS_HEIGHT - path.get(i).y;

			// The difference between old orientation and new orientation is the
			// turn to make
			orientationNew = Math.toDegrees(Math
					.atan2(yNew - yOld, xNew - xOld));
			if (orientationNew < 0) {
				orientationNew = orientationNew + 360;
			}

			// depending on the shortest degrees the roboter should turn left or
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

			// The pixels between old position and new position are the distance
			// to drive
			distance = Math.round(Math.sqrt(Math.pow(xNew - xOld, 2)
					+ Math.pow(yNew - yOld, 2)));
			distance = Math.round(distance);

			if(!calcDistanceOnly) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("degree", (int) Math.round(turn));
				jsonObj.put("distance", (int) Math.round(distance * 0.25));
				jsonArray.add(jsonObj);
			} else {
				distanceOnly = distanceOnly + (int) Math.round(distance * 0.25);
			}
		}
		if(!calcDistanceOnly) {
			jsonDirections.put("floorType", view.getFloorTypeComboBox().getValue().toString());
			jsonDirections.put("directions", jsonArray);
		} else {
			jsonDirections.put("distance", distanceOnly);
		}
		return jsonDirections;
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
		
		@Override
		protected MousePosition clone() throws CloneNotSupportedException {
			return new MousePosition(currentPos.x, currentPos.y);
		}
	}
}
