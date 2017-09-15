package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import gameMechanics.Game;

public class EndGameButton extends JButton implements MouseListener {
	boolean hasBeenClicked;
	Game currentGame;
	boolean isEndButton;

	public EndGameButton(Game g) {
		super("Pass");
		currentGame = g;
		this.addMouseListener(this);
		setOpaque(false);
		setContentAreaFilled(false);
		setToolTipText(
				"If you think you cannot/should not play anymore, use this button to signify you wish to end the game");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isEndButton) {
			String player = null;
			if (currentGame.isBlackTurn())
				player = "Black";
			else
				player = "White";
			if (currentGame.getPassed()) {
				JOptionPane.showMessageDialog(currentGame.getFrame(),
						"Game has ended. Please click on dead stones to mark them as captured, then hit the validation button",
						"", JOptionPane.INFORMATION_MESSAGE);
				convertToEndButton();
			} else
				JOptionPane.showMessageDialog(currentGame.getFrame(), player + " passed", "",
						JOptionPane.INFORMATION_MESSAGE);
			currentGame.pass();
		} else {
			int[] score = currentGame.evaluateScore();
			String winner = null;
			int difference = (score[0]+currentGame.getBlackPrisoners()) - (score[1]+currentGame.getWhitePrisoners());
			if (difference > 0)
				winner = "Black";
			if (difference < 0) {
				winner = "White";
				difference = -difference;
			}
			int select = JOptionPane.showConfirmDialog(currentGame.getFrame(), "Confirm and end the game?", "",
					JOptionPane.YES_NO_OPTION);
			if (select == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(currentGame.getFrame(),
						"Game ended. " + winner + " wins by " + difference + " points(Komi was 0).", "Game ended",
						JOptionPane.INFORMATION_MESSAGE);
				currentGame.getFrame().dispose();
			}
		}
	}

	public void convertToEndButton() {

		setText("Confirm and end game");
		setToolTipText("Press this button once all dead stones have been marked");
		isEndButton = true;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
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

}
