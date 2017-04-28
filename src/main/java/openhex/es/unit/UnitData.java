package openhex.es.unit;

import java.util.UUID;

import com.simsilica.es.EntityComponent;

import openhex.game.board.owner.Ownable;
import openhex.game.board.owner.Owner;

public class UnitData implements EntityComponent, Ownable {

	private Owner owner;
	
	/**
	 * This value describes the type of unit (tank, soldier,
	 * etc.), and is not unique for this class!
	 */
	private final UUID unitId;
	
	public UnitData(UUID unitId) {
		this.unitId = unitId;
	}
	
	@Override
	public Owner getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	/**
	 * The value is unique for it's type, not for it's
	 * instance.
	 * @return
	 */
	public UUID getUnitId() {
		return unitId;
	}

}
