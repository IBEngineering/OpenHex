package openhex.run;

import openhex.es.ResourceDescriptor;
import openhex.game.Game;
import openhex.game.board.HexTile;
import openhex.vec.fin.VectorAS;
import openhex.view.ViewApplication;

public class Test {

	public static void main(String[] args) {
		System.out.println(new VectorAS(0, 0, 0).equals(new VectorAS(0, 0, 0)));
		
		Game g = new Game();
		
		g.getBoard().addTile(new HexTile(new VectorAS(0, 0, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(1, 0, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(0, 1, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(-1, 0, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(0, -1, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(1, -1, 0), new ResourceDescriptor(true)));
		g.getBoard().addTile(new HexTile(new VectorAS(-1, 1, 0), new ResourceDescriptor(true)));
		
		ViewApplication vapp = new ViewApplication();
		vapp.start();
		
		
	}
	
}
