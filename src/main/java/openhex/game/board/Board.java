package openhex.game.board;

import java.util.HashMap;

import openhex.vec.fin.VectorAS;

public class Board implements IBoard {
	
	private HashMap<VectorAS, HexTile> tiles;
	
	public void addTile(HexTile tile) {
		tiles.put(tile.getPosition(), tile);
	}
	
	public void getTile(VectorAS pos) {
		tiles.get(pos);
	}
	
	public void removeTile(VectorAS pos) {
		tiles.remove(pos);
	}
}
