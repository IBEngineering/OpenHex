package openhex.game.board;

public interface BoardLockListener extends BoardListener {

	public boolean onLock(Board board);
	public boolean onUnlock(Board board);
	
}
