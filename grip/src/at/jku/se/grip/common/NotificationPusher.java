package at.jku.se.grip.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/**
 * Simple class to show messages, warnings, errors to the user.
 * 
 * @author Philipp
 *
 */
public class NotificationPusher {
	private static final String DEFAULT_ERROR_CAPTION = "An error occured. Please contact an administrator!";
	private static final Integer DEFAULT_CUSTOM_ERROR_DELAY = 3000;
	
	/**
	 * Show a java error.<br>
	 * Requires the user to click the message. Will not disappear automatically.<br>
	 * With dedicated caption if necessary.
	 * 
	 * @param context on which page to show the notification
	 * @param e the exception
	 * @param caption own caption, if null or empty, the default caption {@value #DEFAULT_ERROR_CAPTION} will be used.
	 */
	public static void showError(Page current, Exception e, String caption) {
		Notification notif = new Notification(StringUtils.isNotBlank(caption) ? caption: DEFAULT_ERROR_CAPTION,
				e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e),
				Notification.Type.ERROR_MESSAGE,
				true);
		notif.setPosition(Position.BOTTOM_CENTER);
		notif.setDelayMsec(-1);
		notif.show(current);
	}
	
	/**
	 * Show a custom error.<br>
	 * With dedicated caption if necessary.
	 * 
	 * @param context on which page to show the notification
	 * @param error the error string
	 * @param caption own caption, if null or empty, the default caption {@value #DEFAULT_ERROR_CAPTION} will be used
	 * @param delay the delay time for the notification, if null default ({@value #DEFAULT_CUSTOM_ERROR_DELAY}) will be used
	 */
	public static void showCustomError(Page current, String error, String caption, Integer delay) {
		Notification notif = new Notification(StringUtils.isNotBlank(caption) ? caption: DEFAULT_ERROR_CAPTION,
				error,
				Notification.Type.ERROR_MESSAGE);
		notif.setPosition(Position.BOTTOM_CENTER);
		notif.setDelayMsec(delay != null ? delay : DEFAULT_CUSTOM_ERROR_DELAY);
		notif.show(current);
	}
	
	/**
	 * Show a custom warning.<br>
	 * With dedicated caption if necessary.
	 * 
	 * @param context on which page to show the notification
	 * @param warning the warning string
	 * @param caption own caption, if null or empty, the default caption {@value #DEFAULT_ERROR_CAPTION} will be used
	 * @param delay the delay time for the notification, if null default ({@value #DEFAULT_CUSTOM_ERROR_DELAY}) will be used
	 */
	public static void showCustomWarning(Page current, String warning, String caption, Integer delay, Position position) {
		Notification notif = new Notification(StringUtils.isNotBlank(caption) ? caption: DEFAULT_ERROR_CAPTION,
				warning,
				Notification.Type.WARNING_MESSAGE);
		notif.setPosition(position != null ? position : Position.BOTTOM_CENTER);
		notif.setDelayMsec(delay != null ? delay : DEFAULT_CUSTOM_ERROR_DELAY);
		notif.show(current);
	}
	
	/**
	 * Show a custom information.<br>
	 * With dedicated caption if necessary.
	 * 
	 * @param context on which page to show the notification
	 * @param warning the warning string
	 * @param caption own caption, if null or empty, the default caption {@value #DEFAULT_ERROR_CAPTION} will be used
	 * @param delay the delay time for the notification, if null default ({@value #DEFAULT_CUSTOM_ERROR_DELAY}) will be used
	 */
	public static void showCustomInformation(Page current, String caption, Integer delay, Position position) {
		Notification notif = new Notification(StringUtils.isNotBlank(caption) ? caption: DEFAULT_ERROR_CAPTION,
				Notification.Type.HUMANIZED_MESSAGE);
		notif.setPosition(position != null ? position : Position.MIDDLE_CENTER);
		notif.setDelayMsec(delay != null ? delay : DEFAULT_CUSTOM_ERROR_DELAY);
		notif.show(current);
	}
	
	/**
	 * Show a generic notification validation message.
	 * 
	 * @param current on which page to show the notification
	 */
	public static void showValidationViolatedNotification(Page current) {
		Notification notif = new Notification("Validation violated!", Notification.Type.TRAY_NOTIFICATION);
		notif.setPosition(Position.BOTTOM_RIGHT);
		notif.show(current);
	}

}
