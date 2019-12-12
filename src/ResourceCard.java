import java.util.ArrayList;

public class ResourceCard extends Card {
	
	private ArrayList<String> resources;
	private boolean isChoice;
	
	public ResourceCard(String name, String color, String age, String cost, String chain, String freeCard) {
		super(name, color, age, cost, chain, freeCard);
		isChoice = false;
		assignResource();
	}
	public ResourceCard(String inp) {
		super(inp);
		isChoice = false;
		assignResource();
	}
	
	private void assignResource() {
		resources = new ArrayList<>();
		if(getColor().equals("silver")) {
			if(getName().equals("Press"))
				resources.add("papyrus");
			else if(getName().equals("Glassworks"))
				resources.add("glass");
			else
				resources.add("cloth");
		}
		else if(getAge() == 1){
			if(getName().equals("Lumber Yard"))
				resources.add("wood");
			else if(getName().equals("Stone Pit"))
				resources.add("stone");
			else if(getName().equals("Clay Pool"))
				resources.add("clay");
			else if(getName().equals("Ore Vein"))
				resources.add("ore");
			else if(getName().equals("Clay Pit")) {
				resources.add("clay");
				resources.add("ore");
				isChoice = true;
			}
			else {	//Timber Yard
				resources.add("stone");
				resources.add("wood");
				isChoice = true;
			}
		}
		else {
			if(getName().equals("Sawmill")) {
				resources.add("wood");
				resources.add("wood");
			}
			else if(getName().equals("Quarry")) {
				resources.add("stone");
				resources.add("stone");
			}
			else if(getName().equals("Brickyard")) {
				resources.add("stone");
				resources.add("stone");
			}
			else if(getName().equals("Foundry")) {
				resources.add("ore");
				resources.add("ore");
			}
			else if(getName().equals("Forum")) {
				resources.add("clay");
				resources.add("stone");
				resources.add("ore");
				resources.add("wood");
				isChoice = true;
			}
			else if(getName().equals("Caravansery")) {
				resources.add("cloth");
				resources.add("glass");
				resources.add("papyrus");
				isChoice = true;
			}
		}
	}
	
	public int compareTo(Object o) {
		return super.compareTo(o);
	}
	
	public ArrayList<String> getResources() {
		return resources;
	}
	
	public boolean isChoice() {
		return isChoice;
	}

}
