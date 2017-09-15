package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gameMechanics.Game;
import goBoard.Board;

public class Frame extends JFrame {
	Dimension frameSize = new Dimension(1400, 900);
	FlowLayout frameLayout = new FlowLayout();
	AdditionalFunctionsPanel functionPanel;

	public Frame(Game g) {
		setTitle("Go Board - Minigo Game");
		setSize(frameSize);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Box b = Box.createHorizontalBox();
		functionPanel = new AdditionalFunctionsPanel(g);
		b.add(new Board(g));
		b.add(functionPanel);
		getContentPane().add(b);
		setJMenuBar(new MenuBar(g).getMenuBar());
//		Image icone = Toolkit.getDefaultToolkit().getImage("icon.png");
		Image icone=null;
		try {
			icone = ImageIO.read(getClass().getResource("/resources/icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIconImage(icone);
		setVisible(true);
	}

	public AdditionalFunctionsPanel getFunctionPanel() {
		return functionPanel;
	}

}
