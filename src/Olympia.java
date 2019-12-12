import java.util.LinkedHashMap;

public class Olympia extends Wonder {
	
	private LinkedHashMap<Integer, Boolean> builtFree;
	public Olympia() {
		super("Olympia", "wood");
		builtFree = new LinkedHashMap<>();
		for(int i = 1; i < 4; i++)
			builtFree.put(i, true);
	}

	public String getResWonder(int stage) {
		if (stage == 1)
			return "wood wood";
		else if (stage == 2)
			return "stone stone";
		else
			return "ore ore";
	}
	
	public void makePhase2(int age) {
		for(int i = age; i < 4; i++)
			builtFree.put(i, false);
	}
	public void use(int age) {
		builtFree.put(age, true);
	}
	public boolean hasUsedFree(int age) {
		return builtFree.get(age);
	}
}
