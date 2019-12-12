import java.util.TreeMap;

public abstract class Wonder {
	private TreeMap<Integer, Boolean> phases;
	private String name, resource, effect;

	public Wonder(String name, String resource) {
		phases = new TreeMap<>();
		phases.put(1, false);
		phases.put(2, false);
		phases.put(3, false);
		this.name = name;
		this.resource = resource;
	}
	
	public abstract String getResWonder(int stage);
	
	public String getName() {
		return name;
	}
	
	public boolean getPhaseState(int stage) {
		return phases.get(stage);
	}

	public int getPhase1() {
		return 3;
	}

	public int getPhase3() {
		return 7;
	}

	public String getResource() {
		return resource;
	}

	public String getEffect() {
		return effect;
	}
	
	public void setPhase(int stage) {
		phases.put(stage, true);
	}
	
	public String toString() { return name; }
}
