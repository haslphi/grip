package at.jku.se.grip.ui.events;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

import at.jku.se.grip.common.LogUtilities;

/**
 * Listener class for DeadEvents on the EventBus.<br>
 * Listener waiting for events that were posted on the EventBus but not delivered to anyone.<br>
 * This will be logged to the console.
 * 
 * @author Philipp
 *
 */
public class DeadEventListener {
	
	boolean notDelivered = false;
	
	// TODO: Register this lsitener to the eventbus
	
	@Subscribe
	public void listen(DeadEvent e) {
		notDelivered = true;
		LogUtilities.log().warn("DEAD EVENT: Unreceived event arrived.");
		LogUtilities.log().info("Event: " + e.getEvent().toString());
	}
	
	public boolean isNotDelivered() {
		return notDelivered;
	}

}
