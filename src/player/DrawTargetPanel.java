package player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class DrawTargetPanel extends JPanel
{
	private Point StartLocation;
	/**
	 * Create the panel.
	 */
	private They they = new They();
	private ClientPlayer me = new ClientPlayer();
	private JLabel label;
	private JLayeredPane jpl = new JLayeredPane();
	private EffectPanel panel = new EffectPanel();
	
	public DrawTargetPanel()
	{
		
		this.setLayout(null);
		this.setSize(880, 720);
		this.add(jpl);
		jpl.setBounds(0, 0, 880, 720);
//		this.add(they);
//		they.setBounds(0, 0, 880, 360);
//		this.add(me);
//		me.setBounds(0, 360, 880, 360);
		they.setVisible(true);
		me.setVisible(true);
		panel.setSize(880, 720);
		panel.setBackground(new Color(255,0,0,0));
		
		
		jpl.add(they);
		jpl.add(me);
		me.setLocation(0, 360);
		jpl.add(panel);
		jpl.moveToFront(panel);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
//		if(StartLocation != null)
//		{
//			g.drawLine((int)this.StartLocation.getX(), (int)this.StartLocation.getY()
//					, (int)this.getMousePosition().getX(), (int)this.getMousePosition().getY());
//		}
		//g2.fillArc(300, 300, 300, 300, 0, 360);
		me.getFieldlist().update();
		me.getHandlist().update();
		they.getFieldlist().update();
		they.getHandlist().update();
	}
	
	public void setStartLocation(Point startLocation)
	{
		StartLocation = startLocation;
	}

	public They getThey()
	{
		return they;
	}

	public void setThey(They they)
	{
		this.they = they;
	}

	public ClientPlayer getMe()
	{
		return me;
	}

	public void setMe(ClientPlayer me)
	{
		this.me = me;
	}
	
	
	
}
