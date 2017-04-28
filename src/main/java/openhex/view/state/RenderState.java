package openhex.view.state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

public class RenderState extends BaseAppState {

	private TileRenderState trs;
	private UnitRenderState urs;
	
	@Override
	protected void initialize(Application app) {
		trs = new TileRenderState();
		urs = new UnitRenderState();
		
		getStateManager().attachAll(trs, urs);
	}

	@Override
	protected void cleanup(Application app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onEnable() {}

	@Override
	protected void onDisable() {}

}
