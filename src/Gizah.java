
public class Gizah extends Wonder 	
{
	
	public Gizah()
	{
		super("Gizah", "stone");
	}
	
	public String getResWonder(int stage)
	{
		if(stage == 1)
			return "stone stone";
		else if(stage == 2)
			return "wood wood wood";
		else
			return "stone stone stone stone";
	}
	
	public int runPhase2()
	{
		return 5; //returns 5 vp
	}	
}