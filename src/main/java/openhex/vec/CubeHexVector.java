package openhex.vec;

import openhex.util.math.GenericMath;
import openhex.vec.at.VectorData;

/**
 * 
 * @author MisterCavespider
 *
 * @param <N>	Number
 */
@VectorData(dimensions = 4)
public abstract class CubeHexVector<N extends Number> extends Vector<N> {

	protected N q,r,s,h;
	
	public CubeHexVector(N... values) {
		super(values);
		this.q = values[0];
		this.r = values[1];
		this.s = values[2];
		this.h = values[3];
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
	
	public N getS() {
		return getValues()[2];	// == -q-r
	}
	
	public void setS(N n) {
		getValues()[2] = n;
	}
	
	public N getH() {
		return getValues()[3];
	}
	
	public void setH(N n) {
		getValues()[3] = n;
	}

	protected void validate() {
		//s = (0-q)-r
		values[2] = GenericMath.genericSubtract(nClass, GenericMath.genericSubtract(nClass, 0, values[1]), values[2]);
	}
	
	@Override
	protected N[] getValues() {
		validate();
		values[0] = q;
		values[1] = r;
		values[2] = s;
		values[3] = h;
		return values;
	}
}
