package at.jku.se.grip.dao;

import java.util.UUID;

public class DaoServiceRegistry {
	
	public static String provideUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
