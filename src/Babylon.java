
public class Babylon extends Wonder {

	public Babylon() {
		super("Babylon", "clay");
	}

	public String getResWonder(int stage) {
		if (stage == 1)
			return "clay clay";
		else if (stage == 2)
			return "wood wood wood";
		else
			return "clay clay clay clay";
	}

	public String runPhase2(Player p) {
		return "gain extra scientific symbol of their choice at the end of the game";
	}
}
