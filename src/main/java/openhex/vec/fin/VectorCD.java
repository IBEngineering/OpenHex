package openhex.vec.fin;

import openhex.vec.CubeHexVector;
import openhex.vec.at.VectorData;

/**
 * Dynamic Cube Hexagonal Vector.
 * @author MisterCavespider
 *
 */
@VectorData(dimensions = 3)
public final class VectorCD extends CubeHexVector<Float> {
	public VectorCD(Float q, Float r, Float s, Float h) {
		super(q, r, s, h);
	}
}
