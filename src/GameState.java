import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GameState {

	private int age;
	private Player currentPlayer;
	private LinkedHashMap<Player, ArrayList<Card>> playerHands;
	private ArrayList<Integer> xCoord;
	private Player[] players;
	private int playerNum, turn, cardIndex, round;
	private int wonderArrsize;
	private boolean endOfGame, clickCard, pressedDownL, pressedDownR, endOfRound;
	private Card clickedCard;
	private ArrayList<Card> discard;

	public GameState() {
		round = 0;
		discard = new ArrayList<>();
		setEnd(false);
		age = 0;
		turn = 1;
		wonderArrsize = 5;
		players = new Player[3];
		playerHands = new LinkedHashMap<Player, ArrayList<Card>>();
		xCoord = new ArrayList<Integer>();
		// assigns wonder
		Alexandria a = new Alexandria();
		Babylon b = new Babylon();
		Ephesus e = new Ephesus();
		Gizah g = new Gizah();
		Halikarnassos h = new Halikarnassos();
		Olympia o = new Olympia();
		Rhodos r = new Rhodos();
		ArrayList<Wonder> wonderArr = new ArrayList<>();
		wonderArr.add(a);
		wonderArr.add(b);
		wonderArr.add(e);
		wonderArr.add(g);
		wonderArr.add(r);
		wonderArr.add(o);
		wonderArr.add(h);

		for (int i = 0; i < 3; i++) {
			players[i] = new Player(i + 1);
			playerHands.put(players[i], new ArrayList<Card>());
			players[i].setWonder(wonderArr.remove((int) (Math.random() * wonderArrsize--)));
		}
		// assigns wonder^
		currentPlayer = players[0];
		playerNum = 1;
		currentPlayer.setLeft(players[1]); // player 2
		currentPlayer.setRight(players[2]); // player 3
		endOfGame = false;
		endOfRound = false;
		clickedCard = null;
		pressedDownL = false;
		pressedDownR = false;
		round = 1;
	}
	
	public ArrayList<Card> getDiscard() {
		return discard;
	}
	
	// playerNum can be 1, 2, or 3
	public Player[] getPlayers() {
		return players;
	}

	public ArrayList<Card> getHand(int playerNum) {
		return playerHands.get(players[playerNum - 1]);
	}

	public Player getLeftPlayer() {
		return currentPlayer.getLeft();
	}

	public Player getRightPlayer() {
		return currentPlayer.getRight();
	}

	public int getAge() {
		return age;
	}

	public ArrayList<Card> getCurrentHand() {
		return playerHands.get(currentPlayer);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public LinkedHashMap<Player, ArrayList<Card>> getPlayerHands() {
		return playerHands;
	}

	public Player getPlayer(int playerNum) {
		return players[playerNum - 1];
	}

	public int getCurrentPlayerNum() {
		return playerNum;
	}

	public void updateState(int age) {
		this.age = age;
	}

	public void updateState(LinkedHashMap<Player, ArrayList<Card>> ph) {
		playerHands = ph;
	}

	public void updateState(ArrayList<Card> hand) {
		playerHands.put(currentPlayer, hand);
	}

	public void resetRound() {
		round = 1;
		turn = 0;
	}
	public void nextTurn() {
		System.out.println(currentPlayer.getCards());
		Player tempCP = currentPlayer;
		currentPlayer = currentPlayer.getLeft();
		currentPlayer.setLeft(tempCP.getRight());
		currentPlayer.setRight(tempCP);
		playerNum = currentPlayer.getPlayerNum();
		turn++;
		playerNum = currentPlayer.getPlayerNum();
		System.out.println(playerHands);
		if(turn%18 == 1)
			endOfRound = true;
		if(turn%3 == 1)
			round++;
		System.out.println(currentPlayer.getCards());
	}
	public int getRound() {
		return round;
	}
	public void setEnd(boolean b) {
		endOfGame = b;
	}

	public boolean ifEnd() {
		return endOfGame;
	}

	public int getTurn() {
		return turn;
	}
	
	public boolean isEndOfRound() { 
		return endOfRound;
	}

	public boolean getClickCard() {
		return clickCard;
	}

	public boolean getPressedDownL() {
		return pressedDownL;
	}

	public boolean getPressedDownR() {
		return pressedDownR;
	}

	public void setClickCard(Boolean b) {
		clickCard = b;
	}

	public void setPressedDownL(Boolean b) {
		pressedDownL = b;
	}

	public void setPressedDownR(Boolean b) {
		pressedDownR = b;
	}

	public ArrayList<Integer> getXCoords() {
		return xCoord;
	}

	public void addXCoord(int i) {
		xCoord.add(i);
	}

	public void setCardIndex(int i) {
		cardIndex = i;
	}

	public int getCardIndex() {
		return cardIndex;
	}
	public boolean canBuild() {
		return currentPlayer.canBuild(clickedCard);
	}
	public boolean canBuildWithTrade() {
		return currentPlayer.canBuildWithTrade(clickedCard);
	}
	
	public void setClickedCard(Card c) {
		clickedCard = c;
	}
	
	public Card getClickedCard() { 
		return clickedCard;
	}
}
