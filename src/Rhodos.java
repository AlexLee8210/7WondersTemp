public class Rhodos extends Wonder 	
{
	private String name, resource;
	public Rhodos()
	{
		super("Rhodos", "ore");
	}
	
	public String getResWonder(int stage)
	{
		if(stage == 1)
			return "wood wood";
		else if(stage == 2)
			return "clay clay";
		else
			return "ore ore ore ore";
	}
	
	public int runPhase2()
	{
		return 2;
	}
}