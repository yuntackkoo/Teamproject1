import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import main.CardSelect;

public class Test
{

	public static void main(String[] args)
	{
		//MainFrame m = new MainFrame();
		testframe m = new testframe();
		
	}
}

class testframe extends JFrame
{
	CardSelect p = new CardSelect();
	public testframe()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(p);
		p.setVisible(true);
		p.setBounds(0, 0, 1280, 720);
		this.pack();
	}
}

class panel extends Canvas
{
	public panel()
	{
		this.setPreferredSize(new Dimension(840, 720));
	}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2;
		g2 = (Graphics2D) g;
		Image Bg;
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6F);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Bg = tool.getImage("DeckEditBg.jpg");
		g2.setComposite(alpha);
		g2.drawImage(Bg,0 , 0, 840, 720, this);
	}
	
}