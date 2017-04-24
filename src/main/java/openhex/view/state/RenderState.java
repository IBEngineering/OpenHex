package openhex.view.state;

import java.util.HashMap;
import java.util.Map;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import openhex.es.HexTile;
import openhex.es.ResourceDescriptor;
import openhex.es.ResourceTypes;
import openhex.event.PickingEvent;
import openhex.game.Game;
import openhex.vec.Vectors;
import openhex.vec.fin.VectorAS;

public class RenderState extends BaseAppState {
	
	public static final long ES_HEXTILE_AND_RES = 0;
	
	private Map<ResourceDescriptor, Spatial> hexTileSpatial = new HashMap<>();
	private EntitySet entitySet;
	private EntitySet eventEntitySet;
	
	@Override
	protected void initialize(Application app) {
		EntityData ted = Game.get().getTileEntityData();
		
		entitySet = ted.getEntities(HexTile.class, ResourceDescriptor.class);
		eventEntitySet = ted.getEntities(ResourceDescriptor.class, PickingEvent.class);
		
		createEntity(new HexTile(new VectorAS(0,0,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new VectorAS(1,-1,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new VectorAS(1,0,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new VectorAS(0,1,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new VectorAS(-1,0,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new VectorAS(0,-1,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new VectorAS(-1,1,0), 0), new ResourceDescriptor());
		
		System.out.println(entitySet.toString());
		
	}
	
	protected void createEntity(HexTile t, ResourceDescriptor r) {
		EntityId id = Game.get().getTileEntityData().createEntity();
		Game.get().getTileEntityData().setComponents(id, t, r);
	}

	@Override
	public void update(float tpf) {
		if(entitySet.applyChanges()) {
			System.out.println("Applied changes...");
			
			for(Entity added : entitySet.getAddedEntities()) {
				System.out.println("Processing added entities...");
				HexTile t = added.get(HexTile.class);
				ResourceDescriptor r = added.get(ResourceDescriptor.class);
				
				draw(r);
				position(r,t);
			}
			
			for(Entity changed : entitySet.getChangedEntities()) {
				System.out.println("Processing changed entities...");
				HexTile t = changed.get(HexTile.class);
				ResourceDescriptor r = changed.get(ResourceDescriptor.class);
				
				position(r,t);
			}
			
			for(Entity removed : entitySet.getRemovedEntities()) {
				System.out.println("Processing removed entities...");
				ResourceDescriptor r = removed.get(ResourceDescriptor.class);
				
				remove(r);
			}
		}
		
		if(eventEntitySet.applyChanges()) {
			for(Entity picked : eventEntitySet.getAddedEntities()) {
				System.out.println("Processing changed event entities...");
				Game.get().getTileEntityData().removeComponent(picked.getId(), PickingEvent.class);
				ResourceDescriptor r = picked.get(ResourceDescriptor.class);
				
				changeColor(r);
			}
		}
	}

	private void draw(ResourceDescriptor r) {
		Mesh mesh = r.getMesh(ResourceTypes.MESH);
		ColorRGBA color = r.getColor(ResourceTypes.COLOR);
		
		Geometry geom = new Geometry("Hex", mesh);
		Material mat = getMaterial();
		mat.setColor("Color", color);
		geom.setMaterial(mat);
		((SimpleApplication)getApplication()).getRootNode().attachChild(geom);
		hexTileSpatial.put(r, geom);
	}
	
	private void changeColor(ResourceDescriptor r) {
		Geometry geom = (Geometry) hexTileSpatial.get(r);
		geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
	}
	
	private void position(ResourceDescriptor r, HexTile t) {
		Spatial s = hexTileSpatial.get(r);
		s.setLocalTranslation(Vectors.toVector3f(t.getPosition(), 1f));
	}
	
	private void remove(ResourceDescriptor r) {
		Spatial s = hexTileSpatial.get(r);
		((SimpleApplication)getApplication()).getRootNode().detachChild(s);
		hexTileSpatial.remove(r);
	}
	
	@Override
	protected void cleanup(Application app) {
		
	}

	@Override
	protected void onEnable() {}

	@Override
	protected void onDisable() {}

	public Material getMaterial() {
		Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		//mat.setColor("Color", ColorRGBA.randomColor());
		mat.setTexture("ColorMap", getApplication().getAssetManager().loadTexture("Textures/Tiles/tiles.jpg"));
		mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Back);
		return mat;
	}

	public EntitySet getEventEntitySet() {
		return eventEntitySet;
	}
}
