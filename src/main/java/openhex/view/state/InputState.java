package openhex.view.state;

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
import com.simsilica.es.Entity;
import com.simsilica.es.EntityChange;
import com.simsilica.es.EntitySet;

import openhex.es.EventComponent;
import openhex.es.filter.HexVectorFilter;
import openhex.event.PickingEvent;
import openhex.pos.HexVector;
import openhex.pos.Vectors;

public class InputState extends BaseAppState implements ActionListener {

	public static final String MAPPING_PICK = "InputState.PICK";
	
	@Override
	protected void initialize(Application app) {
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
		System.out.println("Raycasting...");
		
		Camera cam = getApplication().getCamera();
		
		Vector2f clickPos = getApplication().getInputManager().getCursorPosition();
		Vector3f spacePos = cam.getWorldCoordinates(clickPos, 0);
		Vector3f dir = cam.getWorldCoordinates(clickPos, 1f);
		
		float distance = FastMath.abs(spacePos.y / dir.y);
		Vector3f groundIntersect = spacePos.add(dir.mult(distance));
		HexVector pos = (HexVector) Vectors.toHexVector(groundIntersect);
		
		System.out.println("Position: " + pos.toString());
		
		EntitySet foundEntities = getState(RenderState.class).getEntityData().getEntities(new HexVectorFilter(pos), EventComponent.class);
		boolean found = false;
		for(Entity e : foundEntities) {
			if(!found) { found = true; }
			e.get(EventComponent.class).addEvent(new PickingEvent());
		}
		
		System.out.println("Target found: " + found);
	}
}
