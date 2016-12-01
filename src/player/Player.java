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