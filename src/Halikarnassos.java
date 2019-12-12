
public class Halikarnassos extends Wonder 	
{
	public Halikarnassos()
	{
		super("Halikarnassos", "cloth");
	}
	
	public String getResWonder(int stage)
	{
		if(stage == 1)
			return "clay clay";
		else if(stage == 2)
			return "ore ore ore";
		else
			return "cloth cloth";
	}
	
}
