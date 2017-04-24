package openhex.vec.fin;

import openhex.vec.AxialHexVector;
import openhex.vec.at.VectorData;

@VectorData(dimensions = 3)
public final class VectorAD extends AxialHexVector<Float> {
	public VectorAD(Float q, Float r, Float h) {
		super(q, r, h);
	}
	
	@Override
	protected void createValues() {
		values = new Float[3];
	}
}