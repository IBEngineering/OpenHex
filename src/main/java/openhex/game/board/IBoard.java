package openhex.game.board;

import openhex.vec.fin.VectorAS;

public interface IBoard {

	public void addTile(HexTile tile);
	public void getTile(VectorAS pos);
	public void removeTile(VectorAS pos);
	
}
