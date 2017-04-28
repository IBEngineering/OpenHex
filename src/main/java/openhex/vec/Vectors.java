package openhex.vec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import openhex.vec.fin.VectorAS;

/**
 * Conversion singleton for {@link Vector}s.
 * 
 * @author MisterCavespider
 *
 */
public class Vectors {
	
	private static Logger LOG = LoggerFactory.getLogger(Vectors.class);
	
	public static Vector3f toVector3f(Vector2f vec, float y) {
		return new Vector3f(vec.x, y, vec.y);
	}
	
	public static Vector2f toVector2f(Vector3f vec) {
		return new Vector2f(vec.x, vec.z);
	}
	
	/**
	 * Flat top hexagonal (cube) coordinates.
	 * @param vec
	 */
	public static VectorAS toVectorAS(Vector3f vec, double size) {
		//see redblobgames
		//for some reason q and r are not in order here
		double q = vec.x * 2d/3d / size;
		double r = (-vec.x / 3d + Math.sqrt(3d)/3d * vec.z) / size;
		
		LOG.trace("Unrounded: {}, {}", r, q);
		
		int rq = (int) Math.round(q);
		int rr = (int) Math.round(r);
		
		LOG.trace("Rounded: {}, {}", rr, rq);
		
		return new VectorAS(rr, rq, (int)vec.y);
	}
	
	public static Vector2f toVector2f(VectorAS vec, double size) {
		float x = (float) (size * 3f/2f * vec.getR());
		float y = (float) (size * Math.sqrt(3) * (vec.getQ() + vec.getR()/2.0));
		return new Vector2f(x, y);
	}
	
	public static Vector3f toVector3f(VectorAS vec, double size) {
		Vector2f plane = toVector2f(vec, size);
		return new Vector3f(plane.x, vec.getH(), plane.y);
	}
	
	/*
	public static HexVector toHex(Position pos) {
        HexPosition hpos = new HexPosition(pos.x, pos.y, pos.z);
        return hpos;
    }
    
    public static Vector2f toVector2f(HexPosition pos, boolean alongXaxis) {
        float x = 1f * 3f/2f * pos.z;
        float z = 1f * FastMath.sqrt(3) * (pos.x + pos.z/2f);
        
        return new Vector2f(x, z);
    }
    
    public static Vector3f toVector3f(HexPosition pos, boolean alongXaxis) {
        Vector2f original = toVector2f(pos, alongXaxis);
        return new Vector3f(original.x, 0, original.y);
    }
    
    public static HexPosition toHex(Vector2f vec) {
        //See redblobgames
        double q = (vec.x * 2d/3d / 1d);
        double r = (-vec.x / 3d + Math.sqrt(3d)/3d * vec.y) / 1d;
        
        System.out.println("UNROUNDED: " + q + "," + (-q-r) + "," + r);
        
        int rx = (int) Math.round(q);
        int ry = (int) Math.round(-q-r);
        int rz = (int) Math.round(r);
        
        return new HexPosition(rz, ry, rx);
    }*/
	
}
