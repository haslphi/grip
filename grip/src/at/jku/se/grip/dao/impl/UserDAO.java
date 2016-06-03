package at.jku.se.grip.dao.impl;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.dao.IUserDAO;
import at.jku.se.grip.dao.base.impl.HistorizableDAO;

public class UserDAO extends HistorizableDAO<User> implements IUserDAO<User> {

	@Override
	public Class<User> getType() {
		return User.class;
	}

}
