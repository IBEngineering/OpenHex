package openhex.game.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import openhex.util.MultiNotifier;
import openhex.vec.fin.VectorAS;

public class Board implements IBoard, MultiNotifier<BoardListener> {
	
	public static final Long LISTENERTYPE_LOCK = 0L;
	public static final Long LISTENERTYPE_CHANGE = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(Board.class);
	
	private Map<VectorAS, HexTile> tiles;
	private boolean lock = false;
	
	private Map<Long, Set<BoardListener>> listeners;
	
	public Board() {
		tiles = new HashMap<>();
		listeners = new HashMap<>();
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
		for(BoardListener l : getSet(LISTENERTYPE_LOCK)) {
			LOG.info("Notifying {}", l);
			((BoardLockListener)l).onLock(this);
		}
	}

	@Override
	public void unlock() {
		LOG.info("Unlocking the board...");
		
		lock = false;
		for(BoardListener l : getSet(LISTENERTYPE_LOCK)) {
			((BoardLockListener)l).onUnlock(this);
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
		Long type = getListenerType(listener);
		LOG.trace("Adding {} of type {}", listener, type);
		if(type != -1) {
			getSet(type).add(listener);
		}
	}

	@Override
	public void removeListener(BoardListener listener) {
		Long type = getListenerType(listener);
		if(type != -1) {
			getSet(type).remove(listener);
		}
	}

	@Override
	public boolean isListening(BoardListener listener) {
		Long type = getListenerType(listener);
		if(type != -1) {
			return getSet(type).contains(listener);
		}
		return false;
	}

	private Set<BoardListener> getSet(Long type) {
		Set<BoardListener> set = listeners.get(type);
		if(set == null) {
			LOG.debug("No set found for {}. Creating new one", type);
			set = new HashSet<>();
			listeners.put(type, set);
		}
		return set;
	}
	
	@Override
	public Long getListenerType(BoardListener listener) {
		if(listener instanceof BoardLockListener) {
			return LISTENERTYPE_LOCK;
		}
		if(listener instanceof BoardChangeListener) {
			return LISTENERTYPE_CHANGE;
		}
		LOG.warn("{} is not supported!", listener);
		return -1L;
	}
}
