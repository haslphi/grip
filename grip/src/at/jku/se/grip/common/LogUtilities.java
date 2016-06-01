package at.jku.se.grip.common;

public class LogUtilities {
	private static final LogUtilities logger = new LogUtilities();
	
	private LogUtilities() {
	}
	
	public static LogUtilities log() {
		return logger;
	}
	
	public void debug(String message) {
		System.out.println("[DEBUG]: " + message);
	}
	
	public void info(String message) {
		System.out.println("[INFO]: " + message);
	}
	
	public void warn(String message) {
		System.out.println("[WARN]: " + message);
	}
	
	public void error(String message) {
		System.err.println("[ERROR]: " + message);
	}
}
