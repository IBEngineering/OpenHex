package openhex.vec.fin;

import openhex.vec.AxialHexVector;
import openhex.vec.at.VectorData;

/**
 * Dynamic Axial Hexagonal Vector.
 * 
 * @author MisterCavespider
 *
 */
@VectorData(dimensions = 3)
public final class VectorAD extends AxialHexVector<Float> {
	public VectorAD(Float... values) {
		super(values);
	}
}
