package openhex.game;

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
	
	public static Game get() {
		return INSTANCE;
	}
	
	private static void set(Game g) {
		if(INSTANCE == null) {
			INSTANCE = g;
		} else {
			throw new DuplicateInstanceException(Game.class);
		}
	}
	
	private Board board;
	private EntityData staticEntityData;
	private EntityData dynamicEntityData;
	
	public Game() {
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
