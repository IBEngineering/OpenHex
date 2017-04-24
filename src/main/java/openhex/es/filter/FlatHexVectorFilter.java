package openhex.es.filter;

import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;

import openhex.es.HexTile;
import openhex.vec.AxialHexVector;

/**
 * 
 * @author MisterCavespider
 *
 */
public class FlatHexVectorFilter implements ComponentFilter<HexTile> {

	private AxialHexVector<?> pos;
	
	public FlatHexVectorFilter(AxialHexVector<?> pos) {
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
			if(t.getPosition().getR() == pos.getR() && t.getPosition().getQ() == pos.getQ()) {
				System.out.println("Found: " + t.getPosition() + ", " + pos);
				return true;
			}
		}
		System.out.println("Not Found: " + c + ", " + pos);
		return false;
	}

}
