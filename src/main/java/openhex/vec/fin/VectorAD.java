package openhex.vec.fin;

import openhex.vec.AxialHexVector;

public final class VectorAD extends AxialHexVector<Float> {
	public VectorAD(Float q, Float r, Float h) {
		super(q, r, h);
	}
	
	@Override
	protected void createValues() {
		values = new Float[3];
	}
}
