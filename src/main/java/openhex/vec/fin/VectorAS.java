package openhex.vec.fin;

import openhex.vec.AxialHexVector;
import openhex.vec.at.VectorData;

/**
 * Static Axial Hexagonal Vector.
 * @author MisterCavespider
 *
 */
@VectorData(dimensions = 3)
public final class VectorAS extends AxialHexVector<Integer> {
	public VectorAS(Integer... values) {
		super(values);
	}
}
