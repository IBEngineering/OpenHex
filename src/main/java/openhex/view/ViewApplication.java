package openhex.view;

import com.jme3.app.SimpleApplication;

import openhex.view.state.InputState;
import openhex.view.state.RenderState;

public class ViewApplication extends SimpleApplication {
	
	@Override
	public void simpleInitApp() {
		flyCam.setDragToRotate(true);
		
		stateManager.attachAll(new RenderState(), new InputState());
	}

}
