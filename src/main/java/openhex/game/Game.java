package openhex.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import openhex.game.board.Board;
import openhex.util.DuplicateInstanceException;

/**
 * 
 * @author MisterCavespider
 *
 */
public final class Game implements EntityManager {

	private static Game INSTANCE;
	private static Logger LOG = LoggerFactory.getLogger(Game.class);
	
	public static Game get() {
		return INSTANCE;
	}
	
	private static void set(Game g) throws DuplicateInstanceException {
		if(INSTANCE == null) {
			INSTANCE = g;
		} else {
			LOG.error("Duplicate instances of Game!");
			throw new DuplicateInstanceException(Game.class);
		}
	}
	
	private Board board;
	private EntityData staticEntityData;
	private EntityData dynamicEntityData;
	
	public Game() throws DuplicateInstanceException {
		/*
		 * setInstance throws a RunTimeException if an instance
		 * already exists
		 */
		set(this);
		
		board = new Board();
		staticEntityData = new DefaultEntityData();
		dynamicEntityData = new DefaultEntityData();
	}
	
	public Board getBoard() {
		return board;
	}

	@Override
	public EntityData getStaticEntityData() {
		return staticEntityData;
	}

	@Override
	public EntityData getDynamicEntityData() {
		return dynamicEntityData;
	}
	
	
}
