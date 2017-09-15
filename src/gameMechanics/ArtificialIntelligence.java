package gameMechanics;

import java.util.Random;

import goBoard.Slot;

public class ArtificialIntelligence {
	Game currentGame;
	private static int numberOfGamesGenerated = 1;

	public ArtificialIntelligence(Game g) {
		currentGame = g;
	}

	public void placeAtRandom() {
		Random rand = new Random();
		int j = rand.nextInt(9);
		int i = rand.nextInt(9);
		while (!currentGame.getGrid()[j][i].isAValidSlot()) {

			j = rand.nextInt(9);
			i = rand.nextInt(9);
		}
		// System.out.println("tried to place stone at "+j+","+i);
		currentGame.getGrid()[j][i].placeStone();

	}

	public boolean cantPlay() {
		int slotsLeft = 0;
		for (Slot[] t : currentGame.getGrid()) {
			for (Slot s : t) {
				if (s.isAValidSlot())
					slotsLeft++;
			}
		}
		if (slotsLeft == 0) {
			return true;
		}
		return false;
	}

	public void playout() {
		while (!currentGame.hasEnded()) {
			System.out.print("");
			if (currentGame.countStones() == 80) {
				currentGame.endGame();
			} else {
				if (currentGame.isBlackTurn()) {
					if (cantPlay()) {
						// System.out.println("Black can't play");
						currentGame.pass();
					} else {
						currentGame.unpass();
						placeAtRandom();
					}
				}
				if (!currentGame.isBlackTurn()) {
					if (cantPlay()) {
						// System.out.println("White can't play");
						currentGame.pass();
					} else {
						currentGame.unpass();
						;
						placeAtRandom();
					}
				}
			}
		}
	}

	public void next() {
		double currentWinRate = -1;
		int[] currentCoord = null;
		int gameCount = 0;
		for (Slot[] t : currentGame.getGrid()) {
			for (Slot s : t) {
				if (!s.hasStone()) {
					double test = s.averageVictoryRate(ArtificialIntelligence.numberOfGamesGenerated);
					gameCount++;
					System.out.println("one done:" + gameCount);
					if (test > currentWinRate) {
						currentCoord = s.getCoordinates();
					}
				}
			}
		}
		System.out.println("best coordinates: x=" + currentCoord[0] + "y=" + currentCoord[1]);
		currentGame.getGrid()[currentCoord[0]][currentCoord[1]].placeStone();
	}

	public void playVsComputer() {
		while (!currentGame.hasEnded()) {
			System.out.print("");
			if (!currentGame.isBlackTurn()) {
				// currentGame.setUserControl(false);
				// next();
				// currentGame.setUserControl(true);
			}
		}
	}

}
