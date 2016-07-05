package at.jku.se.grip.dao.impl;

import java.util.List;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.dao.IUserDAO;
import at.jku.se.grip.dao.base.impl.HistorizableDAO;

public class UserDAO extends HistorizableDAO<User> implements IUserDAO {

	@Override
	public Class<User> getType() {
		return User.class;
	}

	/**
	 * Find a user by the given username and encrypted password
	 */
	@Override
	public User findByUsernameAndPassword(String username, String encryptedPwd) {
		CriteriaFactory factory = CriteriaFactory.create()
				.andEquals("username", username)
				.andEquals("password", encryptedPwd);
		List<User> users = findByCriteria(factory);
		if(users != null && users.size() == 1) {
			return users.get(0);
		}
		return null;
	}

}
