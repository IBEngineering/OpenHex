package openhex.pos.at;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import openhex.pos.Vector;

@Retention(RUNTIME)
public @interface VectorData {

	/**
	 * In what dimensions the {@link Vector} works.
	 * 0 stands for no dimensions (default), while -1
	 * stands for invalid dimension count.
	 * 
	 * @return
	 */
	int dimensions()	default	0;
	
}
