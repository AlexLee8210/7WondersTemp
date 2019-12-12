public class GreenCard extends Card {
	
	private String science;
	
	public GreenCard(String name, String color, String age, String cost, String chain, String freeCard) {
		super(name, color, age, cost, chain, freeCard);
		assignScience();
	}
	public GreenCard(String inp) {
		super(inp);
		assignScience();
	}
	
	private void assignScience() {
		if(getName().equals("Apothecary") || getName().equals("Dispensary") || getName().equals("Lodge") || getName().equals("Academy")) {
			science = "math";
		}
		else if(getName().equals("Workshop") || getName().equals("Laboratory") || getName().equals("Observatory") || getName().equals("Study")) {
			science = "engineering";
		}
		else
			science = "literature";
	}
	
	public int compareTo(Object o) {
		return super.compareTo(o);
	}
	
	public String getScience() {
		return science;
	}

}
