package openhex.vec;

import openhex.vec.at.VectorData;

/**
 * 
 * @author MisterCavespider
 *
 * @param <N>	Number
 */
@VectorData(dimensions = 3)
public class AxialHexVector<N extends Number> extends Vector<N> {
	
	public AxialHexVector(N... values) {
		super(values);
	}
	
	public N getQ() {
		return getValues()[0];
	}
	
	public void setQ(N n) {
		getValues()[0] = n;
	}
	
	public N getR() {
		return getValues()[1];
	}
	
	public void setR(N n) {
		getValues()[1] = n;
	}
	
	public N getH() {
		return getValues()[2];
	}
	
	public void setH(N n) {
		getValues()[2] = n;
	}

	@Override
	protected N[] getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		int prime = 29;
		int result = 1;
		result = prime * result + getQ().intValue();
		result = prime * result + getR().intValue();
		return result;
	}
	
	
	
}
