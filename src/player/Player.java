package player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Socket.ClientSocket;
import Socket.Massage;
import net.miginfocom.swing.MigLayout;

public class Player extends JPanel
{
	private boolean turn;
	private PlayerRightPanel RightPanel = new PlayerRightPanel();
	private static Point Screenlocation;
	private Point startLocation=null;
	private DrawTargetPanel target;
	
	public Player(JLayeredPane jpl)
	{
		target = new DrawTargetPanel();
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[grow][880][200]", "[grow][grow]"));
		add(target,"cell 1 0 1 2,grow");
		target.setVisible(true);
		
		
		add(RightPanel,"cell 2 0 1 2,grow");
		RightPanel.setVisible(true);
		this.setVisible(true);
		
		
		
		this.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		});
		this.addMouseMotionListener(new MouseMotionListener()
		{
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
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
		Graphics2D g2 = (Graphics2D) g;
		
		super.paintComponent(g);
//		if(me.getHandlist().clickComponent() || me.getFieldlist().clickComponent())
//		{
//			if(this.startLocation == null)
//			{
//				this.startLocation = this.getMousePosition();
//				target.setStartLocation(this.startLocation);
//			}
//			else
//			{
//				g.drawLine((int)this.startLocation.getX(), (int)this.startLocation.getY()
//						, (int)this.getMousePosition().getX(), (int)this.getMousePosition().getY());
//			}
//		}
		
		this.Screenlocation = this.getLocationOnScreen();
	}

	public ClientPlayer getMe()
	{
		return target.getMe();
	}

	public They getThey()
	{
		return target.getThey();
	}

	public void turnEnd()
	{
		ClientSocket.sendMassage(Massage.getMassage(Massage.TurnEnd));
	}
	
	public boolean isTurn()
	{
		return turn;
	}

	public void setTurn(boolean turn)
	{
		this.turn = turn;
		this.RightPanel.setTurn(this.turn);
	}

	public void surrender()
	{
		ClientSocket.sendMassage(Massage.getMassage(Massage.Surrender));
	}

	public PlayerRightPanel getRightPanel()
	{
		return RightPanel;
	}

	public static Point Location()
	{
		return Screenlocation;
	}

	public DrawTargetPanel getTarget()
	{
		return target;
	}
	
	
	
	
}