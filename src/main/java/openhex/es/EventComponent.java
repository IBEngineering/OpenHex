package openhex.es;

import java.util.LinkedList;

import com.simsilica.es.EntityComponent;

import openhex.event.Event;

public class EventComponent implements EntityComponent {
	
	private LinkedList<Event> eventList;
	
	public EventComponent() {
		eventList = new LinkedList<>();
	}

	public void addEvent(Event e) {
		eventList.add(e);
	}
	
	public LinkedList<Event> getEventList() {
		return eventList;
	}
	
}
