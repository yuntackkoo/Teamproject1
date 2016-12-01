package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class EffectPanel extends JPanel
{
	int x;
	int y;
	private Point StarPoint;
	public EffectPanel()
	{
		this.setSize(880,720);
		this.addMouseMotionListener(new MouseMotionListener()
		{
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
			}
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawLine((int)this.StarPoint.getX(), (int)this.StarPoint.getY(),
				(int) this.getParent().getMousePosition().getX(), (int)this.getParent().getMousePosition().getY());
		g.setColor(Color.RED);
		g.fillArc(x-7, y-7, 15, 15, 0, 360);
	}
	public void setStarPoint(Point starPoint)
	{
		StarPoint = starPoint;
	}
	
}
