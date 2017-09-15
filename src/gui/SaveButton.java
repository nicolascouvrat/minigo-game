package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import gameMechanics.Game;
import goBoard.Slot;

public class SaveButton extends JButton implements MouseListener {
	Game currentGame;

	public SaveButton(Game g) {
		super("Save Game");
		currentGame = g;
		this.addMouseListener(this);

	}

	public void saveGame() {
		JFileChooser chooser = new JFileChooser();
		int answer = chooser.showDialog(chooser, "Save Game");
		if (answer == JFileChooser.APPROVE_OPTION) {
			String filePath = chooser.getSelectedFile().toString();
			ObjectOutputStream oos = null;
			try {
				FileOutputStream file = new FileOutputStream(filePath);
				oos = new ObjectOutputStream(file);
				oos.writeObject(currentGame);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		saveGame();
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
