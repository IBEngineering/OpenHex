package openhex.run;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.scene.shape.Sphere;

import openhex.es.Identifier;
import openhex.es.ResourceDescriptor;
import openhex.es.ResourceTypes;
import openhex.game.Game;
import openhex.game.board.HexTile;
import openhex.game.unit.UnitFactory;
import openhex.util.DuplicateInstanceException;
import openhex.vec.fin.VectorAS;
import openhex.view.ViewApplication;

public class Test {

	private static final Logger LOG = LoggerFactory.getLogger(Test.class);
	
	public static void main(String[] args) {
		LOG.trace("Starting test...");
		
		VectorAS a = new VectorAS(0,0,0);
		VectorAS b = new VectorAS(0,0,0);
		
		LOG.info("Is VectorAS {} the same as VectorAS {}? {}", a, b, a.equals(b));
		
		LOG.trace("Creating game...");
		Game g;
		try {
			g = new Game();
		} catch (DuplicateInstanceException e) {
			LOG.error("Duplicate instance of Game! {}", e);
			LOG.error("How is that even possible?");
			LOG.error("Exitting application...");
			return;
		}
		
		LOG.trace("Adding a unit...");
		UnitFactory.createUnit(new Identifier(), UUID.randomUUID(), new ResourceDescriptor(true)
				.putResource(ResourceTypes.MESH, new Sphere(8, 8, 0.5f))
				);
		
		LOG.trace("Adding Tiles to game...");
		g.getBoard().addTile(new HexTile(new VectorAS(0, 0, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(1, 0, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(0, 1, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(-1, 0, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(0, -1, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(1, -1, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(-1, 1, 0), new ResourceDescriptor(true)));
		
		LOG.trace("Creating Application...");
		ViewApplication vapp = new ViewApplication();
		LOG.debug("Staring Application {}...", vapp);
		vapp.start();
		
		LOG.debug("Testing method 'main()' has returned");
	}
	
}
