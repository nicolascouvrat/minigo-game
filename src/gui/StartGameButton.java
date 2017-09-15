package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gameMechanics.Game;

public class StartGameButton extends JPanel implements MouseListener {
	Image img;
	Boolean isAIButton;

	public StartGameButton(Boolean isAI) {
		isAIButton = isAI;
		addMouseListener(this);
		setImage("/resources/eventailFerme.png");
		setOpaque(false);
	}

	public void setImage(String name) {
		img =  new ImageIcon(getClass().getResource(name)).getImage();
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, 237, (int) Math.round(906 * 0.2), null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isAIButton)
			new Game(true);
		else
			new Game(false);

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setImage("/resources/eventailOuvert.png");
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		setImage("/resources/eventailFerme.png");
		// TODO Auto-generated method stub

	}

}
