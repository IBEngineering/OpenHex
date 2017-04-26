package openhex.game.board;

public interface BoardChangeListener extends BoardListener {

	public boolean onAddedTile(Board board, HexTile tile);
	public boolean onRemovedTile(Board board, HexTile tile);
	
}
