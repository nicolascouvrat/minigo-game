package gui;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gameMechanics.Game;

public class MenuBar implements ActionListener {
	JMenuBar menuBar;
	JMenu mainMenu;
	JMenu helpMenu;
	JMenuItem save;
	JMenuItem open;
	JMenuItem rules;
	Game currentGame;
	Game savedGame;

	public MenuBar(Game g) {
		currentGame = g;
		menuBar = new JMenuBar();
		mainMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		open = new JMenuItem("Charge saved game");
		open.addActionListener(this);
		save = new JMenuItem("Save game");
		save.addActionListener(this);
		rules = new JMenuItem("Rules of the game of go...");
		rules.addActionListener(this);
		helpMenu.add(rules);
		mainMenu.add(save);
		mainMenu.add(open);
		menuBar.add(mainMenu);
		menuBar.add(helpMenu);

	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			saveGame();
		}
		if (e.getSource() == open) {
			openGame();
		}
		if (e.getSource() == rules) {
			try {
				URI uri = URI.create("http://jeudego.org/_php/regleGo.php");
				int option = JOptionPane.showConfirmDialog(currentGame.getFrame(),
						"This will open your web browser. Continue?", "Warning", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					Desktop.getDesktop().browse(uri);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(currentGame.getFrame(), "Unable to open your web browser", "",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		// TODO Auto-generated method stub

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

}
