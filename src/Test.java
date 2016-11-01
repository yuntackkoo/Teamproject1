import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;

import javax.swing.JFrame;

import main.MainFrame;

public class Test
{

	public static void main(String[] args)
	{
		
		MainFrame m = new MainFrame();
	}
}

class testframe extends JFrame
{

	public testframe()
	{
		this.setVisible(true);
		this.setSize(1280, 720);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

class testframe2 extends JFrame
{
	cavas c = new cavas();
	Panel p = new Panel();
	public testframe2()
	{
		this.setVisible(true);
		this.setSize(1280, 720);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(p,"Center");
		p.setLayout(new BorderLayout());
		p.add(c,"Center");
	}
}

class cavas extends Canvas
{

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		//this.setBackground(Color.black);
		g2.rotate(Math.toDegrees(135),400F,400F);
		g2.drawArc(400, 400, 200, 200, 0, 90);
	}
	
}