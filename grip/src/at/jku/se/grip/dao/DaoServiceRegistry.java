package at.jku.se.grip.dao;

import java.util.UUID;

import at.jku.se.grip.dao.impl.DrawingDAO;
import at.jku.se.grip.dao.impl.NoteDAO;
import at.jku.se.grip.dao.impl.RobotDAO;
import at.jku.se.grip.dao.impl.UserDAO;

public class DaoServiceRegistry {
	private static IUserDAO userDAO;
	private static IRobotDAO robotDAO;
	private static IDrawingDAO drawinDAO;
	private static INoteDAO noteDAO;
	
	/**
	 * Generate a uuid for the (composite) pk
	 * 
	 * @return 32 char long alpha-numeric string
	 */
	public static String provideUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * @return instance of the {@link IUserDAO}
	 */
	public static IUserDAO getUserDAO(){
		if(userDAO == null) {
			userDAO = new UserDAO();
		}

		return userDAO;
	}
	
	/**
	 * @return instance of the {@link IRobotDAO}
	 */
	public static IRobotDAO getRobotDAO(){
		if(robotDAO == null) {
			robotDAO = new RobotDAO();
		}

		return robotDAO;
	}
	
	/**
	 * @return instance of the {@link IDrawingDAO}
	 */
	public static IDrawingDAO getDrawingDAO(){
		if(drawinDAO == null) {
			drawinDAO = new DrawingDAO();
		}

		return drawinDAO;
	}
	
	/**
	 * @return instance of the {@link INoteDAO}
	 */
	public static INoteDAO getNoteDAO(){
		if(noteDAO == null) {
			noteDAO = new NoteDAO();
		}

		return noteDAO;
	}
}
