package at.jku.se.grip.dao;

import at.jku.se.grip.beans.User;
import at.jku.se.grip.dao.base.IHistorizableDAO;

public interface IUserDAO extends IHistorizableDAO<User> {

	User findByUsernameAndPassword(String username, String encryptedPwd);

}
