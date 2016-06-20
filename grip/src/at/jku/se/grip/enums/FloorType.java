package at.jku.se.grip.enums;

import java.util.Arrays;
import java.util.List;

public enum FloorType {
	
	SOLID,
	JKU_CARPET;

	public static List<FloorType> valuesAsList() {
		// this kind of list is immutable
		return Arrays.asList(FloorType.values());
	}
}
