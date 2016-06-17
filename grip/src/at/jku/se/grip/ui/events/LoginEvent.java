package at.jku.se.grip.ui.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import at.jku.se.grip.beans.User;

@Getter
@AllArgsConstructor
public class LoginEvent {
	private boolean validLogin = false;
	private User user = null;
}