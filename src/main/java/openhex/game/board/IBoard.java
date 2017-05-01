package openhex.game.board;

import java.util.Collection;
import java.util.Map;

import openhex.vec.fin.VectorAS;

public interface IBoard {

	/*
	 * Managing Tiles
	 */
	public void addTile(HexTile tile);
	public HexTile getTile(VectorAS pos);
	public void removeTile(VectorAS pos);
	public Collection<HexTile> getTiles();
	public Map<VectorAS, HexTile> getTileMap();
	
	/*
	 * Lock
	 */
	public void lock();
	public void unlock();
	public void setLock(boolean lock);
	public boolean isLocked();
	
	/*
	 * Listeners
	 */
	public void addListener(BoardListener listener);
	public void removeListener(BoardListener listener);
	public boolean isListening(BoardListener listener);
}
