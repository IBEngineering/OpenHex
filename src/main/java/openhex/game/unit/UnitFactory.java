package openhex.game.unit;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import openhex.es.Identifier;
import openhex.es.ResourceDescriptor;
import openhex.es.unit.UnitData;
import openhex.game.Game;

public class UnitFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(UnitFactory.class);
	
	public static UnitData createUnitData(UUID unitType) {
		UnitData data = new UnitData(unitType);
		return data;
	}
	
	public static EntityId createUnit(Identifier uid, UnitData data, ResourceDescriptor desc) {
		if(!Game.exists()) {
			LOG.error("LogFactory is called before Game is instantiated!");
			LOG.warn("Returning null for 'createUnit()'");
			return null;
		}
		
		EntityData ued = Game.get().getUnitEntityData();
		EntityId id = ued.createEntity();
		
		ued.setComponents(id, uid, data, desc);
		
		return id;
	}
	
	public static EntityId createUnit(Identifier uid, UUID unitType, ResourceDescriptor desc) {
		return createUnit(uid, createUnitData(unitType), desc);
	}
	
}
