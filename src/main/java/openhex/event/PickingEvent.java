package openhex.event;

public class PickingEvent implements Event {

	private boolean consumed;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Picking";
	}

	@Override
	public boolean isConsumed() {
		return consumed;
	}

	@Override
	public void consume() {
		consumed = true;
	}

}
