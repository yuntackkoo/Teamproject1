package card;

import java.io.Serializable;

import player.ClientPlayer;
import player.ServerPlayer;

public abstract class CardForm implements Serializable,Comparable
{
	private int CardNumber;
	private int CurrentCost;
	private int cost;
	private int loc;
	private CardForm me = this;
	private boolean spcon;
	private int pesnolnumber;
	
	static public int Deck = 1;
	static public int Field = 2;
	static public int Grave = 3;
	static public int Hand = 4;
	
	static public int Self = 100;
	static public int TheyField = 200;
	static public int MyField = 300;

	private boolean ShowImage = false;
	
	public abstract boolean CardUse(int handle);//카드의 사용,카드 효과의 유무
	public abstract void effect(ServerPlayer hostp,ServerPlayer theyp,int target,boolean spcon);//카드의 효과
	public abstract void attack(CardForm other);//공격
	public boolean condition(ClientPlayer p)
	{
		if(p.getMana() >= this.cost)
		{
			return true;
		}
		else
			return false;
	}
	public abstract boolean spcondition(ClientPlayer p);//카드의 특수효과 사용 가능

	public CardForm copy(){return null;}
	abstract public void copy(CardForm card);
	abstract public CardForm checkSpecialCard(CardForm card,String name);
	
	
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
	
	public boolean isSpcon()
	{
		return spcon;
	}
	public void setSpcon(boolean spcon)
	{
		this.spcon = spcon;
	}
	public int getPesnolnumber()
	{
		return pesnolnumber;
	}
	public void setPesnolnumber(int pesnolnumber)
	{
		this.pesnolnumber = pesnolnumber;
	}
}
