import java.util.ArrayList;

public abstract class Card implements Comparable {
	
	private int age;
	private ArrayList<String> cost, freeCard, chain;
	private String name, color;
	
	public Card(String name, String color, String age, String cost, String chain, String freeCard)
	{
		this.name = name;
		this.color = color;
		this.age = Integer.parseInt(age);
		this.cost = new ArrayList<>();
		this.chain = new ArrayList<>();
		this.freeCard = new ArrayList<>();
		String[] c = cost.split(", ");
		for(String k: c)
			this.cost.add(k);
		String[] ch = chain.split(", ");
		for(String k: ch)
			this.chain.add(k);
		String[] fc = freeCard.split(", ");
		for(String k: fc)
			this.freeCard.add(k);
	}
	public Card(String input)
	{
		String[] arr = input.split(";");
		this.name = arr[0];
		this.color = arr[1];
		this.age = Integer.parseInt(arr[2]);
		
		this.cost = new ArrayList<>();
		this.chain = new ArrayList<>();
		this.freeCard = new ArrayList<>();
		
		String[] c = arr[3].split(", ");
		for(String k: c)
			this.cost.add(k);
		
		String[] ch = arr[4].split(", ");
		for(String k: ch)
			this.chain.add(k);
		
		String[] fc = arr[5].split(", ");
		for(String k: fc)
			this.freeCard.add(k);
	}
	
	public String getName() { return name; }
	public int getAge() { return age; }
	public ArrayList<String> getCost() 	{ return cost; }
	public ArrayList<String> getChain() { return chain; }
	public String getColor() { return color; }
	public ArrayList<String> getFree() { return freeCard; }
	
	public int compareTo(Object o)
	{
		Card card = (Card) o;
		int num1 = 0;
		int num2 = 0;
		if(color.equals("brown"))
			num1 = 0;
		else if(color.equals("silver"))
			num1 = 1;
		else if(color.equals("green"))
			num1 = 2;
		else if(color.equals("blue"))
			num1 = 3;
		else if(color.equals("red"))
			num1 = 4;
		else if(color.equals("gold"))
			num1 = 5;
		else
			num1 = 6;
		
		if(card.getColor().equals("brown"))
			num2 = 0;
		else if(card.getColor().equals("silver"))
			num2 = 1;
		else if(card.getColor().equals("green"))
			num2 = 2;
		else if(card.getColor().equals("blue"))
			num2 = 3;
		else if(card.getColor().equals("red"))
			num2 = 4;
		else if(card.getColor().equals("gold"))
			num2 = 5;
		else
			num2 = 6;
		
		if(num1-num2!=0)
			return num1-num2;
		else
			return name.compareTo(card.getName());
	}
	
	public String toString() { 
		String cos = "";
		if(cost.size() == 1 && cost.get(0).equals(" ") || cost.size() == 0)
			cos = "nothing";
		else {
			for(int i = 0; i < cost.size()-1; i++)
				cos+=cost.get(i) + ", ";
			cos+=cost.get(cost.size()-1);
		}

		return name + " - Cost: " + cos; }
}
