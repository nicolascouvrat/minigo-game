package goBoard;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gameMechanics.Game;

public class TopLine extends JPanel {
	private static GridLayout lineLayout = new GridLayout(1, 9);
	Slot slot;

	public TopLine(int i, Game g) {
		lineLayout.setHgap(0);
		this.setLayout(lineLayout);
		slot = new TopLeftCorner(1, i, g);
		g.grid[0][i - 1] = slot;
		this.add(slot);
		for (int j = 2; j < 9; j++) {
			slot = new TopSide(j, i, g);
			g.grid[j - 1][i - 1] = slot;
			this.add(slot);
		}
		slot = new TopRightCorner(9, i, g);
		g.grid[8][i - 1] = slot;
		this.add(slot);

	}

}
