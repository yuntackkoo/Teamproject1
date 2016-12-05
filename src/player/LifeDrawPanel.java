package player;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LifeDrawPanel extends JPanel
{
	private int Life;
	private int MaxLife;

	@Override
	protected void paintComponent(Graphics g)
	{
		double plife;
		super.paintComponent(g);
		try
		{
			plife = (double)(this.Life/MaxLife);
		}
		catch(ArithmeticException e)
		{
			plife = 0;
		}
		g.setColor(Color.red);
		g.fillRect(0, 0, (int)(this.getWidth()*plife), this.getHeight());
	}
	
	public int getLife()
	{
		return Life;
	}

	public void setLife(int life)
	{
		Life = life;
	}

	public int getMaxLife()
	{
		return MaxLife;
	}

	public void setMaxLife(int maxLife)
	{
		MaxLife = maxLife;
	}
	
	
}
