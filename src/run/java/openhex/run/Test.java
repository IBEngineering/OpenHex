package openhex.run;

import openhex.game.Game;
import openhex.vec.Vector;
import openhex.view.ViewApplication;

public class Test {

	public static void main(String[] args) {
		vectorTests();
		
		new Game();		
		ViewApplication vapp = new ViewApplication();
		vapp.start();
	}

	public static void vectorTests() {
		
		Vector<Integer> v3 = new Vector<Integer>(new Integer[] {1,1,-2}) {
			
			@Override
			protected Integer[] getValues() {
				return values;
			}
		};
		Vector<Double> v4 = new Vector<Double>(new Double[] {0d,1d,-1d}) {
			@Override
			protected Double[] getValues() {
				return values;
			}
		};
		
		System.out.println(v3.add(v4));
		System.out.println(v4.add(v3));
		
		System.out.println(v4.subtract(v3));
		
		System.out.println(v4.mult(v3));
		
		System.out.println(v4.div(v3));
		System.out.println(v3.div(v4));
		
		System.out.println(v4.negate());
		
		System.out.println(v4.inverse());
		
		
		
	}
	
}
