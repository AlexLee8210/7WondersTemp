
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

public class Runner {
	public static void main(String[] args) throws IOException, FontFormatException {

		GameState gs = new GameState();

		BlueCard altar = new BlueCard("Altar", "blue", "1", " ", "Temple", " ");
		BlueCard palace = new BlueCard("Palace;blue;3;glass, papyrus, cloth, clay, wood, ore, stone; ; ");
		BlueCard pantheon = new BlueCard("Pantheon;blue;3;clay, clay, ore, papyrus, cloth, glass; ;Temple");
		GreenCard academy = new GreenCard("Academy;green;3;stone, stone, stone, glass; ;School");
		ResourceCard stone = new ResourceCard("StonePit;brown;1; ; ; ");

		ActionCard worker = new ActionCard("WorkersGuild;purple;3;ore, ore, clay,stone, wood; ; ");
		RedCard barracks = new RedCard("Barracks;red;1;ore; ; ");

		ArrayList<Card> cards = new ArrayList<>();
		cards.add(altar);
		cards.add(palace);
		cards.add(pantheon);
		cards.add(academy);
		cards.add(stone);
		cards.add(barracks);
		gs.updateState(cards);

		MainFrame f = new MainFrame("Seven Wonders", gs);

		// f.updateCurrentAge(2);
		// f.updateCurrentPlayer(two);
		// f.updatePlayerCoins();

	}
}
