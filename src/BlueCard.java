import java.util.ArrayList;

public class BlueCard extends Card {
	
	private int vp;
	
	public BlueCard(String name, String color, String age, String cost, String chain, String freeCard) {
		super(name, color, age, cost, chain, freeCard);
		assignVP();
	}
	public BlueCard(String inp) {
		super(inp);
		assignVP();
	}
	private void assignVP() {
		if(getAge() == 1) {
			if(getName().equals("Baths"))
				vp = 3;
			else
				vp = 2;
		}
		else if(getAge() == 2) {
			if(getName().equals("Aqueduct"))
				vp = 5;
			else if(getName().equals("Temple"))
				vp = 3;
			else
				vp = 4;
		}
		else {
			if(getName().equals("Pantheon"))
				vp = 7;
			else if(getName().equals("Gardens"))
				vp = 5;
			else if(getName().equals("Town Hall"))
				vp = 6;
			else
				vp = 8;
		}
	}
	
	public int compareTo(Object o) {
		return super.compareTo(o);
	}
	
	public int getVP() {
		return vp;
	}

}
