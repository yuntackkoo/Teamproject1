package card;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Socket.ClientSocket;
import Socket.Massage;
import dataload.LoadData;
import player.ClientPlayer;
import player.Field;
import player.Hand;
import player.ServerPlayer;

public class Pawn extends CardForm implements Comparable
{
	private String Race;
	private int nativeatt;
	private int nativelife;
	private int currentatt;
	private int currentlife;
	private Pawn me = this;
	private transient LoadData data = LoadData.getInstance();

	public void attack(CardForm other)
	{
		Pawn p = (Pawn) other;
		this.currentlife -= p.currentatt;
		p.currentlife -= this.currentatt;
	}

	public boolean CardUse(int handle)
	{
		Massage tmp = Massage.getMassage(Massage.Summon);
		tmp.setHandle(handle);
		tmp.setCost(super.getCurrentCost());
		ClientSocket.sendMassage(tmp);
		return false;
	}
	
	public CardForm copy()
	{
		CardForm tmp = new Pawn();
		tmp.setCardNumber(super.getCardNumber());
		tmp.setCost(super.getCost());
		tmp.setLoc(super.getLoc());
		Pawn tmp2 = (Pawn)tmp;
		tmp2.currentatt = this.currentatt;
		tmp2.currentlife = this.currentlife;
		tmp2.Race = this.Race;
		tmp2.nativeatt = this.nativeatt;
		tmp2.nativelife = this.nativelife;
		tmp2.me = tmp2;
		return tmp2;
	}
	
	public void copy(CardForm scard)
	{
		Pawn card = (Pawn) scard;
		this.setCardNumber(card.getCardNumber());
		this.setCost(card.getCost());
		this.setLoc(card.getLoc());
		this.currentatt = card.currentatt;
		this.currentlife = card.currentlife;
		this.Race = card.Race;
		this.nativeatt = card.nativeatt;
		this.nativelife = card.nativelife;
	}

	@Override
	public void effect(ServerPlayer hostp,ServerPlayer theyp,int target,boolean spcon)
	{
	}
	public Pawn()
	{}
	
	public Pawn(String race, int att,int life,int cost,int cardnumber)
	{
		this.Race = race;this.nativeatt = att;this.nativelife = life;
		this.currentatt=att;this.currentlife=life;super.setCost(cost);super.setCurrentCost(cost);
		super.setCardNumber(cardnumber);
	}
	

	@Override
	public int compareTo(Object o)
	{
		Pawn comp = (Pawn) o;
		if(super.getCurrentCost() == comp.getCurrentCost() && this.getCurrentatt() == comp.getCurrentatt() && this.getCurrentlife() == comp.getCurrentlife())
			return 0;
		else
			return -1;
	}


	@Override
	public boolean spcondition(ClientPlayer p)
	{
		// TODO Auto-generated method stub
		return false;
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
		return Integer.toString(super.getCardNumber());
	}
	
	public CardForm checkSpecialCard(CardForm card,String CardName)
	{
		try
		{
			String name = "card."+CardName;
			Class<?> tmp = Class.forName(name);
			Pawn rePawn = (Pawn)tmp.newInstance();
			rePawn.copy(card);
			return (CardForm)rePawn;
		}
		catch(IllegalAccessException e1)
		{
			e1.printStackTrace();
			return null;
		}
		catch(InstantiationException e2)
		{
			e2.printStackTrace();
			return null;
		}
		catch(ClassNotFoundException e3)
		{
			return null;
		}
	}

	public String getRace()
	{
		return Race;
	}

	public void setRace(String race)
	{
		Race = race;
	}
	
	
}