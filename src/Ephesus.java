
public class Ephesus extends Wonder {

	public Ephesus() {
		super("Ephesus", "papyrus");
	}

	public String getResWonder(int stage) {
		if (stage == 1)
			return "stone stone";
		else if (stage == 2)
			return "wood wood";
		else
			return "papyrus papyrus";
	}

	public int runPhase2() {
		return 9; //gives 9 coins
	}
}