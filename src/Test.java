import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import card.Pawn;
import dataload.LoadData;
import main.CardImage;
import main.DeckEditPage;
import main.MainFrame;

public class Test
{

	public static void main(String[] args)
	{
		MainFrame m = new MainFrame();
		//testframe m = new testframe();
	}
}

class testframe extends JFrame
{
	public testframe()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}
}
