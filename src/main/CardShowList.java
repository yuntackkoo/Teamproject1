package main;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class CardShowList extends JPanel
{
	/**
	 * Create the panel.
	 */
	public CardShowList()
	{

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2;
		g2 = (Graphics2D) g;
		Image Bg;
		Toolkit tool = Toolkit.getDefaultToolkit();
		Bg = tool.getImage("DeckEditBg.jpg");
		g2.drawImage(Bg,0 , 0, 300, 300, this);
	}

}
