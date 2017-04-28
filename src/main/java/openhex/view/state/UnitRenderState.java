package openhex.view.state;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import openhex.es.Identifier;
import openhex.es.ResourceDescriptor;
import openhex.es.ResourceTypes;
import openhex.game.Game;

/**
 * Should only be called by {@link RenderState}.
 * @author MisterCavespider
 *
 */
public class UnitRenderState extends BaseAppState {

	private static final Logger LOG = LoggerFactory.getLogger(UnitRenderState.class);
	
	/**
	 * Every unit has it's own node.
	 * The model is displayed on
	 * unitnode(UUID) -> model.
	 */
	private HashMap<UUID, Node> unitNodes;
	/**
	 * Single node that holds all unitnodes.
	 */
	private Node majorNode;
	
	private EntitySet drawableUnits;
	
	@Override
	protected void initialize(Application app) {
		unitNodes = new HashMap<>();
		majorNode = new Node("All unit nodes");
		drawableUnits = Game.get().getUnitEntityData().getEntities(Identifier.class, ResourceDescriptor.class);
		LOG.trace("size of drawaleUnits: {}", drawableUnits.size());
		
		for(Entity e : drawableUnits) {
			/*
			 * Everything that existed before the EntitySet
			 * was created does not count as a change.
			 * Therefore, it is processed here.
			 */
			LOG.trace("drawing {}", e);
			UUID uuid = e.get(Identifier.class).getId();
			ResourceDescriptor desc = e.get(ResourceDescriptor.class);
			
			createNode(uuid);
			attModel(uuid, desc);
		}
		
		((SimpleApplication)app).getRootNode().attachChild(majorNode);
	}
	
	@Override
	public void update(float tpf) {
		if(drawableUnits.applyChanges()) {
			LOG.debug("Changes were applied");
			
			for(Entity a : drawableUnits.getAddedEntities()) {
				LOG.trace("{} added!", a);
				UUID uuid = a.get(Identifier.class).getId();
				ResourceDescriptor desc = a.get(ResourceDescriptor.class);
				
				createNode(uuid);
				attModel(uuid, desc);
			}
		}
	}

	private void createNode(UUID uuid) {
		if(!unitNodes.containsKey(uuid)) {
			LOG.trace("Creating node for {}", uuid.hashCode());
			Node n = new Node("UnitNode#"+uuid.hashCode());
			majorNode.attachChild(n);
			unitNodes.put(uuid, n);
		}
	}
	
	private void attModel(UUID uuid, ResourceDescriptor desc) {
		//is there a node?
		if(unitNodes.containsKey(uuid)) {
			//get node
			Node n = unitNodes.get(uuid);
			//does it have a child called "model"?
			if(n.getChild("model") == null) {
				Spatial s;
				String model = desc.getString(ResourceTypes.MODEL);
				if(model == null || model.isEmpty()) {
					//fall back to geom
					LOG.debug("No model found, using geom");
					
					Geometry g;
					//get mesh
					Mesh mesh = desc.getMesh(ResourceTypes.MESH);
					//does mesh exist?
					if(mesh != null) {
						//use mesh
						LOG.trace("Using supplied mesh");
						g = new Geometry("model", mesh);
					} else {
						//fall back to square
						LOG.debug("No mesh found, using box");
						g = new Geometry("model", new Box(1, 1, 1));
					}
					
					//get color
					ColorRGBA color = desc.getColor(ResourceTypes.COLOR);
					//get texture
					String texString = desc.getString(ResourceTypes.TEXTURE);
					//does texture not exist?
					if(texString == null || texString.isEmpty()) {
						//set to default value
						LOG.debug("Texture not found, using \"tiles.JPG\"");
						texString = "tiles.JPG";
					}
					//set texture
					Texture texture = getApplication().getAssetManager().loadTexture(texString);
					
					//create material
					Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
					mat.setTexture("ColorMap", texture);
					mat.setColor("Color", color);
					
					//set material
					g.setMaterial(mat);
					
					//spatial = geometry
					s = g;
				} else {
					LOG.trace("model found");
					//use assetmanager and (String)model
					s = getApplication().getAssetManager().loadModel(model);
					//make sure it's called "model"
					if(!s.getName().equals("model")) {
						LOG.trace("renamed {} to \"model\"", s);
						s.setName("model");
					}
				}
				
				//attach child to proper node
				n.attachChild(s);
			} else {
				LOG.trace("\"model\" already exists");
			}
		} else {
			LOG.warn("Couldn't find node to attach model to! id: {}", uuid);
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
