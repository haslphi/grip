package at.jku.se.grip.dao;

import java.util.UUID;

import at.jku.se.grip.dao.impl.RobotDAO;
import at.jku.se.grip.dao.impl.UserDAO;

public class DaoServiceRegistry {
	private static IUserDAO userDAO;
	private static IRobotDAO robotDAO;
	
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
}
