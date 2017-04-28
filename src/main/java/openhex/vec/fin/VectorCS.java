package openhex.vec.fin;

import openhex.vec.CubeHexVector;
import openhex.vec.at.VectorData;

/**
 * Static Cube Hexagonal Vector.
 * @author MisterCavespider
 *
 */
@VectorData(dimensions = 3)
public final class VectorCS extends CubeHexVector<Integer> {
	public VectorCS(Integer... values) {
		super(values);
	}
}
