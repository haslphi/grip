package at.jku.se.grip.dao.impl;

import at.jku.se.grip.beans.Note;
import at.jku.se.grip.dao.INoteDAO;
import at.jku.se.grip.dao.base.impl.GenericDAO;

public class NoteDAO extends GenericDAO<Note> implements INoteDAO {

	@Override
	public Class<Note> getType() {
		return Note.class;
	}
	
}
