package openhex.view;

import com.jme3.app.SimpleApplication;

import openhex.view.state.RenderState;

public class ViewApplication extends SimpleApplication {
	
	@Override
	public void simpleInitApp() {
		stateManager.attachAll(new RenderState());
	}

}
