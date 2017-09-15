package gui;

import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import gameMechanics.Game;
import goBoard.Slot;

public class ChargeButton extends JButton implements MouseListener {
	Game currentGame;
	Game savedGame;

	public ChargeButton(Game g) {
		super("Open saved game");
		currentGame = g;
		this.addMouseListener(this);
	}

	public void openGame() {
		try {
			JFileChooser chooser = new JFileChooser();
			int answer = chooser.showDialog(chooser, "Open Game");
			if (answer == JFileChooser.APPROVE_OPTION) {
				String filePath = chooser.getSelectedFile().toString();
				ObjectInputStream ois = null;
				try {
					FileInputStream file = new FileInputStream(filePath);
					ois = new ObjectInputStream(file);
					savedGame = (Game) ois.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				currentGame.getGrid()[j][i].importParameters(savedGame.getGrid()[j][i]);
				currentGame.getGrid()[j][i].repaint();
			}
		}
		currentGame.setBlackPrisoners(savedGame.getBlackPrisoners());
		currentGame.setWhitePrisoners(savedGame.getWhitePrisoners());
		currentGame.setTurn(savedGame.isBlackTurn());
		currentGame.getFrame().getFunctionPanel().getPrisonerPanel().refreshDisplay();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		openGame();
		// TODO Auto-generated method stub

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
