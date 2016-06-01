package at.jku.se.grip.dao.impl;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.dao.IUserDAO;
import at.jku.se.grip.dao.base.impl.GenericDAO;

public class UserDAO extends GenericDAO<User> implements IUserDAO<User> {

	@Override
	public Class<User> getType() {
		return User.class;
	}

}
