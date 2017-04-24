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
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.es.base.DefaultEntityData;

import openhex.es.HexTile;
import openhex.es.ResourceDescriptor;
import openhex.event.PickingEvent;
import openhex.pos.HexVector;
import openhex.pos.Vectors;
import openhex.view.mesh.HexMesh;

public class RenderState extends BaseAppState {
	
	public static final long ES_HEXTILE_AND_RES = 0;
	
	private EntityData entityData = new DefaultEntityData();
	private Map<ResourceDescriptor, Spatial> hexTileSpatial = new HashMap<>();
	private EntitySet entitySet;
	private EntitySet eventEntitySet;
	
	@Override
	protected void initialize(Application app) {
		entitySet = entityData.getEntities(HexTile.class, ResourceDescriptor.class);
		eventEntitySet = entityData.getEntities(ResourceDescriptor.class, PickingEvent.class);
		
		createEntity(new HexTile(new HexVector(0,1,-1), 0), new ResourceDescriptor());
		createEntity(new HexTile(new HexVector(1,0,-1), 0), new ResourceDescriptor());
		createEntity(new HexTile(new HexVector(0,0,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new HexVector(1,-1,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new HexVector(-1,1,0), 0), new ResourceDescriptor());
		createEntity(new HexTile(new HexVector(-1,0,1), 0), new ResourceDescriptor());
		createEntity(new HexTile(new HexVector(0,-1,1), 0), new ResourceDescriptor());
		
		System.out.println(entitySet.toString());
		
	}
	
	protected void createEntity(HexTile t, ResourceDescriptor r) {
		EntityId id = entityData.createEntity();
		entityData.setComponents(id, t, r);
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
			for(Entity added : eventEntitySet.getAddedEntities()) {
				System.out.println("Processing added event entities...");
				ResourceDescriptor r = added.get(ResourceDescriptor.class);
				
				changeColor(r);
			}
			
			for(Entity changed : eventEntitySet.getChangedEntities()) {
				System.out.println("Processing changed event entities...");
				entityData.removeComponent(changed.getId(), PickingEvent.class);
				ResourceDescriptor r = changed.get(ResourceDescriptor.class);
				
				changeColor(r);
			}
		}
	}

	private void draw(ResourceDescriptor r) {
		Geometry geom = new Geometry("Hex", new HexMesh(1f));
		geom.setMaterial(getMaterial());
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
		mat.setColor("Color", ColorRGBA.randomColor());
		mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Back);
		return mat;
	}

	public EntityData getEntityData() {
		return entityData;
	}

	public void setEntityData(EntityData entityData) {
		this.entityData = entityData;
	}

	public EntitySet getEventEntitySet() {
		return eventEntitySet;
	}
	
	
}
