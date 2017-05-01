package openhex.view.state;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import openhex.game.EntityManager;
import openhex.game.Game;
import openhex.game.board.Board;
import openhex.game.board.BoardLockListener;
import openhex.game.board.HexTile;
import openhex.vec.fin.VectorAS;
import openhex.view.input.PickListener;
import openhex.view.mesh.HexTerrainMesh;

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
	
	private Node tileNode;
	
	@Override
	protected void initialize(Application app) {
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

	private void draw(Map<VectorAS, HexTile> tilemap) {
		long m1 = System.currentTimeMillis();
		HexTerrainMesh htm = new HexTerrainMesh();
		htm.bake(Game.get().getBoard().getTileMap());
		long m2 = System.currentTimeMillis();
		
		LOG.info("Creating the terrain took {} seconds!", (m2-m1)/1000f);
		
		Geometry g = new Geometry("Terrain", htm);
		Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setBoolean("VertexColor", false);
		mat.setTexture("ColorMap", getApplication().getAssetManager().loadTexture("tiles.JPG"));
		mat.getAdditionalRenderState().setWireframe(false);
		mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Back);
		g.setMaterial(mat);
		
		tileNode.attachChild(g);
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

	@Override
	public void onTilePick(VectorAS pos) {
		
	}

	@Override
	public boolean onLock(Board board) {
		draw(Game.get().getBoard().getTileMap());
		
		// if everything is completed, it must be successful
		return true;
	}

	@Override
	public boolean onUnlock(Board board) {
		return true;
	}
}
