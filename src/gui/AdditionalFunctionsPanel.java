package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gameMechanics.Game;

public class AdditionalFunctionsPanel extends JPanel {
	Game currentGame;
	PrisonerCount prisonerPanel;
	private Image img;

	public AdditionalFunctionsPanel(Game g) {
		img=new ImageIcon(getClass().getResource("/resources/functionPanelPic.jpg")).getImage();
		prisonerPanel = new PrisonerCount(g);
		Box b = Box.createVerticalBox();
		b.add(Box.createRigidArea(new Dimension(0, 50)));
		b.add(prisonerPanel);
		Box b2 = Box.createHorizontalBox(); 
		b2.add(new RollbackButton(g));
		b2.add(Box.createRigidArea(new Dimension(20, 0)));
		b2.add(new EndGameButton(g));
		b.add(Box.createRigidArea(new Dimension(0, 600)));
		b.add(b2);
		currentGame = g;
		this.add(b);
	}

	public PrisonerCount getPrisonerPanel() {
		return prisonerPanel;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
