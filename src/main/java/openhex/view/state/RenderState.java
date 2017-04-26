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

import openhex.es.ResourceTypes;
import openhex.game.EntityManager;
import openhex.game.Game;
import openhex.game.board.Board;
import openhex.game.board.BoardLockListener;
import openhex.game.board.HexTile;
import openhex.vec.Vectors;
import openhex.vec.fin.VectorAS;
import openhex.view.input.PickListener;

/**
 * Renders the scene.
 * Relies heavily on {@link Game} (or any other {@link EntityManager}).
 * 
 * @author MisterCavespider
 *
 */
public class RenderState extends BaseAppState implements PickListener, BoardLockListener {
	
	public static final long ES_HEXTILE_AND_RES = 0;
	
	private Map<VectorAS, Spatial> tileSpatials = new HashMap<>();
	
	@Override
	protected void initialize(Application app) {
		tileSpatials = new HashMap<>();
		
		Game.get().getBoard().addListener(this);
		Game.get().getBoard().lock();
	}

	@Override
	public void update(float tpf) {
		if(!getState(InputState.class).isListnening(this)) {
			getState(InputState.class).addListener(this);
		}
	}

	private void draw(HexTile t) {
		if(!tileSpatials.containsKey(t.getPosition())) {
			Mesh mesh = t.getResourceDescriptor().getMesh(ResourceTypes.MESH);
			ColorRGBA color = t.getResourceDescriptor().getColor(ResourceTypes.COLOR);
			
			Geometry geom = new Geometry("Hex", mesh);
			Material mat = getMaterial();
			mat.setColor("Color", color);
			geom.setMaterial(mat);
			((SimpleApplication)getApplication()).getRootNode().attachChild(geom);
			tileSpatials.put(t.getPosition(), geom);
		}
	}
	
	private void changeColor(HexTile t) {
		System.out.println("Changing color of " + t);
		if(tileSpatials.containsKey(t.getPosition())) {
			System.out.println("Found Spatial");
			Geometry geom = (Geometry) tileSpatials.get(t.getPosition());
			geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
		}
		System.out.println("Couldn't find Spatial");
	}
	
	private void position(HexTile t) {
		if(tileSpatials.containsKey(t.getPosition())) {
			Spatial s = tileSpatials.get(t.getPosition());
			s.setLocalTranslation(Vectors.toVector3f(t.getPosition(), 1f));
		}
	}
	
	private void remove(HexTile t) {
		if(tileSpatials.containsKey(t.getPosition())) {
			Spatial s = tileSpatials.get(t.getPosition());
			((SimpleApplication)getApplication()).getRootNode().detachChild(s);
			tileSpatials.remove(t.getPosition());
		}
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

	@Override
	public void onTilePick(VectorAS pos) {
		System.out.println("On pick");
		
		HexTile tile = Game.get().getBoard().getTile(pos);
		if(tile != null) {
			changeColor(tile);
		}
	}

	@Override
	public boolean onLock(Board board) {
		for(HexTile t : board.getTiles()) {
			try {
				draw(t);
				position(t);
			} catch(Exception e) {
				//returns upon error
				return false;
			}
		}
		
		// if everything is completed, it must be successful
		return true;
	}

	@Override
	public boolean onUnlock(Board board) {
		for(HexTile t : board.getTiles()) {
			try {
				remove(t);
			} catch(Exception e) {
				//returns upon error
				return false;
			}
		}
		
		return true;
	}
}
