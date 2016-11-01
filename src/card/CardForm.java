package card;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import Socket.ClientSocket;
import Socket.Massage;
import player.Player;

public abstract class CardForm extends JButton
{
	private int CardNumber;
	private int cost;
	private String loc;
	private int xloc = 0;
	private int yloc = 0;
	private Player onw;
	private transient boolean hand;
	private CardForm me = this;
	static int Deck = 1;
	static int Field = 2;
	static int Grave = 3;
	static int Hand = 4;

	private boolean ShowImage = false;

	public abstract void CardUse();

	public abstract void effect();
	public abstract void attack(CardForm other);

	public void imageLoad()
	{
	}

	public void tooTipLoad()
	{
	}

	public CardForm()
	{
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(100, 100));
		this.addMouseMotionListener(new MouseAdapter()
		{
			@Override
			public void mouseDragged(java.awt.event.MouseEvent e)
			{
				xloc = xloc + Math.max(0,e.getX())-50;
				yloc =yloc + Math.max(0,e.getY())-50;

				add(onw.getHandlist(), me, xloc, yloc);
			}
		});
		this.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				System.out.println("´­¸²");
				super.mousePressed(e);
				onw.setHandle(loc);
			}
		});
	}

	public Player getOnw()
	{
		return onw;
	}

	public void setOnw(Player onw)
	{
		this.onw = onw;
	}

	private void add(Container con, Component c, int locx, int locy)
	{
		con.setBounds(locx, locy, 100, 100);
		con.add(c);
	}

	public String getLoc()
	{
		return loc;
	}

	public void setLoc(String loc)
	{
		this.loc = loc;
	}
	
}

abstract class Pawn extends CardForm
{
	private String Race;
	private int nativeatt;
	private int nativelife;
	private int currentatt;
	private int currentlife;
	private CardForm me = this;
	private static boolean targeting;

	public void attack(CardForm other)
	{
		Pawn p = (Pawn) other;
		this.currentlife -= p.currentatt;
		p.currentlife -= this.currentatt;
	}

	public void CardUse()
	{
		ClientSocket.sendMassage(Massage.getMassage(Massage.Summon));
	}
	
	public Pawn()
	{
		this.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				super.mousePressed(e);
				if(!targeting)
				{
					me.getOnw().setHandle("Field1");
					targeting = true;
				}
				else
				{
					me.getOnw().setTargetCard("Field1");
					ClientSocket.sendMassage(Massage.getMassage(Massage.Attack));
					targeting = false;
				}
			}
			
		});
		super.setLoc("Field1");
	}
	
	

	@Override
	public void effect()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		this.setText(Integer.toString((this.currentlife)));
		super.paintComponent(g);
	}

	public int getNativeatt()
	{
		return nativeatt;
	}

	public void setNativeatt(int nativeatt)
	{
		this.nativeatt = nativeatt;
	}

	public int getNativelife()
	{
		return nativelife;
	}

	public void setNativelife(int nativelife)
	{
		this.nativelife = nativelife;
	}

	public int getCurrentatt()
	{
		return currentatt;
	}

	public void setCurrentatt(int currentatt)
	{
		this.currentatt = currentatt;
	}

	public int getCurrentlife()
	{
		return currentlife;
	}

	public void setCurrentlife(int currentlife)
	{
		this.currentlife = currentlife;
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(this.currentlife);
	}
}