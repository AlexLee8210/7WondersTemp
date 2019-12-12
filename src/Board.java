import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Iterator;
import static java.lang.System.*;

public class Board {
	
	private ArrayList<Card> deck;
	private ArrayList<Card> discard;
	private MainFrame mf;
	private GameState gs;
	private boolean vineyard;
	
	public Board() {
		deck = new ArrayList<>();
		discard = new ArrayList<>();
		gs = new GameState();
		nextAge();
	}
	
	public void createDeck() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("age" + gs.getAge() + ".txt"));
		StringTokenizer st;
		deck = new ArrayList<>();
		ArrayList<ActionCard> guildCards = new ArrayList<>();
		while(br.ready()) {
			st = new StringTokenizer(br.readLine(), ";");
			String name = st.nextToken();
			String color = st.nextToken();
			String age = st.nextToken();
			if(color.equals("brown") || color.equals("silver") || name.equals("caravansery") || name.equals("Forum"))
				deck.add(new ResourceCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if(color.equals("blue"))
				deck.add(new BlueCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if(color.equals("red"))
				deck.add(new RedCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if(color.equals("green"))
				deck.add(new GreenCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else if(color.equals("gold"))
				deck.add(new ActionCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			else {
				guildCards.add(new ActionCard(name, color, age, st.nextToken(), st.nextToken(), st.nextToken()));
			}
		}
		
		if(gs.getAge() == 3) {
			Collections.shuffle(guildCards);
			for(int i = 4; i >= 0; i--)
				guildCards.remove(i);
			for(Card c: guildCards)
				deck.add(c);
		}
		Collections.shuffle(deck);
	}
	
	public void printDeck() {
		Collections.sort(deck);
		System.out.println("DECK:");
		for(Card c: deck)
			System.out.println(c);
	}
	
	public void nextAge() {
		if(gs.getAge() == 3)
			return;
		LinkedHashMap<Player, ArrayList<Card>> ph = gs.getPlayerHands();
		
		gs.updateState(gs.getAge()+1);
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
		for(int i = 0; i < 3; i++)
			ph.put(gs.getPlayers()[i], new ArrayList<>());
		Iterator<Player> iter = ph.keySet().iterator();
		for(int i = 0; i < 3; i++) {
			Player cp = iter.next();
			ArrayList<Card> cc = ph.get(cp);
			for(int j = 6; j >= 0; j--) {
				cc.add(deck.remove(j));
				ph.put(cp, cc);
			}
			Collections.sort(ph.get(cp));
		}
		//out.println(ph);
		gs.updateState(ph);
		//out.println(gs.getPlayerHands());
	}
	
	public void discard(Card c) {
		gs.getCurrentPlayer().addCoins(3);
		discard.add(c);
		gs.getCurrentHand().remove(c);
	}
	public void build(Card c) {
		gs.getCurrentPlayer().addCard(c);
		LinkedHashMap<Player, ArrayList<Card>> hands = gs.getPlayerHands();
		hands.remove(c);
		gs.updateState(hands);
		if(c.getName().equals("Vineyard"))
				vineyard = true;
	}
	public boolean sacrifice(Card c) {
		if(gs.getCurrentPlayer().canBuildWonder()) {
			LinkedHashMap<Player, ArrayList<Card>> ph = gs.getPlayerHands();
			ph.remove(c);
			if(gs.getCurrentPlayer().getWonder().getName().equals("Halikarnassos"))
				gs.getCurrentPlayer().addCard(gs.getCurrentPlayer().buildWonder(discard));
			else
				gs.getCurrentPlayer().buildWonder();
			gs.updateState(ph);
			return true;
		}
		return false;
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
		if(gs.getAge() == 2) {
			temp = ph.get(p1);
			ph.put(p1, ph.get(p2));
			ph.put(p2, ph.get(p3));
			ph.put(p3, temp);
		}
		else {
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
		p1.wageWar(age);
		p2.wageWar(age);
		p3.wageWar(age);
	}
	
	public String runTurn(Scanner input) {
		ArrayList<Card> ch = gs.getCurrentHand();
		out.println("Player " + gs.getCurrentPlayerNum() + "'s turn");
		out.println("----------------------");
		out.println("This is your current hand:\n" + ch);
		out.println("----------------------");
		out.println("These are your cards:\n" + gs.getCurrentPlayer().getCards());
		out.println("These are your resources:\n" + gs.getCurrentPlayer().getResources());
		out.println("These are your choice resources:\n" + gs.getCurrentPlayer().getChoiceRes());
		out.println("You have " + gs.getCurrentPlayer().getCoins() + " coins.");
		out.println("----------------------");
		out.println("What would you like to do? (Enter number)\n1. Build\n2. Sacrifice\n3. Discard");
		int choice = input.nextInt();
		if(choice == 1) {
			out.println("Which card would you like to build? (Enter number: 0,1,2...)");
			Card bc = ch.get(input.nextInt());
			if(gs.getCurrentPlayer().canBuild(bc)) {
				build(bc);
				System.out.println("These are your cards: " + gs.getCurrentPlayer().getCards());
				return "Player " + gs.getCurrentPlayerNum() + " built " + bc;
			}
			else if(gs.getCurrentPlayer().canBuildWithTrade(bc) && gs.getCurrentPlayer().getWonder().getName().equals("Olympia")) {
				if(!((Olympia)gs.getCurrentPlayer().getWonder()).hasUsedFree(gs.getAge())) {
					out.println("You can build this card either by trading or with Phase 2 of your Wonder (Build a card every age for free).)");
					out.println("Would you like to trade or use your second wonder phase? (Trade/Wonder)");
					String c2 = input.next();
					if(c2.equalsIgnoreCase("wonder")) {
						build(bc);
						return "Player " + gs.getCurrentPlayerNum() + " built " + bc;
					}
					else if(c2.equalsIgnoreCase("trade")){
						gs.getCurrentPlayer().trade(bc);
						build(bc);
						return "Player " + gs.getCurrentPlayerNum() + " built " + bc;
					}
					else {
						out.println("Invlaid input");
						runTurn(input);
					}
				}
				else {
					out.println("In order to build this you must trade with other players. Would you like to Trade? (Y/N)");
					if(input.next().equalsIgnoreCase("y")) {
						gs.getCurrentPlayer().trade(bc);
						build(bc);
						return "Player " + gs.getCurrentPlayerNum() + " built " + bc;
					}
					else
						runTurn(input);
				}
			}
			else if(gs.getCurrentPlayer().canBuildWithTrade(bc)) {
				out.println("In order to build this you must trade with other players. Would you like to Trade? (Y/N)");
				if(input.next().equalsIgnoreCase("y")) {
					gs.getCurrentPlayer().trade(bc);
					build(bc);
					return "Player " + gs.getCurrentPlayerNum() + " built " + bc;
				}
				else
					runTurn(input);
			}
			else {
				out.println("You cannot do this, choose another option.");
				runTurn(input);
			}
		}
		else if(choice == 2) {
			out.println("Which card would you like to sacrifice to build a wonder? (Enter number: 0,1,2...)");
			int sc = input.nextInt();
			if(sacrifice(ch.get(sc))) {
				String built = "";
				int phase = 0;
				if(gs.getCurrentPlayer().getWonder().getPhaseState(1)) {
					built+="Phase 1 ";
					phase = 1;
				}
				if(gs.getCurrentPlayer().getWonder().getPhaseState(2)) {
					built+=", Phase 2 ";
					phase = 2;
				}
				if(gs.getCurrentPlayer().getWonder().getPhaseState(3)) {
					built+=", and Phase 3";
					phase = 3;
				}
				if(built.length() == 0)
					built = "nothing";
				out.println("You have built " + built + ".");
				out.println(gs.getCurrentPlayer().getWonder().getPhaseState(1));
				return "Player " + gs.getCurrentPlayerNum() + " sacrificed a card to build phase" + phase;
			}
			else {
				out.println("You cannot do this, choose another option.");
				out.println("----------------------");
				runTurn(input);
			}
		}
		else if(choice == 3) {
			out.println("Which card would you like to discard for 3 coins? (Enter number: 0,1,2...)");
			int dc = input.nextInt();
			discard(ch.get(dc));
			out.println("You now have " + gs.getCurrentPlayer().getCoins() + " coins.");
			return "Player " + gs.getCurrentPlayerNum() + " discarded a card and got 3 coins";
		}
		else {
			out.println("Invalid number.");
			runTurn(input);
		}
		return null;
	}
	public void run() {
		Scanner input = new Scanner(System.in);
		out.println("Player 1's wonder: " + gs.getPlayer(1).getWonder().getName());
		out.println("Player 2's wonder: " + gs.getPlayer(2).getWonder().getName());
		out.println("Player 3's wonder: " + gs.getPlayer(3).getWonder().getName());
		for(int x = 1; x < 4; x++) {
			try {
				createDeck();
			} catch (IOException e) {
				e.printStackTrace();
			}
			deal();
			for(int i = 0; i < 6; i++) {
				String[] effects = new String[3];
				for(int j = 1; j < 4; j++) {
					out.println("----------------------");
					effects[j-1] = runTurn(input);
					gs.nextTurn();
				}
				if(vineyard) {
					for(Player p: gs.getPlayers()) {
						if(p.hasCard("Vineyard")) {
							p.addCoins(p.getLeft().getBrownNum());
							p.addCoins(p.getRight().getBrownNum());
						}
					}
				}
				for(String k: effects)
					out.println(k);
				passCards();
			}
			for(Player p: gs.getPlayerHands().keySet()) //adding last remaining card to discard pile
				discard.add(gs.getPlayerHands().get(p).get(0));
			
			for(Player p: gs.getPlayers()) {
				out.println("Player " + p.getPlayerNum() + " wages war on player " + gs.getLeftPlayer().getPlayerNum());
				int result = p.wageWar(gs.getAge());
				if(result == 0)
					out.println("No one won");
				else {
					String mp = "5";
					if(gs.getAge() == 1)
						mp = "1";
					else if(gs.getAge() == 2)
						mp = "3";
					out.println(result + " won! Gained " + mp + " military points.");
				}
			}
			nextAge();
		}
		LinkedHashMap<Integer, String> scores = new LinkedHashMap<>();
		int p1Score = gs.getPlayer(1).score();
		int p2Score = gs.getPlayer(2).score();
		int p3Score = gs.getPlayer(3).score();
		scores.put(p1Score, "1");
		scores.put(p2Score, "2");
		scores.put(p3Score, "3");
		int win = Math.max(p1Score, Math.max(p2Score, p3Score));
		int third = Math.min(p1Score, Math.min(p2Score, p3Score));
		int second = 0;
		for(int s: scores.keySet())
			if(s!=win && s!=third)
				second = s;
		out.println("First Place: Player " + scores.get(win) + " Score: " + win);
		out.println("Second Place: Player " + scores.get(second) + " Score: " + second);
		out.println("Third Place: Player " + scores.get(third) + " Score: " + third);
		
		
		if(p1Score == win)
			out.println("Player 1 wins!");
		else if(p2Score == win)
			out.println("Player 2 wins!");
		else
			out.println("Player 3 wins!");
		gs.setEnd(true);
	}
}
