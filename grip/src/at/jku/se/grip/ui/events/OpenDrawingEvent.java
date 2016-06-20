package at.jku.se.grip.ui.events;

import at.jku.se.grip.beans.Drawing;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenDrawingEvent {
	
	private Drawing bean = null;
	
}
