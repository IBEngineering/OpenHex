package openhex.view.mesh;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 * 
 * @author MisterCavespider
 *
 */
public class HexMesh extends DynaMesh {

	protected float radius = 1f;
	
	public HexMesh(float radius) {
		this.radius = radius;
		applyBuffers();
	}

	@Override
	protected void generateVertices() {
		//length of 7, 1 in the middle, 6 on the vertices
		Vector3f[] vec = new Vector3f[7];
        
        //First vertex is in the middle
        vec[0] = Vector3f.ZERO;
        
        //The rest goes through another method
        for(int i=0; i<6; i++) {
            vec[i+1] = calcHexVertexSpace(i);
        }
        
        vertices = vec;
	}

	protected Vector2f calcHexVertex(int corner) {
		float angle = 2*FastMath.PI/6f * corner + 2*FastMath.PI/12f;
        float x = radius * FastMath.sin(angle);
        float y = radius * FastMath.cos(angle);
        Vector2f r = new Vector2f(x, y);
        
        return r;
	}
	
	protected Vector3f calcHexVertexSpace(int corner) {
		Vector2f o = calcHexVertex(corner);
		return new Vector3f(o.x, 0, o.y);
	}
	
	@Override
	protected void generateTexCoords() {
		Vector2f[] tex = new Vector2f[7];
        
        //First vertex is in the middle
        tex[0] = new Vector2f(.5f, .5f);
        
        //The rest goes through another method
        for(int i=0; i<6; i++) {
            tex[i+1] = calcHexVertex(i).addLocal(1f, 1f).divideLocal(2f);
        }
        
        texCoords = tex;
	}

	@Override
	protected void generateIndices() {
		short[] ind = new short[6*3];
        
        for(short i=0; i<6; i++) {
            // 0 
            // i + 1
            // (i + 2) % 6
            
            ind[i*3  ] = (short) ( 0 );
            ind[i*3+1] = (short) ( i+1 );
            ind[i*3+2] = (short) ( (i+1)%6 + 1);   //Can't get out od bounds
        }
        
        indices = ind;
	}
	
}
