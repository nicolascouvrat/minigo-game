package goBoard;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gameMechanics.Game;

public class RegularLine extends JPanel {
	private static GridLayout lineLayout = new GridLayout(1, 9);
	Slot slot;

	public RegularLine(int i, Game g) {
		lineLayout.setHgap(0);
		this.setLayout(lineLayout);
		slot = new LeftSide(1, i, g);
		g.grid[0][i - 1] = slot;
		this.add(slot);
		switch (i) {
		case 3:
			for (int j = 2; j < 9; j++) {
				if (j == 3 || j == 7) {
					slot = new Hoshi(j, i, g);
					g.grid[j - 1][i - 1] = slot;
					this.add(slot);
				} else {
					slot = new Middle(j, i, g);
					g.grid[j - 1][i - 1] = slot;
					this.add(slot);
				}
			}
			break;
		case 7:
			for (int j = 2; j < 9; j++) {
				if (j == 3 || j == 7) {
					slot = new Hoshi(j, i, g);
					g.grid[j - 1][i - 1] = slot;
					this.add(slot);
				} else {
					slot = new Middle(j, i, g);
					g.grid[j - 1][i - 1] = slot;
					this.add(slot);
				}
			}
			break;
		case 5:
			for (int j = 2; j < 9; j++) {
				if (j == 5) {
					slot = new Hoshi(j, i, g);
					g.grid[j - 1][i - 1] = slot;
					this.add(slot);
				} else {
					slot = new Middle(j, i, g);
					g.grid[j - 1][i - 1] = slot;
					this.add(slot);
				}
			}
			break;
		default:
			for (int j = 2; j < 9; j++) {
				slot = new Middle(j, i, g);
				g.grid[j - 1][i - 1] = slot;
				this.add(slot);
			}
			break;
		}
		slot = new RightSide(9, i, g);
		g.grid[8][i - 1] = slot;
		this.add(slot);

	}

}
