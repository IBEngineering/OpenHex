package openhex.view.input;

import openhex.util.Listener;
import openhex.vec.fin.VectorAS;

public interface PickListener extends Listener {

	public void onTilePick(VectorAS tilePos);
	
}
