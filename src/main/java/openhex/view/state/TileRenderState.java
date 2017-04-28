package openhex.view.state;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
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
public class TileRenderState extends BaseAppState implements PickListener, BoardLockListener {
	
	public static final long ES_HEXTILE_AND_RES = 0;
	private static final Logger LOG = LoggerFactory.getLogger(TileRenderState.class);
	
	private Map<VectorAS, Spatial> tileSpatials = new HashMap<>();
	private Node tileNode;
	
	@Override
	protected void initialize(Application app) {
		tileSpatials = new HashMap<>();
		tileNode = new Node("Tile Node");
		
		((SimpleApplication)app).getRootNode().attachChild(tileNode);
		
		Game.get().getBoard().addListener(this);
		Game.get().getBoard().lock();
	}

	@Override
	public void update(float tpf) {
		if(!getState(InputState.class).isListening(this)) {
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
			tileNode.attachChild(geom);
			tileSpatials.put(t.getPosition(), geom);
		}
	}
	
	private void changeColor(HexTile t) {
		if(tileSpatials.containsKey(t.getPosition())) {
			LOG.trace("Found the Spatials for {} to change the color of", t);
			
			Geometry geom = (Geometry) tileSpatials.get(t.getPosition());
			geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
		} else {
			LOG.debug("Couldn't find spatial for {} to change the color of", t);
		}
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
			tileNode.detachChild(s);
			tileSpatials.remove(t.getPosition());
		}
	}
	
	@Override
	protected void cleanup(Application app) {
		if(tileNode.getChildren().size() > 0) {
			tileNode.detachAllChildren();
		}
	}

	@Override
	protected void onEnable() {}

	@Override
	protected void onDisable() {}

	public Material getMaterial() {
		Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setTexture("ColorMap", getApplication().getAssetManager().loadTexture("Textures/Tiles/tiles.jpg"));
		mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Back);
		return mat;
	}

	@Override
	public void onTilePick(VectorAS pos) {
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
