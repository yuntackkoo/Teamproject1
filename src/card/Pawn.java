package card;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Socket.ClientSocket;
import Socket.Massage;

public class Pawn extends CardForm
{
	private String Race;
	private int nativeatt;
	private int nativelife;
	private int currentatt;
	private int currentlife;
	private Pawn me = this;
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
	
	public CardForm copy()
	{
		CardForm tmp = new Pawn();
		tmp.setCardNumber(super.getCardNumber());
		tmp.setCost(super.getCost());
		tmp.setLoc(super.getLoc());
		tmp.setOnw(super.getOnw());
		Pawn tmp2 = (Pawn)tmp;
		tmp2.currentatt = this.currentatt;
		tmp2.currentlife = this.currentlife;
		tmp2.Race = this.Race;
		tmp2.nativeatt = this.nativeatt;
		tmp2.nativelife = this.nativelife;
		tmp2.me = tmp2;
		return tmp2;
	}
	

	@Override
	public void effect()
	{
		// TODO Auto-generated method stub
		
	}
	public Pawn()
	{}
	
	public Pawn(String race, int att,int life,int cost,int cardnumber)
	{
		this.Race = race;this.nativeatt = att;this.nativelife = life;
		this.currentatt=att;this.currentlife=life;super.setCost(cost);super.setCurrentCost(cost);
		super.setCardNumber(cardnumber);
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
		return "Pawn [Race=" + Race + ", currentatt=" + currentatt + ", currentlife=" + currentlife + ", getCost()="
				+ getCost() + "]";
	}
}