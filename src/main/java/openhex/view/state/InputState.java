package openhex.view.state;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import openhex.util.Notifier;
import openhex.vec.Vectors;
import openhex.vec.fin.VectorAS;
import openhex.view.input.PickListener;

/**
 * Handles all input from jME.
 * 
 * To remove a mapping, use the {@link InputManager}
 * and constants like {@link InputState#MAPPING_PICK}.
 * 
 * @author MisterCavespider
 *
 */
public class InputState extends BaseAppState implements Notifier<PickListener>, ActionListener {
	
	public static final String MAPPING_PICK = "InputState.PICK";
	
	private static final Logger LOG = LoggerFactory.getLogger(InputState.class);
	
	private Set<PickListener> listeners;
	
	@Override
	protected void initialize(Application app) {
		listeners = new HashSet<>();
		
		InputManager input = app.getInputManager();
		
		input.addMapping(MAPPING_PICK,
			new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		
		input.addListener(this, MAPPING_PICK);
	}

	@Override
	protected void cleanup(Application app) {
		InputManager input = app.getInputManager();
		
		input.removeListener(this);
		input.deleteMapping(MAPPING_PICK);
	}

	@Override
	protected void onEnable() {}

	@Override
	protected void onDisable() {}
	
	
	
	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		switch (name) {
		case MAPPING_PICK:
			if(isPressed) { ray(); }
			break;
		}
	}
	
	private void ray() {
		LOG.debug("Raycasting...");
		
		Camera cam = getApplication().getCamera();
		
		Vector2f clickPos = getApplication().getInputManager().getCursorPosition();
		Vector3f spacePos = cam.getWorldCoordinates(clickPos, 0);
		Vector3f dir = cam.getWorldCoordinates(clickPos, 1f).subtractLocal(spacePos).normalizeLocal();
		
		float distance = FastMath.abs(spacePos.y / dir.y);
		Vector3f groundIntersect = spacePos.add(dir.mult(distance));
		VectorAS pos = Vectors.toVectorAS(groundIntersect, 1f);
		
		// ALERT ALL LISTENERS
		
		for(PickListener l : listeners) {
			l.onTilePick(pos);
		}
	}
	
	@Override
	public void addListener(PickListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(PickListener listener) {
		listeners.remove(listener);
	}

	@Override
	public boolean isListening(PickListener listener) {
		return listeners.contains(listener);
	}
}
