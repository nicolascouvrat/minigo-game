package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class WelcomeScreenPanel extends JPanel {
	private Image img;
	private StartGameButton vsAI;
	private StartGameButton twoPlayers;

	public WelcomeScreenPanel(Image img) {
		this.img = img;
		twoPlayers = new StartGameButton(false);
		vsAI = new StartGameButton(true);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		BoxLayout buttonLayout = new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS);
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 300)));
		buttonPanel.add(twoPlayers);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonPanel.add(vsAI);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 906 - 2 * 181 - 310)));
		BoxLayout mainLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(mainLayout);
		add(Box.createRigidArea(new Dimension(1250, 0)));
		add(buttonPanel);
		add(Box.createRigidArea(new Dimension(1920 - 1250 - 237, 0)));

	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
