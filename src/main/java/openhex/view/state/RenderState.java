package openhex.view.state;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import openhex.es.HexTile;
import openhex.pos.HexVector;
import openhex.pos.Vectors;
import openhex.view.mesh.HexMesh;

public class RenderState extends BaseAppState {
	
	private List<HexTile> hexTiles;
	
	@Override
	protected void initialize(Application app) {
		hexTiles = new ArrayList<>();
		
		hexTiles.add(new HexTile());
		hexTiles.add(new HexTile(new HexVector(1, -2, 1), (float)Math.random(), 1));
		hexTiles.add(new HexTile(new HexVector(-1, 2, -1), (float)Math.random(), 1));
		hexTiles.add(new HexTile(new HexVector(-1, 0, 1), (float)Math.random(), 1));
		
		for(HexTile t : hexTiles) {
			Geometry geom = new Geometry("Hex", new HexMesh(1f));
			geom.setMaterial(getMaterial());
			Vector2f planar = Vectors.toVector2f(t.getPosition(), 1f);
			geom.setLocalTranslation(new Vector3f(planar.y, t.getHeight()-0.5f, planar.x));
			((SimpleApplication)app).getRootNode().attachChild(geom);
		}
		
		
		Geometry geom2 = new Geometry("zero", new Sphere(8,8,0.3f));
		geom2.setMaterial(getMaterial());
		((SimpleApplication)app).getRootNode().attachChild(geom2);
		
		System.out.println("Drew something and a ball");
		
		
	}
	
	public Material getMaterial() {
		Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.randomColor());
		mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
		return mat;
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
