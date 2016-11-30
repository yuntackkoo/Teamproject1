package player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class DrawTargetPanel extends JPanel
{
	private Point StartLocation;
	/**
	 * Create the panel.
	 */
	public DrawTargetPanel()
	{
		this.setBackground(new Color(255,0,0,0));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if(StartLocation != null)
		{
			g.drawLine((int)this.StartLocation.getX(), (int)this.StartLocation.getY()
					, (int)this.getMousePosition().getX(), (int)this.getMousePosition().getY());
		}
		//g2.fillArc(300, 300, 300, 300, 0, 360);
	}
	
	public void setStartLocation(Point startLocation)
	{
		StartLocation = startLocation;
	}
}
