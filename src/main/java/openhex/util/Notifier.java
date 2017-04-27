package openhex.util;

public interface Notifier<L extends Listener> {

	public void addListener(L listener);
	public void removeListener(L listener);
	public boolean isListening(L listener);
	
}
