package openhex.view.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

import openhex.es.HexTile;
import openhex.pos.Vectors;
import openhex.view.mesh.HexMesh;

public class RenderState extends BaseAppState {
	
	@Override
	protected void initialize(Application app) {
		HexTile t = new HexTile();
		
		Geometry geom = new Geometry("Hex", new HexMesh(1f));
		Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.randomColor());
		geom.setMaterial(mat);
		geom.setLocalTranslation(Vectors.toVector3f(t.getPosition(), t.getHeight()));
		((SimpleApplication)app).getRootNode().attachChild(geom);
	}

	@Override
	protected void cleanup(Application app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onEnable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDisable() {
		// TODO Auto-generated method stub
		
	}

}
