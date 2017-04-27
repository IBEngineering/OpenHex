package openhex.game.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import openhex.vec.fin.VectorAS;

public class Board implements IBoard {
	
	private static final Logger LOG = LoggerFactory.getLogger(Board.class);
	
	private Map<VectorAS, HexTile> tiles;
	private boolean lock = false;
	
	private Set<BoardLockListener> lockListeners;
	private Set<BoardChangeListener> changeListeners;
	
	public Board() {
		tiles = new HashMap<>();
		lockListeners = new HashSet<>();
		changeListeners = new HashSet<>();
	}
	
	@Override
	public void addTile(HexTile tile) {
		tiles.put(tile.getPosition(), tile);
	}
	
	@Override
	public HexTile getTile(VectorAS pos) {
		return tiles.get(pos);
	}
	
	@Override
	public void removeTile(VectorAS pos) {
		tiles.remove(pos);
	}

	@Override
	public Collection<HexTile> getTiles() {
		return tiles.values();
	}

	@Override
	public void lock() {
		LOG.info("Locking the board...");
		
		lock = true;
		for(BoardLockListener l : lockListeners) {
			l.onLock(this);
		}
	}

	@Override
	public void unlock() {
		LOG.info("Unlocking the board...");
		
		lock = false;
		for(BoardLockListener l : lockListeners) {
			l.onUnlock(this);
		}
	}

	@Override
	public void setLock(boolean lock) {
		if(lock)	lock();
		else		unlock();
	}

	@Override
	public boolean isLocked() {
		return lock;
	}

	@Override
	public void addListener(BoardListener listener) {
		if(listener instanceof BoardLockListener) {
			lockListeners.add((BoardLockListener) listener);
		} else if(listener instanceof BoardChangeListener) {
			changeListeners.add((BoardChangeListener)listener);
		}
	}

	@Override
	public void removeListener(BoardListener listener) {
		if(listener instanceof BoardLockListener) {
			lockListeners.remove((BoardLockListener) listener);
		} else if(listener instanceof BoardChangeListener) {
			changeListeners.remove((BoardChangeListener)listener);
		}
	}

	@Override
	public boolean isListening(BoardListener listener) {
		if(listener instanceof BoardLockListener) {
			return lockListeners.contains(listener);
		} else if(listener instanceof BoardChangeListener) {
			return changeListeners.contains(listener);
		} else {
			return false;
		}
	}
}
