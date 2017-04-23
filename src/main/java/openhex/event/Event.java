package openhex.event;

public interface Event {

	public String getName();
	
	public boolean isConsumed();
	public void consume();
	
}
