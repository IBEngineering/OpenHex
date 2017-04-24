package openhex.vec.fin;

import openhex.vec.CubeHexVector;

public final class VectorCD extends CubeHexVector<Float> {
	public VectorCD(Float q, Float r, Float s, Float h) {
		super(q, r, s, h);
	}
	
	@Override
	protected void createValues() {
		values = new Float[4];
	}
}
