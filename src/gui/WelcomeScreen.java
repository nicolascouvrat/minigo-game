package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class WelcomeScreen extends JFrame {
	BufferedImage img;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new WelcomeScreen();

	}

	public WelcomeScreen() {
		setSize(1920, 906);
		setTitle("Main menu - Minigo Game");
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new WelcomeScreenPanel(new ImageIcon(getClass().getResource("/resources/welcomePic.png")).getImage()));
		Image icone=null;
		try {
			icone = ImageIO.read(getClass().getResource("/resources/icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIconImage(icone);
		this.setVisible(true);
	}

}
