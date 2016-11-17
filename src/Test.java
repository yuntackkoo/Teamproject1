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
	testp p = new testp();
	public testframe()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(p);
		this.pack();
	}
}

class panel extends Canvas
{
	LoadData data = LoadData.getInstance();
	public panel()
	{
		this.setPreferredSize(new Dimension(840, 720));
	}

	@Override
	public void paint(Graphics g)
	{
		BufferedImage tmp;
		Graphics2D g2;
		g2 = (Graphics2D) g;
		tmp = CardImage.get((Pawn)data.getCard(1));
		
		g2.drawImage(tmp, 0, 0, 720, 640, null);
	}
	
}