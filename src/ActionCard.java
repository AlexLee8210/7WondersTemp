import java.util.ArrayList;

public class ActionCard extends Card{
	private String effect;
	
	public ActionCard(String name,String color,String age,String cost,String chain, String freeCard)
	{
		super(name,color,age,cost,chain,freeCard);
		setEffect();
	}
	public ActionCard(String inp)
	{
		super(inp);
		setEffect();
	}
	private void setEffect()
	{
		if(getAge() == 1)
		{
			if(getName().equals("East Trading Post"))
				effect = "right 1";
			else if(getName().equals("West Trading Post"))
				effect = "left 1";
			else if(getName().equals("Marketplace"))
				effect = "both 1";
		}
		else if(getAge() == 2)
		{
			if(getName().equals("Forum"))
				effect = "loom glass papyrus";
			else if(getName().equals("Caravansery"))
				effect = "clay stone ore wood";
		}
	}
	public String getEffect()
	{
		return effect;
	}
}
