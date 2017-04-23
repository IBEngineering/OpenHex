package openhex.pos;

import openhex.pos.at.VectorData;

@VectorData(dimensions = 3)
public class HexVector extends Vector<Integer> {

	public int x,y,z = 0;
	
	public HexVector() {
		this(0,0,0);
	}
	
	public HexVector(int x, int y, int z) {
		super(new Integer[] {x,y,z});
		System.out.println(values);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean validate() {
		if(x+y+z == 0) {
			return true;
		} else {
			y = -x-z;
			return false;
		}
	}
	
	protected Integer[] getValues() {
		validate();
		values[0] = x;
		values[1] = y;
		values[2] = z;
		
		return values;
	}
}
