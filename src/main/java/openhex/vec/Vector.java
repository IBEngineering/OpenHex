package openhex.vec;

import java.util.Arrays;

import openhex.util.math.GenericMath;
import openhex.vec.at.VectorData;

/**
 * Generic Vector with generic type.
 * Uses GenericMath for it's math.
 * This class is easier to use when extending,
 * instead of anonymously.
 * 
 * @author MisterCavespider
 *
 * @param <N>	Number
 */
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
	
	protected Class<N> nClass;
	
	public Vector(N... values) {
		if(values.length == getDimensionsOf(this)) {
			this.nClass = (Class<N>) values[0].getClass();
			this.values = values;
		} else {
			throw new DimensionalException(getDimensionsOf(this), values.length);
		}
	}
	
	/**
	 * Checks whether the dimensions specified by
	 * the {@link VectorData} AND values length is
	 * the same.
	 * 
	 * @param v	Another vector
	 * @return	Same {@link VectorData#dimensions()} && values.length
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
	 *  MATH
	 */
	
	/**
	 * Adds two vectors, and returns a new one.
	 * @param v	The other vector
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
	 * @param v	The other vector
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
	 * @param v	The other vector
	 * @return	A new vector with the difference between this and v.
	 */
	public Vector<N> subtract(Vector<?> v) {
		return add(v.negate());
	}
	
	/**
	 * Subtracts two vectors locally, and returns this, for method chaining.
	 * @param v	The other vector
	 * @return	This vector with the difference of this and v.
	 */
	public Vector<N> subtractLocal(Vector<?> v) {
		return addLocal(v.negate());
	}
	
	/**
	 * Multiplies two vectors, and returns a new one.
	 * @param v	The other vector
	 * @return	A new vector with the product of this and v.
	 */
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
	
	/**
	 * Multiplies two vectors locally, and returns this, for method chaining.
	 * @param v	The other vector
	 * @return	This vector with the product of this and v.
	 */
	public Vector<N> multLocal(Vector<?> v) {
		if(!sameDimensionsAs(v)) {
			return null;
		}
		
		for(int i = 0; i<values.length; i++) {
			getValues()[i] = GenericMath.genericMult(nClass, getValues()[i], v.getValues()[i]);
		}
		
		return this;
	}
	
	/**
	 * Divides two vectors, and returns a new one.
	 * @param v	The other vector
	 * @return	A new vector with the quotient of this and v.
	 */
	public Vector<N> div(Vector<?> v) {
		return mult(v.inverse());
	}
	
	/**
	 * Divides two vectors locally, and returns this, for method chaining.
	 * @param v	The other vector
	 * @return	This vector with the quotient of this and v.
	 */
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
	 * @return	A new Vector
	 */
	public Vector<N> inverse() {		
		N[] r = GenericMath.genericArray(nClass, values.length);
		
		for (int i = 0; i < values.length; i++) {
			r[i] = GenericMath.genericDiv(nClass, 1, getValues()[i]);
		}
		
		return new DefaultVector<N>(r);
	}
	
	/**
	 * Same as 1/x, but locally.
	 * @return	This
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
	/**
	 * Get values.
	 * This is made abstract, so you can update
	 * your local values.
	 * @return	The (correct) values.
	 */
	protected abstract N[] getValues();
	
	/*
	 * OTHER CRAP
	 */
	
	
	
	

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vector other = (Vector) obj;
		if (!Arrays.equals(values, other.values)) {
			return false;
		}
		return true;
	}
	
	
	
}
