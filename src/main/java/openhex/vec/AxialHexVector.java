package openhex.vec;

import openhex.vec.at.VectorData;

/**
 * 
 * @author MisterCavespider
 *
 * @param <N>	Number
 */
@VectorData(dimensions = 3)
public abstract class AxialHexVector<N extends Number> extends Vector<N> {

	protected N q,r,h;
	
	public AxialHexVector(N... values) {
		super(values);
		this.q = values[0];
		this.r = values[1];
		this.h = values[2];
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
		values[0] = q;
		values[1] = r;
		values[2] = h;
		return values;
	}
	
}
