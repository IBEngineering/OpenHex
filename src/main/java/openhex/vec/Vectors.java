package openhex.vec;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import openhex.vec.fin.VectorAD;
import openhex.vec.fin.VectorAS;
import openhex.vec.fin.VectorCD;
import openhex.vec.fin.VectorCS;

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
	
	/**
	 * Converts from VectorAD to VectorAS
	 * @param vec
	 * @return
	 */
	public static VectorAS toVectorAS(VectorAD vec) {
		int q = Math.round(vec.getQ());
		int r = Math.round(vec.getR());
		int h = Math.round(vec.getH());
		return new VectorAS(q,r,h);
	}
	
	/**
	 * Converts from VectorCS to VectorAS
	 * @param vec
	 * @return
	 */
	public static VectorAS toVectorAS(VectorCS vec) {
		int q = vec.getQ();
		int r = vec.getR();
		int h = vec.getH();
		return new VectorAS(q,r,h);
	}
	
	/**
	 * Converts from VectorCD to VectorAS through {@link Vectors#toVectorAS(VectorCS)}
	 * @param vec
	 * @return
	 */
	public static VectorAS toVectorAS(VectorCD vec) {
		return toVectorAS(toVectorCS(vec));
	}
	
	/**
	 * Converts from VectorAS to VectorAD
	 * @param vec
	 * @return
	 */
	public static VectorAD toVectorAD(VectorAS vec) {
		float r = (float)vec.getR();
		float q = (float)vec.getQ();
		float h = (float)vec.getH();
		return new VectorAD(r,q,h);
	}
	
	/**
	 * Converts from VectorCS to VectorAD through {@link Vectors#toVectorAD(VectorCD)}
	 * @param vec
	 * @return
	 */
	public static VectorAD toVectorAD(VectorCS vec) {
		return toVectorAD(toVectorCD(vec));
	}
	
	/**
	 * Converts from VectorCD to VectorAD
	 * @param vec
	 * @return
	 */
	public static VectorAD toVectorAD(VectorCD vec) {
		float q = vec.getQ();
		float r = vec.getR();
		float h = vec.getH();
		return new VectorAD(q,r,h);
	}
	
	/**
	 * Converts from VectorCD to VectorCS
	 * @param vec
	 * @return
	 */
	public static VectorCS toVectorCS(VectorCD vec) {
		int q = Math.round(vec.getQ());
		int r = Math.round(vec.getR());
		int s = Math.round(vec.getS());
		int h = Math.round(vec.getH());
		return new VectorCS(q,r,s,h);
	}
	
	/**
	 * Converts from VectorAS to VectorCS
	 * @param vec
	 * @return
	 */
	public static VectorCS toVectorCS(VectorAS vec) {
		int q = vec.getQ();
		int r = vec.getR();
		int s = -q-r;
		int h = vec.getH();
		return new VectorCS(q,r,s,h);
	}
	
	/**
	 * Converts from VectorAD to VectorCS through {@link Vectors#toVectorCS(VectorAD)}
	 * @param vec
	 * @return
	 */
	public static VectorCS toVectorCS(VectorAD vec) {
		return toVectorCS(toVectorAS(vec));
	}
	
	/**
	 * Converts from VectorCS to VectorCD
	 * @param vec
	 * @return
	 */
	public static VectorCD toVectorCD(VectorCS vec) {
		VectorCD r = new VectorCD();
		r.setR((float)vec.getR());
		r.setQ((float)vec.getQ());
		r.setS((float)vec.getS());
		r.setH((float)vec.getH());
		return r;
	}
	
	/**
	 * Converts from VectorAS to VectorCD through {@link Vectors#toVectorCD(VectorAS)}
	 * @param vec
	 * @return
	 */
	public static VectorCD toVectorCD(VectorAS vec) {
		return toVectorCD(toVectorAD(vec));
	}
	
	/**
	 * Converts from VectorAD to VectorCD
	 * @param vec
	 * @return
	 */
	public static VectorCD toVectorCD(VectorAD vec) {
		float q = vec.getQ();
		float r = vec.getR();
		float s = -q-r;
		float h = vec.getH();
		return new VectorCD(q,r,s,h);
	}
	
	public static final VectorAS[] neighbors = new VectorAS[] {
			new VectorAS(0, 1, 0),
			new VectorAS(1, 0, 0),
			new VectorAS(1, -1, 0),
			new VectorAS(0, -1, 0),
			new VectorAS(-1, 0, 0),
			new VectorAS(-1, 1, 0),
		};
	
	public static VectorAS[] directionsAS = {
		    new VectorAS(+1,  0,0), new VectorAS(+1, -1,0), new VectorAS( 0, -1,0),
		    new VectorAS(-1,  0,0), new VectorAS(-1, +1,0), new VectorAS( 0, +1,0) 

		    };
	
	public static ArrayList<VectorAS> getRing(VectorAS vec, int radius) {
		ArrayList<VectorAS> ring = new ArrayList<VectorAS>();
		VectorAS tmp = vec;

		for (int t = 0; t < radius; t++) {
			tmp = (VectorAS) tmp.add(directionsAS[4]);
		}
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < radius; j++) {
				tmp = (VectorAS) tmp.add(directionsAS[i]);
				ring.add(tmp);
			}
		}
		return ring;
	}
	
	public static int getTileDistance(VectorAS a, VectorAS b) {
		return (int) Vectors.toVectorCD(a).distanceToTile(Vectors.toVectorCD(b));
	}
}
