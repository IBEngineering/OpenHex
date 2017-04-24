package openhex.vec;

/**
 * 
 * @author MisterCavespider
 *
 */
public class DimensionalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5277991466899562645L;

	public DimensionalException() {
		super("Error in dimensions");
	}
	
	public DimensionalException(int d1, int d2) {
		super(String.format("Error in dimensions, supposed to be %d, is actually %d", d1, d2));
	}
	
}
