package openhex.view.mesh;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

public abstract class DynaMesh extends Mesh {

	protected Vector3f[]	vertices;
	protected ColorRGBA[]	colors;
	protected Vector2f[]	texCoords;
	protected short[]			indices;
	
	public void applyBuffers() {
        //Calls to update all containers
        generateVertices();
        generateTexCoords();
        generateIndices();
        
        //The actual buffers
        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
        setBuffer(VertexBuffer.Type.Index, 3, indices);
    }
    
    protected abstract void generateVertices();
    protected abstract void generateTexCoords();
    protected abstract void generateIndices();
	
}
