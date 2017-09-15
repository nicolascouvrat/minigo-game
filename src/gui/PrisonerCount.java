package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameMechanics.Game;

public class PrisonerCount extends JPanel {
	int blackPrisoners = 0;
	int whitePrisoners = 0;
	boolean isScoreBoard;
	JLabel whiteLabel;
	JLabel blackLabel;
	int previousBlackPrisoners;
	int previousWhitePrisoners;
	JLabel blackPrisonersLabel = new JLabel("<html><div style='text-align: center;'>" + "0" + "</html>");
	JLabel whitePrisonersLabel = new JLabel("<html><div style='text-align: center;'>" + "0" + "</html>");
	Game currentGame;

	public PrisonerCount(Game g) {
		currentGame = g;
		setPreferredSize(new Dimension(400, 100));
		Box b1 = Box.createVerticalBox();
		blackLabel = new JLabel("Black's prisoners");
		whiteLabel = new JLabel("White's prisoners");
		b1.add(blackLabel);
		blackLabel.setAlignmentX(CENTER_ALIGNMENT);
		b1.add(Box.createRigidArea(new Dimension(0, 10)));
		b1.add(blackPrisonersLabel);
		blackPrisonersLabel.setAlignmentX(CENTER_ALIGNMENT);
		Box b2 = Box.createVerticalBox();
		b2.add(whiteLabel);
		whiteLabel.setAlignmentX(CENTER_ALIGNMENT);
		b2.add(Box.createRigidArea(new Dimension(0, 10)));
		b2.add(whitePrisonersLabel);
		whitePrisonersLabel.setAlignmentX(CENTER_ALIGNMENT);
		Box b3 = Box.createHorizontalBox();
		b3.add(b1);
		b3.add(Box.createRigidArea(new Dimension(20, 0)));
		b3.add(b2);
		this.add(b3);
		setOpaque(false);
	}

	public void refreshDisplay() {
		if (!isScoreBoard) {
			blackPrisonersLabel.setText("" + currentGame.getBlackPrisoners());
			whitePrisonersLabel.setText("" + currentGame.getWhitePrisoners());
		} else {
			blackPrisonersLabel.setText(currentGame.evaluateScore()[0] + currentGame.getBlackPrisoners() + " (including"
					+ currentGame.getBlackPrisoners() + " prisoners)");
			whitePrisonersLabel.setText(currentGame.evaluateScore()[1] + currentGame.getWhitePrisoners() + " (including"
					+ currentGame.getWhitePrisoners() + " prisoners)");
		}
	}

	public void convertToScoreBoard() {
		whiteLabel.setText("White's score");
		blackLabel.setText("Black's score");
		blackPrisonersLabel.setText(currentGame.evaluateScore()[0] + currentGame.getBlackPrisoners() + " (including"
				+ currentGame.getBlackPrisoners() + " prisoners)");
		whitePrisonersLabel.setText(currentGame.evaluateScore()[1] + currentGame.getWhitePrisoners() + " (including"
				+ currentGame.getWhitePrisoners() + " prisoners)");
		isScoreBoard = true;
	}

}
