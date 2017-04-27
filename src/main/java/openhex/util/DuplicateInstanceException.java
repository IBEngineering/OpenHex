package openhex.util;

/**
 * Should be thrown when a singleton is instanced twice.
 * 
 * @author MisterCavespider
 *
 */
public class DuplicateInstanceException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -8594557931392331436L;
	
	
	public DuplicateInstanceException() {
		super("Duplicate instances");
	}
	
	public DuplicateInstanceException(Class<?> c) {
		super("Duplicate instances in class " + c.getName());
	}
}
