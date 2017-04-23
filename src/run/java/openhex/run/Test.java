package openhex.run;

import openhex.pos.HexVector;
import openhex.pos.Vector;

public class Test {

	public static void main(String[] args) {
		hexvector();
	}
	
	public static void vector() {
		Vector<Integer> v0 = new Vector<Integer>(new Integer[] {0, 1, 2}) {
			@Override
			protected Integer[] getValues() {
				return values;
			}
		};
		Vector<Float> v1 = new Vector<Float>(new Float[] {2f, 3f, 1f}) {
			@Override
			protected Float[] getValues() {
				return values;
			}
		};
		
		System.out.println(v0);
		System.out.println(v0.addLocal(v1));
		System.out.println(v0);
	}
	
	public static void hexvector() {
		HexVector v0 = new HexVector(0, 1, 4);
		HexVector v1 = new HexVector(-2, -1, 3);
		
		System.out.println(v0);
		System.out.println(v0.add(v1));
	}
	
	
}
