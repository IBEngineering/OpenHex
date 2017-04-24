package openhex.es;

import com.simsilica.es.EntityComponent;

import openhex.vec.fin.VectorAS;

/**
 * 
 * @author MisterCavespider
 *
 */
public class HexTile implements EntityComponent {
	
	protected VectorAS position;
	protected float height;
	
	public HexTile(VectorAS position, float height) {
		this.position = position;
		this.height = height;
	}
	
	public HexTile() {
		this(new VectorAS(0, 0, 0), 0f);
	}

	public VectorAS getPosition() {
		return position;
	}

	public void setPosition(VectorAS position) {
		this.position = position;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
