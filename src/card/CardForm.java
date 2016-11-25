package card;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JButton;

import Socket.ClientSocket;
import Socket.Massage;
import player.Player;

public abstract class CardForm implements Serializable
{
	private int CardNumber;
	private int CurrentCost;
	private int cost;
	private int loc;
	private int xloc = 0;
	private int yloc = 0;
	private CardForm me = this;
	static public int Deck = 1;
	static public int Field = 2;
	static public int Grave = 3;
	static public int Hand = 4;

	private boolean ShowImage = false;

	public abstract void CardUse(int handle);

	public abstract void effect();
	public abstract void attack(CardForm other);

	public void imageLoad()
	{
	}

	public void tooTipLoad()
	{
	}
	
	public CardForm copy()
	{return null;}
	
	public int getLoc()
	{
		return loc;
	}

	public int getCardNumber()
	{
		return CardNumber;
	}

	public void setCardNumber(int cardNumber)
	{
		CardNumber = cardNumber;
	}

	public int getCost()
	{
		return cost;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}

	public void setLoc(int loc)
	{
		this.loc = loc;
	}


	public int getCurrentCost()
	{
		return CurrentCost;
	}

	public void setCurrentCost(int currentCost)
	{
		CurrentCost = currentCost;
	}

	@Override
	public String toString()
	{
		return "CardForm [CardNumber=" + CardNumber + ", cost=" + cost + "]";
	}
	
}
