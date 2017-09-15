package goBoard;

import java.awt.Color;
import java.awt.Graphics;
import gameMechanics.Game;

/**
 * default is bottom right corner
 * 
 * @author Nicolas
 *
 */
public class BottomLeftCorner extends Slot {

	public BottomLeftCorner(int x, int y, Game g) {
		super(x, y, g);
		// TODO Auto-generated constructor stub
	}

	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.drawLine(100, 50, 50, 50);
		g.drawLine(50, 0, 50, 50);
		printStone(g);
	}
}
