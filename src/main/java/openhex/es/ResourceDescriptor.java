package openhex.es;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.simsilica.es.EntityComponent;

import openhex.view.mesh.HexMesh;

/**
 * Describes the resources used by the Entity
 * through a Map.
 * 
 * @author MisterCavespider
 *
 */
public class ResourceDescriptor implements EntityComponent {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResourceDescriptor.class);
	
	private static void loadStandardValues(ResourceDescriptor desc) {
		desc.putResource(ResourceTypes.COLOR,	ColorRGBA.White)
			.putResource(ResourceTypes.MESH,	new HexMesh(1f))
			.putResource(ResourceTypes.TEXTURE, "Textures/Tiles/mr-tjpg.jpeg");
			;
	}
	
	private Map<ResourceType, Object> resources;
	
	public ResourceDescriptor() {
		this(true);
	}
	
	public ResourceDescriptor(boolean defValues) {
		resources = new HashMap<>();
		
		if(defValues) {
			loadStandardValues(this);
		}
	}
	
	/**
	 * Gets an Object straight from the map.
	 * This can be null.
	 * 
	 * @param res	Desired ResourceType
	 * @return		Potential object/resource
	 */
	public Object getResource(ResourceType res) {
		return resources.get(res);
	}
	
	/**
	 * Gets a resource T out of the map.
	 * Uses reflection.
	 * 
	 * If null is returned, either the resource is null
	 * or the resource can't be casted to T.
	 * 
	 * @param res	Desired ResourceType
	 * @param c		Desired class of the return object
	 * @return		Potential object/resource of class T
	 */
	public <T> T getResource(ResourceType res, Class<T> c) {
		Object o = getResource(res);
		//check instance
		if(c.isInstance(o)) {
			//cast
			return c.cast(o);
		} else {
			LOG.warn("Can't cast {} to {}! Returning null", o, c);
			return null;
		}
	}
	
	public ColorRGBA getColor(ResourceType res) {
		return getResource(res, ColorRGBA.class);
	}
	
	public Material getMaterial(ResourceType res) {
		return getResource(res, Material.class);
	}
	
	public Mesh getMesh(ResourceType res) {
		return getResource(res, Mesh.class);
	}
	
	public String getString(ResourceType res) {
		return getResource(res, String.class);
	}
	
	/**
	 * 
	 * @param res
	 * @param o
	 * @return <code>this</code>, for method chaining
	 */
	public ResourceDescriptor putResource(ResourceType res, Object o) {
		resources.put(res, o);
		return this;
	}
}
