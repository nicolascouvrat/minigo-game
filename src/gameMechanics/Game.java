package gameMechanics;

import java.io.Serializable;

import goBoard.Slot;
import gui.Frame;

public class Game implements Serializable {

	private boolean blackToPlay = true;
	transient private boolean gameEnded = false;
	transient private boolean passed = false;
	public Slot[][] grid = new Slot[9][9];
	transient private Slot[][] rollBackGrid = new Slot[9][9];
	int whitePrisoners;
	int blackPrisoners;
	transient int previousBlackPrisoners;
	transient int previousWhitePrisoners;
	transient Frame frame;
	transient ArtificialIntelligence aI;
	transient boolean isVirtual;
	transient boolean isVsComputer;

	public Game(Game g) {
		isVirtual = true;
		grid = copyGrid(g.grid, this);
		aI = new ArtificialIntelligence(this);
		rollBackGrid = copyGrid(g.rollBackGrid, this);

	}

	public Game(boolean vsComputer) {
		isVirtual = false;
		isVsComputer = vsComputer;
		frame = new Frame(this);
		initiateRollBack();
		if (isVsComputer)
			aI = new ArtificialIntelligence(this);

	}

	public boolean getIfIA() {
		return isVsComputer;
	}

	public Slot[][] generateVirtualGrid(Game g) {
		Slot[][] virtualGrid = new Slot[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				virtualGrid[j][i] = new Slot(j + 1, i + 1, g);
			}
		}
		return virtualGrid;
	}

	public boolean isVirtual() {
		return isVirtual;
	}

	public void pass() {
		if (passed)
			endGame();
		else {
			passed = true;
			blackToPlay = !blackToPlay;
		}
	}

	public void unpass() {
		passed = false;
	}

	public ArtificialIntelligence getAI() {
		return aI;
	}

	public boolean getPassed() {
		return passed;
	}

	public int countStones() {
		int black = 0;
		int white = 0;
		for (Slot[] t : grid) {
			for (Slot s : t) {
				if (s.hasStone()) {
					if (s.hasBlackStone())
						black++;
					else
						white++;
				}
			}
		}
		return white + black;
	}

	public void setUserControl(boolean control) {

		for (Slot[] t : grid) {
			for (Slot s : t) {
				s.setEnabled(control);
			}
		}
	}

	public void refresh() {
		blackToPlay = !blackToPlay;
	}

	public void rollBack() {
		blackToPlay = !blackToPlay;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				grid[j][i].importParameters(rollBackGrid[j][i]);
				grid[j][i].repaint();
			}
		}
		blackPrisoners = previousBlackPrisoners;
		whitePrisoners = previousWhitePrisoners;
		if (!isVirtual)
			getFrame().getFunctionPanel().getPrisonerPanel().refreshDisplay();
	}

	public void initiateRollBack() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				rollBackGrid[j][i] = new Slot(j + 1, i + 1, this);
			}
		}
		previousWhitePrisoners = 0;
		previousBlackPrisoners = 0;

	}

	public void refreshRollBack() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				rollBackGrid[j][i].importParameters(grid[j][i]);
			}
		}
		previousBlackPrisoners = blackPrisoners;
		previousWhitePrisoners = whitePrisoners;
	}

	public boolean isBlackTurn() {
		return (blackToPlay);
	}
	//
	// public void testGrid(){ //TEST ONLY METHOD
	// for (int i=0;i<9;i++){
	// for(int j=0;j<9;j++){
	// if(grid[j][i].hasStone()){
	// if(grid[j][i].hasBlackStone()){
	// System.out.print("black");
	// }
	// else System.out.print("white");
	// }
	// else {
	// switch(grid[j][i].getColorOfOwner()){
	// case 0:
	// System.out.print("none");
	// break;
	// case 1:
	// System.out.print("Bter");
	// break;
	// case 2:
	// System.out.print("Wter");
	// break;
	// }
	// }
	// System.out.print(grid[j][i].coordinates()+" |");
	//
	// }
	// System.out.print("\n");
	//
	// }
	// }

	// public void testRollBackGrid(){ TEST ONLY METHOD
	// for (int i=0;i<9;i++){
	// for(int j=0;j<9;j++){
	// if(rollBackGrid[j][i].hasStone()){
	// if(rollBackGrid[j][i].hasBlackStone()){
	// System.out.print("black");
	// }
	// else System.out.print("white");
	// }
	// else {
	// System.out.print("none");
	// }
	// System.out.print(rollBackGrid[j][i].coordinates()+" |");
	//
	// }
	// System.out.print("\n");
	//
	// }
	// }

	public Frame getFrame() {
		return frame;
	}

	public void pointCounter() {
		for (Slot[] t : grid) {
			for (Slot s : t) {
				s.pointCounter();
			}
		}
	}

	public boolean hasEnded() {
		return gameEnded;
	}

	public void endGame() {
		gameEnded = true;
		if (!isVirtual) {
			pointCounter();
			getFrame().getFunctionPanel().getPrisonerPanel().convertToScoreBoard();
			getFrame().getFunctionPanel().getPrisonerPanel().refreshDisplay();
		}
	}

	public Slot[][] getGrid() {
		return grid;
	}

	public int getWhitePrisoners() {
		return whitePrisoners;
	}

	public int getBlackPrisoners() {
		return blackPrisoners;
	}

	public void addPrisoners(int n) {
		if (blackToPlay)
			blackPrisoners += n;
		else
			whitePrisoners += n;
		if (!isVirtual)
			getFrame().getFunctionPanel().getPrisonerPanel().refreshDisplay();
	}

	public void setBlackPrisoners(int i) {
		blackPrisoners = i;
	}

	public void setWhitePrisoners(int i) {
		whitePrisoners = i;
	}

	public void setTurn(boolean b) {
		blackToPlay = b;
	}

	public int[] evaluateScore() {
		int black = 0;
		int white = 0;
		for (Slot[] t : grid) {
			for (Slot s : t) {
				if (s.getColorOfOwner() == 1)
					black++;
				if (s.getColorOfOwner() == 2)
					white++;
			}
		}
		int[] tab = { black, white };
		return tab;
	}

	public Slot[][] getRollBackGrid() {
		return rollBackGrid;
	}

	public Slot[][] copyGrid(Slot[][] oldGrid, Game newGame) {
		Slot[][] newGrid = generateVirtualGrid(newGame);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				newGrid[j][i].importParameters(oldGrid[j][i]);
			}
		}
		return newGrid;

	}
}
