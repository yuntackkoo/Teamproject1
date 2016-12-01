package player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		they.setVisible(true);
		me.setVisible(true);
		panel.setSize(880, 720);
		panel.setBackground(new Color(255,0,0,0));
		
		
		jpl.add(they);
		jpl.add(me);
		me.setLocation(0, 360);
		jpl.add(panel);
		jpl.moveToFront(panel);
		panel.setVisible(false);
		
		this.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				panel.setVisible(false);
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				panel.setStarPoint(e.getPoint());
				panel.setVisible(true);
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
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
