package player;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import Socket.ClientSocket;
import Socket.Massage;
import net.miginfocom.swing.MigLayout;

public class Player extends JPanel
{
	private boolean turn;
	private String Handle;
	private String targetCard;
	private They they = new They();
	private ClientPlayer me = new ClientPlayer();
	private PlayerRightPanel RightPanel = new PlayerRightPanel();
	private static Point Screenlocation;

	public Player()
	{
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[grow][880][200]", "[grow][grow]"));

		add(they, "cell 1 0,grow");
		they.setVisible(true);
		add(me, "cell 1 1,grow");
		me.setVisible(true);
		add(RightPanel,"cell 2 0 1 2,grow");
		RightPanel.setVisible(true);
		this.setVisible(false);
		
		this.addMouseListener(new MouseAdapter()
		{	
		});
	}
	
	public String getHandle()
	{
		return Handle;
	}

	public ClientPlayer getMe()
	{
		return me;
	}

	public They getThey()
	{
		return they;
	}


	public void setHandle(String handle)
	{
		Handle = handle;
	}

	public String getTargetCard() {
		return targetCard;
	}

	public void setTargetCard(String targetCard) {
		this.targetCard = targetCard;
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

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		me.getFieldlist().update();
		me.getHandlist().update();
		they.getFieldlist().update();
		they.getHandlist().update();
		this.Screenlocation = this.getLocationOnScreen();
	}
	
	public static Point Location()
	{
		return Screenlocation;
	}
	
	
	
}