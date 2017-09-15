package goBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gameMechanics.Game;

public class Slot extends JPanel implements MouseListener, Serializable {
	// TODO ATTRIBUTES
	int[] coord = new int[2];
	boolean hasStone = false;
	boolean hasBlackStone = false;
	transient boolean dead = false;
	transient Game currentGame;
	transient Image stone;
	static final Color color = new Color(130, 82, 1);
	static final Dimension slotsDimension = new Dimension(100, 100);
	transient int colorOfOwner; // 0 is none, 1 is black, 2 is white

	public void importParameters(Slot s) {
		if (s.hasStone)
			hasStone = true;
		else
			hasStone = false;
		if (s.hasBlackStone)
			hasBlackStone = true;
		else
			hasBlackStone = false;
	}

	public Slot(int x, int y, Game g) {
		coord[1] = y - 1;
		coord[0] = x - 1;
		this.addMouseListener(this);
		currentGame = g;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isEnabled()) {
			if (!currentGame.hasEnded()) {
				if (!hasStone) {
					if (isAValidSlot()) {
						placeStone();
					}
				}
			} else
				markAsDead();
			;
		}

	}

	public void placeStone() {
		// TEST IF STONE NOT NEEDED (DONE IN isValid())
		currentGame.unpass();
		currentGame.refreshRollBack();
		hasBlackStone = currentGame.isBlackTurn();
		hasStone = true;
		crossLibertyCheck(currentGame.getGrid()); // test if it kills
													// surrounding stones
		if (!currentGame.isVirtual())
			repaint();
		currentGame.refresh();
		if (!currentGame.isBlackTurn() && currentGame.getIfIA()) currentGame.getAI().next();

	}

	public boolean isDead() {
		return dead;
	}

	public boolean hasStone() {
		return (hasStone);
	}

	public boolean hasBlackStone() {
		return (hasBlackStone);
	}

	public int[] getCoordinates() {
		return coord;
	}

	public boolean isAValidSlot() {
		Slot[][] virtualGrid = new Slot[9][9];
		if (!hasStone) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					virtualGrid[j][i] = new Slot(j + 1, i + 1, currentGame);
					virtualGrid[j][i].importParameters(currentGame.getGrid()[j][i]);
				}
			}
			Slot testSlot = virtualGrid[coord[0]][coord[1]];
			testSlot.hasStone = true;
			testSlot.hasBlackStone = currentGame.isBlackTurn();
			if (testSlot.isKo(virtualGrid)) {
				testSlot.crossLibertyCheck(virtualGrid); // test if it kills surrounding stones (override the no suicide rule)
				if (testSlot.getLiberties(virtualGrid) != 0) { // no suicide rule
					return true;
				}
			}
		}
		return false;
	}

	public int getLiberties(Slot[][] grid) { // TEST METHOD
		ArrayList<Slot> list = new ArrayList<Slot>();
		ArrayList<Slot> liberties = new ArrayList<Slot>();
		int l = recursiveLiberties(list, liberties, grid);
		return (l);
	}

	public int getColorOfOwner() {
		return colorOfOwner;
	}

	public void markAsDead() {
		if (hasStone) {
			ArrayList<Slot> list = new ArrayList<Slot>();
			ArrayList<Slot> liberties = new ArrayList<Slot>();
			recursiveLiberties(list, liberties, currentGame.getGrid());
			for (Slot s : list) {
				if (this.hasBlackStone) {
					s.colorOfOwner = 2;
					s.repaint();
				} else {
					s.colorOfOwner = 1;
					s.repaint();
				}
				s.dead = true;
			}
		}
		currentGame.pointCounter();
		currentGame.getFrame().getFunctionPanel().getPrisonerPanel().refreshDisplay();
	}

	public boolean libertyCheck(Slot s, Slot[][] grid) {
		boolean killed = false;
		ArrayList<Slot> list = new ArrayList<Slot>();
		ArrayList<Slot> liberties = new ArrayList<Slot>();
		int l = recursiveLiberties(list, liberties, grid);
		int numberOfPrisoners = 0;
		if (l == 0) {
			killed = true;
			s.hasStone = false; // to save correctly in rollback
			if (grid == currentGame.getGrid())
				currentGame.refreshRollBack();
			s.hasStone = true;
			for (Slot slot : list) {
				slot.hasStone = false;
				if (!currentGame.isVirtual())
					slot.repaint();
				numberOfPrisoners += 1;
			}
			if (grid == currentGame.getGrid())
				currentGame.addPrisoners(numberOfPrisoners);
		}
		return killed;
	}

	public int recursiveLiberties(ArrayList<Slot> list, ArrayList<Slot> liberties, Slot[][] grid) {
		int l = 0;
		list.add(this);
		Slot test;

		if (coord[0] != 8) {
			test = grid[coord[0] + 1][coord[1]];
			if (test.hasStone()) {
				if (test.hasBlackStone() == this.hasBlackStone() && !list.contains(test)) {
					l += test.recursiveLiberties(list, liberties, grid);
				}
			} else {
				if (!liberties.contains(test)) {
					l += 1;
					liberties.add(test);
				}
			}
		}

		if (coord[1] != 8) {
			test = grid[coord[0]][coord[1] + 1];
			if (test.hasStone()) {
				if (test.hasBlackStone() == this.hasBlackStone() && !list.contains(test)) {
					l += test.recursiveLiberties(list, liberties, grid);
				}
			} else {
				if (!liberties.contains(test)) {
					l += 1;
					liberties.add(test);
				}
			}
		}

		if (coord[1] != 0) {
			test = grid[coord[0]][coord[1] - 1];
			if (test.hasStone()) {
				if (test.hasBlackStone() == this.hasBlackStone() && !list.contains(test)) {
					l += test.recursiveLiberties(list, liberties, grid);
				}
			} else {
				if (!liberties.contains(test)) {
					l += 1;
					liberties.add(test);
				}
			}
		}

		if (coord[0] != 0) {
			test = grid[coord[0] - 1][coord[1]];
			if (test.hasStone()) {
				if (test.hasBlackStone() == this.hasBlackStone() && !list.contains(test)) {
					l += test.recursiveLiberties(list, liberties, grid);
				}
			} else {
				if (!liberties.contains(test)) {
					l += 1;
					liberties.add(test);
				}
			}
		}
		return l;
	}

	public boolean crossLibertyCheck(Slot[][] grid) {
		Slot test = null;
		boolean killed = false;
		boolean k1, k2, k3, k4;
		k1 = false;
		k2 = false;
		k3 = false;
		k4 = false;

		if (hasStone()) {
			if (coord[0] != 8) {
				test = grid[coord[0] + 1][coord[1]];
				if (test.hasBlackStone() != this.hasBlackStone()) {
					k1 = test.libertyCheck(this, grid);
				}
			}
			if (coord[1] != 0) {
				test = grid[coord[0]][coord[1] - 1];
				if (test.hasBlackStone() != this.hasBlackStone()) {
					k2 = test.libertyCheck(this, grid);
				}
			}
			if (coord[1] != 8) {
				test = grid[coord[0]][coord[1] + 1];
				if (test.hasBlackStone() != this.hasBlackStone()) {
					k3 = test.libertyCheck(this, grid);
				}
			}
			if (coord[0] != 0) {
				test = grid[coord[0] - 1][coord[1]];
				if (test.hasBlackStone() != this.hasBlackStone()) {
					k4 = test.libertyCheck(this, grid);
				}
			}
			if (k1 == true || k2 == true || k3 == true || k4 == true) { // tests
																		// if
																		// something
																		// has
																		// been
																		// killed
																		// around
																		// the
																		// stone
				killed = true;
			}
		}
		return killed;
	}

	public boolean isKo(Slot[][] grid) {
		boolean valid = true;
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		Slot[][] koGrid = new Slot[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				koGrid[j][i] = new Slot(j + 1, i + 1, currentGame);
				koGrid[j][i].importParameters(grid[j][i]);
			}
		}
		crossLibertyCheck(koGrid);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (koGrid[j][i].hasStone()) {
					if (currentGame.getRollBackGrid()[j][i].hasStone()) {
						list.add(true);
					} else
						list.add(false);
				}
			}
		}
		if (!list.isEmpty() && !list.contains(false))
			valid = false;
		return valid;

	}

	public void pointCounter() {
		if (!hasStone) {
			ArrayList<Slot> list = new ArrayList<Slot>();
			ArrayList<Slot> border = new ArrayList<Slot>();
			int black = 0;
			int white = 0;
			recursivePointCounter(list, border);
			for (Slot s : border) {
				if (s.hasBlackStone())
					black++;
				else
					white++;
			}
			if (black > white) {
				for (Slot s : list) {
					s.colorOfOwner = 1;
					if (!currentGame.isVirtual())
						s.repaint();
				}
			} else {
				for (Slot s : list) {
					s.colorOfOwner = 2;
					if (!currentGame.isVirtual())
						s.repaint();
				}
			}
		}
	}

	public void recursivePointCounter(ArrayList<Slot> list, ArrayList<Slot> border) {
		list.add(this);
		Slot test;

		if (coord[0] != 8) {
			test = currentGame.grid[coord[0] + 1][coord[1]];
			if (!test.hasStone() && !list.contains(test) || test.isDead() && !list.contains(test)) {
				test.recursivePointCounter(list, border);
			} else if (test.hasStone() && !border.contains(test) && !test.isDead())
				border.add(test);
		}

		if (coord[1] != 8) {
			test = currentGame.grid[coord[0]][coord[1] + 1];
			if (!test.hasStone() && !list.contains(test) || test.isDead() && !list.contains(test)) {
				test.recursivePointCounter(list, border);
			} else if (test.hasStone() && !border.contains(test) && !test.isDead())
				border.add(test);
		}

		if (coord[1] != 0) {
			test = currentGame.grid[coord[0]][coord[1] - 1];
			if (!test.hasStone() && !list.contains(test) || test.isDead() && !list.contains(test)) {
				test.recursivePointCounter(list, border);
			} else if (test.hasStone() && !border.contains(test) && !test.isDead())
				border.add(test);
		}

		if (coord[0] != 0) {
			test = currentGame.grid[coord[0] - 1][coord[1]];
			if (!test.hasStone() && !list.contains(test) || test.isDead() && !list.contains(test)) {
				test.recursivePointCounter(list, border);
			} else if (test.hasStone() && !border.contains(test) && !test.isDead())
				border.add(test);
		}
	}

	public int evaluateSlot() {
		Game testGame = new Game(currentGame);
		double iTime=System.currentTimeMillis();
		testGame.getAI().playout();
		double eTime=System.currentTimeMillis()-iTime;
		System.out.println(eTime);
		testGame.pointCounter();
		int[] result = testGame.evaluateScore();
		if (result[0] + testGame.getBlackPrisoners() > result[1] + testGame.getWhitePrisoners())
			return -1; // black won
		if (result[1] + testGame.getWhitePrisoners() > result[0] + testGame.getBlackPrisoners())
			return 1; // white won
		return 0; // equal score
	}

	public double averageVictoryRate(int n) {
		double avg = 0;
		for (int j = 0; j < n; j++) {
			avg += evaluateSlot();
		}
		avg = avg / n;
		return avg;

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

	public void serializerSlots(ObjectOutputStream oos) throws IOException { // saving
																				// method
		oos.writeObject(this);
		oos.flush();
	}

	public void printStone(Graphics g) {
		if (hasStone) {
			if (hasBlackStone) {
				stone = new ImageIcon(getClass().getResource("/resources/blackStone.png")).getImage();
			} else {
				stone = new ImageIcon(getClass().getResource("/resources/whiteStone.png")).getImage();
			}
			g.drawImage(stone, 10, 10, 80, 80, null);
		}
		if (colorOfOwner == 1) {
			g.setColor(Color.BLACK);
			g.fillOval(45, 45, 10, 10);
		}
		if (colorOfOwner == 2) {
			g.setColor(Color.WHITE);
			g.fillOval(45, 45, 10, 10);
		}
	}

}
