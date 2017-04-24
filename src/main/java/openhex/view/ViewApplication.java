package openhex.view;

import com.jme3.app.SimpleApplication;

import openhex.view.state.InputState;
import openhex.view.state.RenderState;

/**
 * Application for the client part of the game.
 * @author MisterCavespider
 *
 */
public class ViewApplication extends SimpleApplication {
	
	@Override
	public void simpleInitApp() {
		flyCam.setDragToRotate(true);
		flyCam.setMoveSpeed(65f);
		
		stateManager.attachAll(new RenderState(), new InputState());
	}

}
