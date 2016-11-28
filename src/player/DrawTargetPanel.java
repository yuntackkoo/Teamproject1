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
	public DrawTargetPanel(Dimension d)
	{
		this.setBackground(new Color(255,0,0,0));
		this.setSize(d);
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
	}
	
	public void setStartLocation(Point startLocation)
	{
		StartLocation = startLocation;
	}
}
