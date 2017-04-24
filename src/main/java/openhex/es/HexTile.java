package openhex.es;

import com.simsilica.es.EntityComponent;

import openhex.pos.HexVector;

public class HexTile implements EntityComponent {
	
	protected HexVector position;
	protected float height;
	
	public HexTile(HexVector position, float height) {
		this.position = position;
		this.height = height;
	}
	
	public HexTile() {
		this(new HexVector(0, 0, 0), 0f);
	}

	public HexVector getPosition() {
		return position;
	}

	public void setPosition(HexVector position) {
		this.position = position;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
