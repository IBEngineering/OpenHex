package openhex.pos;

import openhex.pos.at.VectorData;
import openhex.util.math.GenericMath;

@VectorData()	//No dimension, Float
public abstract class Vector<N extends Number> {

	private static class DefaultVector<DN extends Number> extends Vector<DN> {
		
		public DefaultVector(DN... values) {
			super(values);
		}
		
		@Override
		protected DN[] getValues() {
			return values;
		}
	}
	
	/**
	 * Returns the <code>dimensions</code> value of the
	 * {@link VectorData}. If the annotation doesn't exist
	 * and is null, -1 is returned.
	 * 
	 * @param o	Object that might have a {@link VectorData}.
	 * @return	{@link VectorData#dimensions()}
	 */
	public static int getDimensionsOf(Object o) {
		VectorData at = o.getClass().getAnnotation(VectorData.class);
		
		if(at != null) {
			return at.dimensions();
		} else {
			return -1;
		}
		
		
	}
	
	/**
	 * All values.
	 * The length of the values must be the same
	 * as the dimensions value in the {@link VectorData}.
	 */
	protected N[] values;
	
	private Class<N> nClass;
	
	public Vector(N... values) {
		this.nClass = (Class<N>) values[0].getClass();
		this.values = values;
	}
	
	/*
	 * SPACE
	 */
	public boolean sameDimensionsAs(Vector<?> v) {
		if(getDimensionsOf(v) != getDimensionsOf(this)) {
			return false;
		}
		
		if(v.values.length != this.values.length) {
			return false;
		}
		
		return true;
	}
	
	/*
	 * CONVERSION
	 */
	
	
	
	
	
	
	
	
	public void checkType() {
		if(nClass == Integer.class) 	{	System.out.println("Integer!");	}
		else if(nClass == Long.class) 	{	System.out.println("Long!");	}
		else if(nClass == Short.class)	{	System.out.println("Short!");	}
		else if(nClass == Byte.class) 	{	System.out.println("Byte!");	}
		else if(nClass == Float.class)	{	System.out.println("Float!");	}
		else							{	System.out.println("Double!");	}
	}
	
	/*
	 *  MATH
	 */
	
	/**
	 * Adds two vectors, and returns a new one.
	 * @param v
	 * @return	A new vector with the sum of this and v.
	 */
	public Vector<N> add(Vector<?> v) {
		if(!sameDimensionsAs(v)) {
			return null;
		}
		
		N[] r = GenericMath.genericArray(nClass, values.length);
		for(int i = 0; i<values.length; i++) {
			r[i] = GenericMath.genericAdd(nClass, getValues()[i], v.getValues()[i]);
		}
		
		return new DefaultVector<N>(r);
	}
	
	/**
	 * Adds two vectors locally, and returns this, for method chaining.
	 * The sum is applied locally (<code>this.values</code> changes).
	 * @param v
	 * @return	This vector with the sum of this and v.
	 */
	public Vector<N> addLocal(Vector<?> v) {
		if(!sameDimensionsAs(v)) {
			return null;
		}
		
		for(int i = 0; i<values.length; i++) {
			getValues()[i] = GenericMath.genericAdd(nClass, getValues()[i], v.getValues()[i]);
		}
		
		return this;
	}
	
	/**
	 * Subtracts two vectors, and returns a new one.
	 * @param v
	 * @return	A new vector with the difference between this and v.
	 */
	public Vector<N> subtract(Vector<?> v) {
		return add(v.negate());
	}
	
	/**
	 * Subtracts two vectors locally, and returns this, for method chaining.
	 * The difference is applied locally (<code>this.values</code> changes).
	 * @param v
	 * @return	This vector with the difference of this and v.
	 */
	public Vector<N> subtractLocal(Vector<?> v) {
		return addLocal(v.negate());
	}
	
	public Vector<N> mult(Vector<?> v) {
		if(!sameDimensionsAs(v)) {
			return null;
		}
		
		N[] r = GenericMath.genericArray(nClass, values.length);
		for(int i = 0; i<values.length; i++) {
			r[i] = GenericMath.genericMult(nClass, getValues()[i], v.getValues()[i]);
		}
		
		return new DefaultVector<N>(r);
	}
	
	public Vector<N> multLocal(Vector<?> v) {
		if(!sameDimensionsAs(v)) {
			return null;
		}
		
		for(int i = 0; i<values.length; i++) {
			getValues()[i] = GenericMath.genericMult(nClass, getValues()[i], v.getValues()[i]);
		}
		
		return this;
	}
	
	public Vector<N> div(Vector<?> v) {
		return mult(v.inverse());
	}
	
	public Vector<N> divLocal(Vector<?> v) {
		return multLocal(v.inverse());
	}
	
	/**
	 * Makes all positive values negative, and vice versa.
	 * @return	A new Vector
	 */
	public Vector<N> negate() {
		N[] r = GenericMath.genericArray(nClass, values.length);
		
		for (int i = 0; i < values.length; i++) {
			r[i] = GenericMath.genericSubtract(nClass, 0, values[i]);	// 0-a  = -a
		}
		
		return new DefaultVector<N>(r);
	}
	
	/**
	 * Makes all positive values negative, and vice versa.
	 * @return	This
	 */
	public Vector<N> negateLocal() {
		for (int i = 0; i < values.length; i++) {
			getValues()[i] = GenericMath.genericSubtract(nClass, 0, getValues()[i]);
		}
		
		return this;
	}
	
	/**
	 * Same as 1/x.
	 * @return
	 */
	public Vector<N> inverse() {		
		N[] r = GenericMath.genericArray(nClass, values.length);
		
		for (int i = 0; i < values.length; i++) {
			r[i] = GenericMath.genericDiv(nClass, 1, getValues()[i]);
		}
		
		return new DefaultVector<N>(r);
	}
	
	/**
	 * Same as 1/x.
	 * @return
	 */
	public Vector<N> inverseLocal() {
		for (int i = 0; i < values.length; i++) {
			getValues()[i] = GenericMath.genericDiv(nClass, 1, getValues()[i]);
		}
		
		return this;
	}
	
	/*
	 * GET AND SET
	 */
	protected abstract N[] getValues();
	
	/*
	 * OTHER CRAP
	 */
	
	/**
	 * For a 2 vectors to be the same,
	 * they must:
	 * 1. Have the same amount of dimensions.
	 * 2. Have the same values
	 */
	public boolean equals(Object obj) {
		//Check if dimensions are the same
		if(getDimensionsOf(obj) != getDimensionsOf(this)) {
			return false;
		}
		
		if(obj instanceof Vector) {
			Vector<?> that = (Vector<?>) obj;
			
			//Check if values are identical
			int dimension = getDimensionsOf(obj);
			for(int i = 0; i<dimension; i++) {
				if(that.values[i] != this.values[i]) {
					return false;
				}
			}
			return true;
		}
		
		//If nothing, return false
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		String r = this.getClass().getSimpleName() + '[';
		if(getValues().length > 0) {
			r += String.format("%s", getValues()[0].toString());
			for (int i = 1; i < values.length; i++) {
				r += String.format(", %s", getValues()[i].toString());
			}
		}
		
		r+=']';
		return r;
	}
	
	
	
}
