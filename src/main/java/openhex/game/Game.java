package openhex.game;

import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

import openhex.util.DuplicateInstanceException;

/**
 * 
 * @author MisterCavespider
 *
 */
public final class Game implements EntityManager {

	private static Game INSTANCE;
	
	public static Game getInstance() {
		return INSTANCE;
	}
	
	private static void setInstance(Game g) {
		if(INSTANCE == null) {
			INSTANCE = g;
		} else {
			throw new DuplicateInstanceException(Game.class);
		}
	}
	
	private EntityData tileEntityData;
	private EntityData staticEntityData;
	private EntityData dynamicEntityData;
	
	public Game() {
		/*
		 * setInstance throws a RunTimeException if an instance
		 * already exists
		 */
		setInstance(this);
		
		tileEntityData = new DefaultEntityData();
		staticEntityData = new DefaultEntityData();
		dynamicEntityData = new DefaultEntityData();
	}
	
	@Override
	public EntityData getTileEntityData() {
		return tileEntityData;
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
