package player;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.text.LayeredHighlighter.LayerPainter;

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
	private Point startLocation=null;
	private DrawTargetPanel target = null;
	private JLayeredPane jlp = JLayeredPane.getLayeredPaneAbove(this);

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
		target = new DrawTargetPanel(this.getSize());
		
		this.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				System.out.println("Á¾·á");
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				System.out.println(e.getPoint());
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
				System.out.println(e.getPoint());
			}
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
		/*if(me.getHandlist().clickComponent() || me.getFieldlist().clickComponent())
		{
			if(this.startLocation == null)
			{
				this.startLocation = this.getMousePosition();
			}
			else
			{
				g.drawLine((int)this.startLocation.getX(), (int)this.startLocation.getY()
						, (int)this.getMousePosition().getX(), (int)this.getMousePosition().getY());
			}
		}*/
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