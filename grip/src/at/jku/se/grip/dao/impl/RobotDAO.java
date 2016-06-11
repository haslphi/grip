package at.jku.se.grip.dao.impl;

import at.jku.se.grip.beans.Robot;
import at.jku.se.grip.dao.IRobotDAO;
import at.jku.se.grip.dao.base.impl.HistorizableDAO;

public class RobotDAO extends HistorizableDAO<Robot> implements IRobotDAO {

	@Override
	public Class<Robot> getType() {
		return Robot.class;
	}
	
}
