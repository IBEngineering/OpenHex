package openhex.es;

/**
 * 
 * @author MisterCavespider
 *
 */
public interface ResourceType {

	/**
	 * Is this ResourceType the same as
	 * that ResourceType?
	 * 
	 * @param that	The other ResourceType
	 * @return		Whether this == that
	 */
	public boolean equals(ResourceType that);
	/**
	 * Is this ResourceType the same as
	 * that ResourceType?
	 * 
	 * @param that	A String, representing a ResourceType.
	 * @return		Whether this == that
	 */
	public boolean equals(String that);
	
}
