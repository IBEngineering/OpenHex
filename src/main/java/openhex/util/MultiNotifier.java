package openhex.util;

public interface MultiNotifier<L extends Listener> extends Notifier<L> {

	public Long getListenerType(L listener);
	
}
