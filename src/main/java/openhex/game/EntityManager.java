package openhex.game;

import com.simsilica.es.EntityData;

/**
 * 
 * @author MisterCavespider
 *
 */
public interface EntityManager {

	public EntityData getTileEntityData();
	public EntityData getStaticEntityData();
	public EntityData getDynamicEntityData();
	
}
