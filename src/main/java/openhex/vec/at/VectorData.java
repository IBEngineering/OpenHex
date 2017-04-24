package openhex.vec.at;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import openhex.vec.Vector;

/**
 * Describes a {@link Vector}.
 * 
 * @author MisterCavespider
 *
 */
@Retention(RUNTIME)
public @interface VectorData {

	public static final int DIMENSION_INVALID = -1;
	
	/**
	 * In what dimensions the {@link Vector} works.
	 * 0 stands for no dimensions (default), while -1
	 * stands for invalid dimension count.
	 * 
	 * @return
	 */
	int dimensions()	default	0;
	
}
