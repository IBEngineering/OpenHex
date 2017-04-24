package openhex.es;

/**
 * 
 * @author MisterCavespider
 *
 */
public enum ResourceTypes implements ResourceType {
	COLOR,
	MESH,
	MODEL,
	TEXTURE;

	@Override
	public boolean equals(ResourceType that) {
		return this.equals((Object)that);
	}

	@Override
	public boolean equals(String that) {
		return this == valueOf(that);
	}
	
}
