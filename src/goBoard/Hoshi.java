package goBoard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import gameMechanics.Game;

public class Hoshi extends Slot implements MouseListener, Cloneable {

	public Hoshi(int x, int y, Game g) {
		super(x, y, g);
	}

	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.drawLine(50, 0, 50, 100);
		g.drawLine(0, 50, 100, 50);
		g.fillOval(45, 45, 10, 10);
		printStone(g);
	}

}
