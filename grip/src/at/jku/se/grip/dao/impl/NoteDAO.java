package at.jku.se.grip.dao.impl;

import java.util.List;

import at.jku.se.grip.beans.Note;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.dao.INoteDAO;
import at.jku.se.grip.dao.base.impl.GenericDAO;

public class NoteDAO extends GenericDAO<Note> implements INoteDAO {

	@Override
	public Class<Note> getType() {
		return Note.class;
	}
	
	@Override
	public Note findByUserId(String userId) {
		CriteriaFactory factory = CriteriaFactory.create()
				.andEquals("noteUser.id.id", userId);
		List<Note> users = findByCriteria(factory);
		if(users != null && users.size() == 1) {
			return users.get(0);
		}
		return null;
	}
	
}
