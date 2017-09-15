package goBoard;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

import gameMechanics.Game;

public class Board extends JPanel {
	Dimension boardSize = new Dimension(900, 900);
	GridLayout boardLayout = new GridLayout(9, 1);

	public Board(Game g) {
		setPreferredSize(boardSize);
		setMaximumSize(boardSize);
		boardLayout.setVgap(0);
		setLayout(boardLayout);
		add(new TopLine(1, g));
		for (int j = 2; j < 9; j++) {
			add(new RegularLine(j, g));
		}
		add(new BottomLine(9, g));

	}

}
