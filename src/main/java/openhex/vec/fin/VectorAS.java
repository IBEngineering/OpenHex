package openhex.vec.fin;

import openhex.vec.AxialHexVector;
import openhex.vec.at.VectorData;

@VectorData(dimensions = 3)
public final class VectorAS extends AxialHexVector<Integer> {
	public VectorAS(Integer q, Integer r, Integer h) {
		super(new Integer[] {q,r,h});
	}

	@Override
	protected void createValues() {
		values = new Integer[3];
	}
}
