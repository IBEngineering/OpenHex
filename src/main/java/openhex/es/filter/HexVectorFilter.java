package openhex.es.filter;

import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;

import openhex.es.HexTile;
import openhex.vec.fin.VectorAS;

public class HexVectorFilter implements ComponentFilter<HexTile> {

	private VectorAS pos;
	
	public HexVectorFilter(VectorAS pos) {
		this.pos = pos;
	}
	
	@Override
	public Class<HexTile> getComponentType() {
		return HexTile.class;
	}

	@Override
	public boolean evaluate(EntityComponent c) {
		if(c instanceof HexTile) {
			HexTile t = (HexTile) c;
			if(t.getPosition().equals(pos)) {
				System.out.println("Found: " + t.getPosition() + ", " + pos);
				return true;
			}
		}
		System.out.println("Not Found: " + c + ", " + pos);
		return false;
	}

}
