package openhex.vec.fin;

import openhex.vec.CubeHexVector;

public final class VectorCS extends CubeHexVector<Integer> {
	public VectorCS(Integer q, Integer r, Integer s, Integer h) {
		super(q, r, s, h);
	}
	
	@Override
	protected void createValues() {
		values = new Integer[4];
	}
}
