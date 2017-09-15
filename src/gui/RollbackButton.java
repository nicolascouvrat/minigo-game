package gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import gameMechanics.Game;

public class RollbackButton extends JButton implements MouseListener {
	Game currentGame;

	public RollbackButton(Game g) {
		super("Rollback");
		this.setPreferredSize(new Dimension(300, 50));
		this.addMouseListener(this);
		currentGame = g;
		setOpaque(false);
		setContentAreaFilled(false);
		setToolTipText(
				"Use this button to take back your previous move. Works only if your opponent has not played yet");
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		String player = null;
		if (!currentGame.isBlackTurn())
			player = "Black";
		else
			player = "White";
		int choice = JOptionPane.showConfirmDialog(currentGame.getFrame(),
				player + " wants to take back his move. Do you accept?", "", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			currentGame.rollBack();
		} else {
			JOptionPane.showMessageDialog(currentGame.getFrame(), "Permission to take back denied", "",
					JOptionPane.WARNING_MESSAGE);
		}

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
