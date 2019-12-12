import static java.lang.System.out;

import java.awt.FontFormatException;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BoardGUI {

	private ArrayList<Card> deck = new ArrayList<>();
	private MainFrame mf;
	private GameState gs;
	private boolean endOfAge;

	public BoardGUI() throws IOException, FontFormatException {
		gs = new GameState();
		nextAge();
		mf = new MainFrame("7 Wonders", gs);
		mf.addMouseListener((MouseListener) this);
		endOfAge = false;
	}

	public void createDeck() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("age" + gs.getAge() + ".txt"));
		StringTokenizer st;
		deck = new ArrayList<>();
		ArrayList<ActionCard> guildCards = new ArrayList<>();
		while (br.ready()) {
			st = new StringTokenizer(br.readLine(), ";");
			String name = st.nextToken();
			String color = st.nextToken();
			String age = st.nextToken();
			if (color.equals("brown") || color.equals("silver") || name.equals("caravansery") || name.equals("Forum"))
				deck.add(new ResourceCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if (color.equals("blue"))
				deck.add(new BlueCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if (color.equals("red"))
				deck.add(new RedCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if (color.equals("green"))
				deck.add(new GreenCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if (color.equals("gold"))
				deck.add(new ActionCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else {
				guildCards.add(new ActionCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			}
		}

		if (gs.getAge() == 3) {
			Collections.shuffle(guildCards);
			for (int i = 4; i >= 0; i--)
				guildCards.remove(i);
			for (Card c : guildCards)
				deck.add(c);
		}
		Collections.shuffle(deck);
	}

	public void printDeck() {
		Collections.sort(deck);
		System.out.println("DECK:");
		for (Card c : deck)
			System.out.println(c);
	}

	public void nextAge() {
		if (gs.getAge() == 3) {
			endOfAge = true;
			return;
		}
		LinkedHashMap<Player, ArrayList<Card>> ph = gs.getPlayerHands();

		gs.updateState(gs.getAge() + 1);
		gs.updateState(ph);
		try {
			createDeck();
		} catch (IOException e) {
			e.printStackTrace();
		}
		deal();
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void deal() {
		Collections.shuffle(deck);
		LinkedHashMap<Player, ArrayList<Card>> ph = gs.getPlayerHands();
		for (int i = 0; i < 3; i++)
			ph.put(gs.getPlayers()[i], new ArrayList<>());
		Iterator<Player> iter = ph.keySet().iterator();
		for (int i = 0; i < 3; i++) {
			Player cp = iter.next();
			ArrayList<Card> cc = ph.get(cp);
			for (int j = 6; j >= 0; j--) {
				cc.add(deck.remove(j));
				ph.put(cp, cc);
			}
			Collections.sort(ph.get(cp));
		}
		// out.println(ph);
		gs.updateState(ph);
		// out.println(gs.getPlayerHands());
	}

	public void printPlayerHand(int pNum) {
		out.println(gs.getPlayerHands().get(gs.getPlayer(pNum)));
	}

	public void passCards() {
		LinkedHashMap<Player, ArrayList<Card>> ph = gs.getPlayerHands();
		Iterator<Player> iter = ph.keySet().iterator();
		ArrayList<Card> temp;
		Player p1 = iter.next();
		Player p2 = iter.next();
		Player p3 = iter.next();
		if (gs.getAge() == 2) {
			temp = ph.get(p1);
			ph.put(p1, ph.get(p2));
			ph.put(p2, ph.get(p3));
			ph.put(p3, temp);
		} else {
			temp = ph.get(p1);
			ph.put(p1, ph.get(p3));
			ph.put(p3, ph.get(p2));
			ph.put(p2, temp);
		}
		gs.updateState(ph);
	}

	public void wageWar() {
		int age = gs.getAge();
		Player p1 = gs.getPlayer(1);
		Player p2 = gs.getPlayer(2);
		Player p3 = gs.getPlayer(3);
		p1.setLeft(p2);
		p1.setRight(p3);
		p1.wageWar(age);
		p2.setLeft(p3);
		p2.setRight(p1);
		p2.wageWar(age);
		p3.setLeft(p1);
		p3.setRight(p2);
		p3.wageWar(age);
	}

	public void run() throws IOException, FontFormatException {
		for(int i = 0; i < 3; i++) {
			createDeck();
			deal();
			mf.updateGraphics();
			for (int a = 0; a < 6; a++) {
				for(int j = 0; j < 3; j++) {
					gs.nextTurn();
					mf.updateGraphics();
				}
				passCards();
				mf.updateGraphics();
			}
			for(Player p: gs.getPlayerHands().keySet()) //adding last remaining card to discard pile
				gs.getDiscard().add(gs.getPlayerHands().get(p).get(0));
			wageWar();
			nextAge();
			mf.updateGraphics();
			if (endOfAge == true)
				gs.setEnd(endOfAge);
		}

		mf.updateGraphics();

		/*
		 * for (int x = 1; x < 4; x++) { try { createDeck(); } catch (IOException e) {
		 * e.printStackTrace(); } deal(); mf.updateGraphics(); for (int i = 0; i < 6;
		 * i++) { for (int j = 1; j < 4; j++) {
		 * 
		 * gs.nextTurn(); mf.updateGraphics(); } passCards(); mf.updateGraphics(); }
		 * 
		 * wageWar(); nextAge(); mf.updateGraphics();
		 * 
		 * }
		 */

	}
}
