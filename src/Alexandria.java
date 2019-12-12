import java.util.ArrayList;

public class Alexandria extends Wonder {

	public Alexandria() {
		super("Alexandria", "glass");
	
	}

	public String getResWonder(int stage) {
		if (stage == 1)
			return "clay clay";
		else if (stage == 2)
			return "wood wood wood";
		else
			return "clay clay clay clay";
	}

	public ArrayList<String> runPhase2()
	{
		ArrayList<String> list = new ArrayList<String>();
		list.add("stone");
		list.add("clay");
		list.add("wood");
		list.add("ore");
		return list;
	}
}
