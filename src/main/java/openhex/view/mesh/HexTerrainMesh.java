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
	
	public Vector3f[] vertices = new Vector3f[1024];
	public Integer[] indices = new Integer[1024];
	public ColorRGBA[] colors = new ColorRGBA[1024];
	public Vector2f[][] texCoords = new Vector2f[8][1024];

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

		setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(trimArray(vertices)));
		setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(toPrimitve(trimArray(indices))));
		setBuffer(Type.Color, 4, BufferUtils.createFloatBuffer(colors));
		setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords[0]));
		
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
			vertices[vPos + i] = getCorner(i).add(Vectors.toVector3f(pos, 1f).multLocal(1, heightScale, 1));
			colors[vPos + i] = ColorRGBA.Red;
		}
		
		drawUvMapping(0);
		
		int s = vPos;
		indices[iPos] = s;
		indices[iPos+1] = s+1;
		indices[iPos+2] = s+2;
		indices[iPos+3] = s;
		indices[iPos+4] = s+2;
		indices[iPos+5] = s+5;
		indices[iPos+6] = s+2;
		indices[iPos+7] = s+3;
		indices[iPos+8] = s+5;
		indices[iPos+9] = s+3;
		indices[iPos+10] = s+4;
		indices[iPos+11] = s+5;
		
		vPos += 6;
		iPos += 12;
	}
	
	public void drawUvMapping(int buffer) {
		texCoords[buffer][vPos] = new Vector2f(.75f, 0f);
		texCoords[buffer][vPos+1] = new Vector2f(1f, .5f);
		texCoords[buffer][vPos+2] = new Vector2f(.75f, 1f);
		texCoords[buffer][vPos+3] = new Vector2f(.25f, 1f);
		texCoords[buffer][vPos+4] = new Vector2f(0f, .5f);
		texCoords[buffer][vPos+5] = new Vector2f(.25f, 0f);
	}
	
	public void drawWall(VectorAS t, VectorAS u, int n) {
		if(Math.abs(t.getH() - u.getH()) > heightTreshhold) {
			Vector3f a = getVertexForNeighbor(n+5, t);
			Vector3f b = getVertexForNeighbor(n, t);
			Vector3f c = getVertexForNeighbor(n+2, u);
			Vector3f d = getVertexForNeighbor(n+3, u);
			drawWall(a, b, c, d);
		}
	}
	
	public void drawWall(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
		vertices[vPos] = a;
		vertices[vPos+1] = b;
		vertices[vPos+2] = c;
		vertices[vPos+3] = d;
		
		colors[vPos] = ColorRGBA.Blue;
		colors[vPos+1] = ColorRGBA.Blue;
		colors[vPos+2] = ColorRGBA.Blue;
		colors[vPos+3] = ColorRGBA.Blue;
		
		indices[iPos] = vPos;
		indices[iPos+1] = vPos+1;
		indices[iPos+2] = vPos+2;
		
		indices[iPos+3] = vPos;
		indices[iPos+4] = vPos+2;
		indices[iPos+5] = vPos+3;
		
		vPos += 4;
		iPos += 6;
	}
	
	public Vector3f getVertexForNeighbor(int n, VectorAS pos) {
		int i = (6-n)%6;
		return getCorner(i).add(Vectors.toVector3f(pos, 1f).multLocal(1, heightScale, 1));
	}
	
	public Vector3f[] getVerticesOfHexagon(VectorAS pos) {
		Vector3f[] arr = new Vector3f[6];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = getCorner(i).add(Vectors.toVector3f(pos, 1f).multLocal(1, heightScale, 1));
		}
		return arr;
	}
	
	public Vector3f getCorner(int i) {
		float angle = 2*FastMath.PI/6f * i + 2*FastMath.PI/12f;
		float x = FastMath.sin(angle);
		float z = FastMath.cos(angle);
		return new Vector3f(x,i*cornerScale,z);
	}
	
	public int getIndexOf(HexTile t) {
		if(t == null) {
			return -1;
		}
		for (int i = 0; i < tiles.size(); i++) {
			HexTile tile = tiles.get(i);
			if(t.getPosition().equals(tile.getPosition())) {
				return i;
			}
		}
		return -1;
	}
	
}
