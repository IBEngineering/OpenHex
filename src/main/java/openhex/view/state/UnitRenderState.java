package openhex.view.state;

import java.util.HashMap;
import java.util.UUID;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import openhex.es.Identifier;
import openhex.es.ResourceDescriptor;
import openhex.game.Game;

/**
 * Should only be called by {@link RenderState}.
 * @author MisterCavespider
 *
 */
public class UnitRenderState extends BaseAppState {

	/**
	 * Every unit has it's own node.
	 * The model is displayed on
	 * unitnode(UUID) -> model.
	 */
	private HashMap<UUID, Node> unitNodes;
	/**
	 * Single node that holds all unitnodes.
	 */
	private Node unitsNode;
	
	private EntitySet drawableUnits;
	
	@Override
	protected void initialize(Application app) {
		unitNodes = new HashMap<>();
		drawableUnits = Game.get().getUnitEntityData().getEntities(Identifier.class, ResourceDescriptor.class);
	}
	
	@Override
	public void update(float tpf) {
		if(drawableUnits.applyChanges()) {
			for(Entity a : drawableUnits.getAddedEntities()) {
				UUID uuid = a.get(Identifier.class).getId();
			}
		}
	}

	@Override
	protected void cleanup(Application app) {
		
	}

	@Override
	protected void onEnable() {
		//game resume?
	}

	@Override
	protected void onDisable() {
		//game pause?
	}

}
