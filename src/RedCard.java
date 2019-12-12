public class RedCard extends Card {
	
	private int mp;
	
	public RedCard(String name, String color, String age, String cost, String chain, String freeCard) {
		super(name, color, age, cost, chain, freeCard);
		assignMP();
	}
	public RedCard(String inp) {
		super(inp);
		assignMP();
	}
	private void assignMP() {
		mp = getAge();
	}
	
	public int compareTo(Object o) {
		return super.compareTo(o);
	}
	
	public int getMP() {
		return mp;
	}

}
