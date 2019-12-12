import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements MouseListener {
	private GameState gs;
	private GamePanel panel;

	public MainFrame(String title, GameState gs) throws IOException, FontFormatException {
		super(title);
		this.gs = gs;
		setupGraphics();
		addMouseListener(this);

	}

	public void setupGraphics() throws IOException, FontFormatException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);

		panel = new GamePanel(gs);
		panel.updateGameState(gs);
		
		add(panel);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		pack();
		setVisible(true);
	}

	public void updateGraphics() throws IOException {
		panel.updateGameState(gs);		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();

		// int size = tempHand.size();
		System.out.println(x + ", " + y);
		for (int i = 0; i < gs.getXCoords().size(); i++)
				if ((x >= gs.getXCoords().get(i) && x <= gs.getXCoords().get(i) + 180) && (y >= 780 && y <= 1080)) {
					gs.setCardIndex(i);
					gs.setClickCard(true);
					gs.setClickedCard(gs.getCurrentHand().get(i));
					System.out.println(gs.getClickedCard());
					repaint();
					return;
				}
		if(gs.getRound() == 7) {
			gs.resetRound();
			repaint();
		}
		int cardIndex = gs.getCardIndex();
		if (x >= 208 && x <= 268) {
			if((y >= 822 && y <= 868) && gs.canBuild()) {
				gs.getCurrentPlayer().build(gs.getCurrentHand().remove(cardIndex));
				gs.getDiscard().add(gs.getClickedCard());
				gs.setClickCard(false);
				gs.setClickedCard(null);
				gs.setPressedDownL(false);
				gs.setPressedDownR(false);
				gs.nextTurn();
				repaint();
			}
			else if((y >= 822 && y <= 868) && gs.canBuildWithTrade()) {
				gs.getCurrentPlayer().trade(gs.getCurrentHand().get(cardIndex));
				gs.getCurrentPlayer().build(gs.getCurrentHand().remove(cardIndex));
				gs.getDiscard().add(gs.getClickedCard());
				gs.setClickCard(false);
				gs.setClickedCard(null);
				gs.setPressedDownL(false);
				gs.setPressedDownR(false);
				gs.nextTurn();
				repaint();
			}
			else if (y >= 924 && y <= 970 && gs.getCurrentPlayer().canBuildWonder()) {
				gs.getCurrentPlayer().buildWonder();
				gs.getCurrentHand().remove(cardIndex);
				gs.setClickCard(false);
				gs.setClickedCard(null);
				gs.setPressedDownL(false);
				gs.setPressedDownR(false);
				gs.nextTurn();
				repaint();
			}

			else if ((x >= 220 && x <= 255) && (y >= 1027 && y <= gs.getXCoords().get(cardIndex) + 1065) && (y >= 1000 && y <= 1042)) {
				gs.getCurrentPlayer().addCoins(3);
				gs.getCurrentHand().remove(cardIndex);
				gs.getDiscard().add(gs.getClickedCard());
				gs.setClickCard(false);
				gs.setClickedCard(null);
				gs.setPressedDownL(false);
				gs.setPressedDownR(false);
				gs.nextTurn();
				repaint();
			}
			
			
			
			try {
				panel.updateGameState(gs);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}
		if ((x >= 920 && x <= 950) && (y >= 150 && y <= 170)) {
			gs.setPressedDownL(!gs.getPressedDownL());
			repaint();
		}
		if ((x >= 1880 && x <= 1910) && (y >= 150 && y <= 170)) {
			gs.setPressedDownR(!gs.getPressedDownR());
			repaint();
		}
		try {
			panel.updateGameState(gs);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
