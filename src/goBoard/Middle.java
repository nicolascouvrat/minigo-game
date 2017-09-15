package goBoard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import gameMechanics.Game;

public class Middle extends Slot implements MouseListener {

	public Middle(int x, int y, Game g) {
		super(x, y, g);
		this.setBackground(Color.ORANGE);
	}

	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.drawLine(50, 0, 50, 100);
		g.drawLine(0, 50, 100, 50);
		printStone(g);
	}
}
