package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import card.CardForm;
import dataload.LoadData;

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
	private boolean target;
	private CardForm FocusCard;
	private LoadData data = LoadData.getInstance();
	
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
				if(e.getClickCount() >= 3)
				{
					target = true;
					System.out.println(target);
				}
				else
				{
					e.translatePoint((int)(getMousePosition(true).getX()-e.getX()), (int)(getMousePosition(true).getY()-e.getY()));
					panel.setStarPoint(e.getPoint());
					panel.setVisible(true);
					panel.dispatchEvent(e);
				}
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
		this.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
			}
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
				e.translatePoint((int)(getMousePosition(true).getX()-e.getX()), (int)(getMousePosition(true).getY()-e.getY()));
				panel.dispatchEvent(e);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
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

	public CardForm getFocusCard()
	{
		return FocusCard;
	}
	
	public void Update()
	{
		me.getFieldlist().update();
		me.getHandlist().update();
		they.getFieldlist().update();
		they.getHandlist().update();
		me.getHandlist().checkHand(me);
		this.FocusCard = this.me.getHandlist().focusCheck();
		if(this.FocusCard == null)
			this.FocusCard = this.me.getFieldlist().focusCheck();
		if(this.FocusCard == null)
			this.FocusCard = this.they.getFieldlist().focusCheck();
	}
}
