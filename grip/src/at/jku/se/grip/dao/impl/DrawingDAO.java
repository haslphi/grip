package at.jku.se.grip.dao.impl;

import at.jku.se.grip.beans.Drawing;
import at.jku.se.grip.dao.IDrawingDAO;
import at.jku.se.grip.dao.base.impl.HistorizableDAO;

public class DrawingDAO extends HistorizableDAO<Drawing> implements IDrawingDAO {

	@Override
	public Class<Drawing> getType() {
		return Drawing.class;
	}
	
}
