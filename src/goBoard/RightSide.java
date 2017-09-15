package goBoard;

import java.awt.Color;
import java.awt.Graphics;
import gameMechanics.Game;

/**
 * default is bottom
 * 
 * @author Nicolas
 *
 */

public class RightSide extends Slot {

	public RightSide(int x, int y, Game g) {
		super(x, y, g);
		// TODO Auto-generated constructor stub
	}

	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.drawLine(0, 50, 50, 50);
		g.drawLine(50, 100, 50, 0);
		printStone(g);
	}
}
