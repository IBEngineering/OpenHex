package openhex.view.mesh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import openhex.game.board.HexTile;
import openhex.vec.Vectors;
import openhex.vec.fin.VectorAS;

public class HexTerrainMesh extends Mesh {

	public int bufferSize = 1024;
	public float heightScale = 1/10f;
	public double heightTreshhold = 1.5e-2;	//0.015
	public float cornerScale = 0f;
	
	
	
	public int vPos, iPos = 0;
	
	public List<Vector3f> vertices = new ArrayList<>();
	public List<Integer> indices = new ArrayList<>();
	public List<ColorRGBA> colors = new ArrayList<>();
	public List<Vector2f> texCoord = new ArrayList<>();	//texCoord1

	private Map<VectorAS, HexTile> tilemap;
	private List<HexTile> tiles;
	
	public void bake(Map<VectorAS, HexTile> tilemap) {
		this.tilemap = tilemap;
		this.tiles = new ArrayList<>(tilemap.values());
		
		for (int i = 0; i < tiles.size(); i++) {
			HexTile tile = tiles.get(i);
			
			drawHexagon(tile.getPosition());
			
			for (int n=0; n<Vectors.neighbors.length; n++) {
				//for every neighbor...
				
				if(hasNeighbor(tile, n)) {
					drawWall(tile.getPosition(), getNeighborOf(tile, n).getPosition(), n);
				} else if(tile.getPosition().getH() > 0) {
					System.out.println("Needs a wall down @ " + n);
					VectorAS npos = getNeighborPosOf(tile, n);
					npos.setH(0);
					drawWall(tile.getPosition(), npos, n);
				}
			}
		}
		
		//drawWall(tiles[0], tiles[1], 0);
		//drawWall(tiles[0], tiles[2], 1);
		//drawWall(tiles[1], tiles[2], 2);
		
		System.out.println(vertices);
		System.out.println(indices);

		setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices.toArray(new Vector3f[vertices.size()])));
		setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(toPrimitve(indices.toArray(new Integer[indices.size()]))));
		setBuffer(Type.Color, 4, BufferUtils.createFloatBuffer(colors.toArray(new ColorRGBA[colors.size()])));
		setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord.toArray(new Vector2f[texCoord.size()])));
		
		updateBound();
	}
	
	public Vector3f[] trimArray(Vector3f[] arr) {
		int count = 0;
		for (Vector3f obj : arr) {
			if(obj != null) {
				count++;
			}
		}
		
		Vector3f[] rarr = new Vector3f[count];
		
		int index = 0;
		for (Vector3f obj : arr) {
			if(obj != null) {
				rarr[index++] = obj;
			}
		}
		return rarr;
	}
	
	public Integer[] trimArray(Integer[] arr) {
		int count = 0;
		for (Integer obj : arr) {
			if(obj != null) {
				count++;
			}
		}
		
		Integer[] rarr = new Integer[count];
		
		int index = 0;
		for (Integer obj : arr) {
			if(obj != null) {
				rarr[index++] = obj;
			}
		}
		return rarr;
	}
	
	public int[] toPrimitve(Integer[] arr) {
		int[] rarr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			rarr[i] = arr[i].intValue();
		}
		return rarr;
	}
	
	public HexTile getNeighborOf(HexTile t, int n) {
		VectorAS pos = getNeighborPosOf(t, n);
		return tilemap.get(pos);
	}
	
	public boolean hasNeighbor(HexTile t, int n) {
		VectorAS pos = (VectorAS) t.getPosition().add(Vectors.neighbors[n]);
		return tilemap.containsKey(pos);
	}
	
	public VectorAS getNeighborPosOf(HexTile t, int n) {
		return (VectorAS) t.getPosition().add(Vectors.neighbors[n]);
	}
	
	public void drawHexagon(VectorAS pos) {
		for(int i = 0 ; i < 6 ; i ++ ) {
			vertices.add( getCorner(i).add(toVector3f(pos)) );
			colors.add(ColorRGBA.Red);
		}
		
		drawUvMapping(0);
		
		int s = vPos;
		
		indices.add(s);
		indices.add(s+1);
		indices.add(s+2);
		
		indices.add(s);
		indices.add(s+2);
		indices.add(s+5);
		
		indices.add(s+2);
		indices.add(s+3);
		indices.add(s+5);
		
		indices.add(s+3);
		indices.add(s+4);
		indices.add(s+5);
		
		vPos += 6;
		iPos += 12;
	}
	
	private void drawUvMapping(int buffer) {
		texCoord.add(new Vector2f(.75f, 0f));
		texCoord.add(new Vector2f(1f, .5f));
		texCoord.add(new Vector2f(.75f, 1f));
		texCoord.add(new Vector2f(.25f, 1f));
		texCoord.add(new Vector2f(0f, .5f));
		texCoord.add(new Vector2f(.25f, 0f));
	}
	
	private void drawWall(VectorAS t, VectorAS u, int n) {
		if(Math.abs(t.getH() - u.getH()) > heightTreshhold) {
			Vector3f a = getVertexForNeighbor(n+5, t);
			Vector3f b = getVertexForNeighbor(n, t);
			Vector3f c = getVertexForNeighbor(n+2, u);
			Vector3f d = getVertexForNeighbor(n+3, u);
			drawWall(a, b, c, d);
		}
	}
	
	private void drawWall(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
		
		colors.add(ColorRGBA.Blue);
		colors.add(ColorRGBA.Blue);
		colors.add(ColorRGBA.Blue);
		colors.add(ColorRGBA.Blue);
		
		texCoord.add(new Vector2f(0,0));
		texCoord.add(new Vector2f(1,0));
		texCoord.add(new Vector2f(0,1));
		texCoord.add(new Vector2f(1,1));
		
		indices.add(vPos);
		indices.add(vPos+1);
		indices.add(vPos+2);
		
		indices.add(vPos);
		indices.add(vPos+2);
		indices.add(vPos+3);
		
		vPos += 4;
		iPos += 6;
	}
	
	private Vector3f getVertexForNeighbor(int n, VectorAS pos) {
		int i = (6-n)%6;
		return getCorner(i).add(toVector3f(pos));
	}
	
	private Vector3f getCorner(int i) {
		float angle = 2*FastMath.PI/6f * i + 2*FastMath.PI/12f;
		float x = FastMath.sin(angle);
		float z = FastMath.cos(angle);
		return new Vector3f(x,i*cornerScale,z);
	}
	
	private Vector3f toVector3f(VectorAS pos) {
		return Vectors.toVector3f(pos, 1f).multLocal(1, heightScale, 1);
	}
}
