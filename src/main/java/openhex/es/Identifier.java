package openhex.es;

import java.util.UUID;

import com.simsilica.es.EntityComponent;

public class Identifier implements EntityComponent {
	
	private final UUID id;
	
	public Identifier() {
		this(UUID.randomUUID());
	}
	
	public Identifier(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}
	
}
