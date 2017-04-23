package openhex.es;

import openhex.pos.HexVector;

public class HexTile {
	
	protected HexVector position;
	protected float height;
	protected long type;
	
	public HexTile(HexVector position, float height, long type) {
		this.position = position;
		this.height = height;
		this.type = type;
	}
	
	public HexTile() {
		this(new HexVector(0, 0, 0), 0f, 0L);
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
