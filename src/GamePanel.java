
import static java.lang.System.out;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private GameState gs;
	private BufferedImage[] imgList;
	private BufferedImage[] imgList1;
	private BufferedImage currentAgeCard, coin1, coin3, endScreen, scroll;
	private Font font;
	private String currentAgeStr;
	private int[] coin1List, coin3List;
	private ArrayList<Card> tempHand;
	private int width, height;
	private int cpNum, leftNum, rightNum;

	public GamePanel(GameState gs) throws IOException, FontFormatException {
		this.gs = gs;
		imgList = new BufferedImage[3];
		imgList1 = new BufferedImage[3];
		coin1List = new int[3];
		coin3List = new int[3];
		tempHand = gs.getCurrentHand();
		currentAgeStr = "";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		width = screenSize.width;
		height = screenSize.height;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		coin1 = ImageIO.read(getClass().getResource("/tokens/coin1.png"));
		coin3 = ImageIO.read(getClass().getResource("/tokens/coin3.png"));

		String fName = "/font/PossumSaltareNF.ttf";
		InputStream is = GamePanel.class.getResourceAsStream(fName);
		font = Font.createFont(Font.TRUETYPE_FONT, is);
		font = font.deriveFont(Font.BOLD, 20);

		// endScreen =
		// ImageIO.read(getClass().getResource("/wonder_boards/endscreen.png"));
		// scroll = ImageIO.read(getClass().getResource("/tokens/scroll.png"));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gs.ifEnd()) {
			String res = "";
			int a = 300;
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
			g.drawString(("First Place: Player " + scores.get(win) + " Score: " + win), 500, a+=100);
			g.drawString(("Second Place: Player " + scores.get(second) + " Score: " + second), 500, a+=100);
			g.drawString(("Third Place: Player " + scores.get(third) + " Score: " + third), 500, a+=100);
			
		}
		else if(gs.isEndOfRound()) {
			int a = 300;
			g.setColor(new Color(0, 0, 0));
			String res = "";
			
			for(Player p: gs.getPlayers()) {
				g.drawString("Player " + p.getPlayerNum() + " wages war on player " + gs.getLeftPlayer().getPlayerNum(), 500, a+=75);
				int result = p.wageWar(gs.getAge());
				if(result == 0)
					g.drawString("No one won", 500, a+=75);
				else {
					String mp = "5";
					if(gs.getAge() == 1)
						mp = "1";
					else if(gs.getAge() == 2)
						mp = "3";
					g.drawString("Player " + result + " won! Gained " + mp + " military points.", 500, a+=75);
				}
			}
			g.drawString(res, 500, a+=75);
		}
		else {
			// current Player
			g.drawImage(imgList[cpNum-1], 0, 0, 1920, 1080, this);
	
			g.setColor(new Color(145, 127, 107, 200));
			g.fillRect(0, 780, 1920, 1080);
			g.setColor(new Color(120, 108, 92, 200));
			g.fillRect(0, 760, 1920, 20);
	
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(("Player " + cpNum).toUpperCase(), 900, 778);
			g.drawImage(currentAgeCard, 0, 780, this);
			g.drawImage(coin1, 1700, 950, 40, 40, this);
			g.drawImage(coin3, 1700, 1000, 40, 40, this);
			g.drawString(":  " + coin1List[cpNum-1], 1745, 975);
			g.drawString(":  " + coin3List[cpNum-1], 1745, 1025);
			// current Player resource
			String resourceFile = "/wonder_resource/" + gs.getCurrentPlayer().getWonder().getResource()
					+ ".jpg";
			try {
				g.drawImage(ImageIO.read(getClass().getResource(resourceFile)), 0, 710, 170, 50, this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			// effects
			g.drawImage(imgList1[0], 480, 680, 230, 80, this);
			g.drawImage(imgList1[1], 840, 680, 230, 80, this);
			g.drawImage(imgList1[2], 1190, 680, 230, 80, this);
	
			// Player after (testing Player 2)
			g.drawImage(imgList[leftNum-1], 0, 0, 960, 150, 0, 360, 1920, 720, this);
			g.drawString(("Player " + leftNum).toUpperCase(), 440, 150);
			g.drawImage(coin1, 10, 5, 40, 40, this);
			g.drawImage(coin3, 10, 55, 40, 40, this);
			if (gs.getPlayers()[leftNum - 1].getWonder().getName().equals("Olympia")) {
				g.setColor(Color.BLACK);
				g.drawString(":  " + coin1List[leftNum-1], 55, 30);
				g.drawString(":  " + coin3List[leftNum-1], 55, 80);
				g.setColor(Color.WHITE);
			} else {
				g.drawString(":  " + coin1List[leftNum-1], 55, 30);
				g.drawString(":  " + coin3List[leftNum-1], 55, 80);
			}
	
			resourceFile = "/wonder_resource/" + gs.getPlayers()[leftNum-1].getWonder().getResource() + ".jpg";
			try {
				g.drawImage(ImageIO.read(getClass().getResource(resourceFile)), 0, 100, 170, 50, this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			// Player before (testing Player 3)
			g.drawImage(imgList[rightNum-1], 960, 0, 1920, 150, 0, 360, 1920, 720, this);
			g.drawString(("Player " + rightNum).toUpperCase(), 1400, 150);
			g.drawImage(coin1, 970, 5, 40, 40, this);
			g.drawImage(coin3, 970, 55, 40, 40, this);
			if (gs.getPlayers()[rightNum-1].getWonder().getName().equals("Olympia")) {
				g.setColor(Color.BLACK);
				g.drawString(":  " + coin1List[rightNum-1], 1015, 30);
				g.drawString(":  " + coin3List[rightNum-1], 1015, 80);
				g.setColor(Color.WHITE);
			} else {
				g.drawString(":  " + coin1List[rightNum-1], 1015, 30);
				g.drawString(":  " + coin3List[rightNum-1], 1015, 80);
			}
	
			resourceFile = "/wonder_resource/" + gs.getPlayers()[rightNum-1].getWonder().getResource() + ".jpg";
			try {
				g.drawImage(ImageIO.read(getClass().getResource(resourceFile)), 960, 100, 170, 50, this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			g.drawString("ROUND: " + gs.getRound(), 1800, 200);
			int a = 0;
			LinkedHashMap<String, ArrayList<Card>> map = gs.getCurrentPlayer().getCards();
			for(String k: map.keySet()) {
				for(int b = 0; b < map.get(k).size(); b++) {
					Card c = map.get(k).get(b);
					try {
						g.drawImage(
								ImageIO.read(getClass()
										.getResource("/cards/" + (map.get(k).get(b).getName()).toLowerCase() + ".png")),
								100 + a + b * 30, 200 + b * 50, 144, 220, this);
						//System.out.println((0 + i + j * 30) + ", " + (200 + j * 50));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	
				if (map.get(k).size() <= 2) {
					a += 150 * map.get(k).size() * 2;
				} else
					a += 300;
	
			}
			
			int count = 0;
			for (Card c : tempHand) {
				try {
					g.drawImage(ImageIO.read(getClass().getResource("/cards/" + c.getName().toLowerCase() + ".png")),
							300 + count, 780, this);
					gs.addXCoord(300 + count);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count += 200;
			}
	
			try {
				g.drawImage(ImageIO.read(getClass().getResource("/tokens/dropdown.png")), 920, 125, 32, 18, this);
				g.drawImage(ImageIO.read(getClass().getResource("/tokens/dropdown.png")), 1880, 125, 32, 18, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(gs.getPressedDownL()) {
				g.setColor(new Color(19, 21, 23, 200));
				g.fillRect(0, 150, 960, 400);
				
				Player p = gs.getPlayer(leftNum);
				map = p.getCards();
				a = 0;
				for(String k: map.keySet()) {
					for(int b = 0; b < map.get(k).size(); b++) {
						Card c = map.get(k).get(b);
						try {
							g.drawImage( ImageIO.read(getClass()
											.getResource("/cards/" + (c.getName()).toLowerCase() + ".png")),
									50 + a + b * 20, 175 + b * 35, 108, 165, this);
							//System.out.println((0 + a + b * 30) + ", " + (200 + b * 50));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
	
					if (map.get(k).size() <= 2) {
						a += 75 * map.get(k).size() * 2;
					} else
						a += 150;
				}
			}
	
			else if(gs.getPressedDownR()) {
				g.setColor(new Color(19, 21, 23, 200));
				g.fillRect(960, 150, 960, 400);
				
				a = 0;
				Player p = gs.getPlayer(rightNum);
				map = p.getCards();
				for(String k: map.keySet()) {
					for(int b = 0; b < map.get(k).size(); b++) {
						Card c = map.get(k).get(b);
						try {
							g.drawImage( ImageIO.read(getClass()
											.getResource("/cards/" + (c.getName()).toLowerCase() + ".png")),
									1010 + a + b * 20, 175 + b * 35, 108, 165, this);
							System.out.println((0 + a + b * 30) + ", " + (200 + b * 50));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
	
					if (map.get(k).size() <= 2) {
						a += 75 * map.get(k).size() * 2;
					} else
						a += 150;
				}
			}
			else if(gs.getClickCard()) {
				try {
					if(gs.canBuild())
						g.drawImage(ImageIO.read(getClass().getResource("/tokens/card.png")), 208, 800, 60, 46, this);
					else if(gs.canBuildWithTrade()) {
						g.drawImage(ImageIO.read(getClass().getResource("/tokens/card.png")), 208, 800, 60, 46, this);
						g.drawString("(With Trade)", 177, 845);
					}
					if(gs.getCurrentPlayer().canBuildWonder())
						g.drawImage(ImageIO.read(getClass().getResource("/tokens/pyramid.png")), 208, 900, 60, 46, this);
					g.drawImage(ImageIO.read(getClass().getResource("/tokens/trash.png")), 218, 1000, 35, 42, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void updateGameState(GameState gs) throws IOException {
		this.gs = gs;
		
		cpNum = gs.getCurrentPlayerNum();
		leftNum = gs.getCurrentPlayer().getLeft().getPlayerNum();
		rightNum = gs.getCurrentPlayer().getRight().getPlayerNum();
		String wonderName = gs.getCurrentPlayer().getWonder().getName();

		for (int i = 0; i < imgList1.length; i++) {
			imgList1[i] = ImageIO.read(getClass().getResource("/wonder_effects/" + wonderName + "Stage" + (i + 1) + ".jpg"));
		}

		
		tempHand = gs.getCurrentHand();
		
		int totalCoins = 0;
		for (int i = 0; i < gs.getPlayers().length; i++) {
			totalCoins = gs.getPlayers()[i].getCoins();
			coin3List[i] = totalCoins / 3;
			totalCoins -= coin3List[i] * 3;
			coin1List[i] = totalCoins;
			totalCoins = 0;
		}
		
		currentAgeStr = "age" + gs.getAge() + ".png";
		currentAgeCard = ImageIO.read(getClass().getResource("/cards/" + currentAgeStr));
		
		Player[] playerList = gs.getPlayers();
		for (int i = 0; i < playerList.length; i++) {
			Player p = playerList[i];
			System.out.println(p.getWonder().getName());
			imgList[i] = ImageIO.read(getClass().getResource("/wonder_boards/" + p.getWonder().getName() + ".jpg"));
		}
		
	}
}
