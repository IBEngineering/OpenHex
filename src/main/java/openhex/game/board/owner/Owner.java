package openhex.game.board.owner;

import java.util.Set;

public interface Owner {

	public void addOwnable(Ownable ownable);
	public void removeOwnable(Ownable ownable);
	
	public Set<Ownable> getOwnables();
	
}
