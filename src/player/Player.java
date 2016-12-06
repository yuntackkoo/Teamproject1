package player;

import java.awt.Point;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Socket.ClientSocket;
import Socket.Massage;
import card.Magic;
import card.Pawn;
import net.miginfocom.swing.MigLayout;

public class Player extends JPanel
{
	private boolean turn;
	private PlayerRightPanel RightPanel = new PlayerRightPanel();
	private LeftPanel leftPanel = new LeftPanel(50);
	private static Point Screenlocation;
	private Point startLocation=null;
	private DrawTargetPanel target;
	
	public Player(JLayeredPane jpl)
	{
		target = new DrawTargetPanel();
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[200][880][200]", "[grow][grow]"));
		add(target,"cell 1 0 1 2,grow");
		target.setVisible(true);
		
		
		add(RightPanel,"cell 2 0 1 2,grow");
		RightPanel.setVisible(true);
		this.setVisible(true);
		
		add(leftPanel,"cell 0 0 1 2,grow");
		leftPanel.setVisible(true);
		RightPanel.setVisible(true);
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
		target.getMe().getHandlist().setturn(this.turn);
		target.getMe().getFieldlist().setturn(this.turn);
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
	
	public void update()
	{
		this.target.Update();
		if(target.getFocusCard() != null)
		{
			if(target.getFocusCard() instanceof Pawn)
			{
				this.leftPanel.getCardPlainPanel().setCard((Pawn) target.getFocusCard());
			}
			else
			{
				this.leftPanel.getCardPlainPanel().setCard((Magic) target.getFocusCard());
			}
		}
		this.leftPanel.setMyLife(target.getMe().getLife());
		this.leftPanel.setTheyLife(target.getThey().getLife());
		this.leftPanel.setMyMana(target.getMe().getMana());
		this.leftPanel.setTheyMana(target.getThey().getMana());
		this.leftPanel.reDraw();
	}
}