package card;

import Socket.ClientSocket;
import Socket.Massage;
import dataload.LoadData;
import player.ClientPlayer;
import player.ServerPlayer;

public class Magic extends CardForm implements Comparable
{
	transient LoadData data = LoadData.getInstance();
	
	public Magic()
	{
		super.setTargeting(true);
	}
	
	public Magic(int cost,int cardnumber)
	{
		super.setCost(cost);
		super.setCurrentCost(cost);
		super.setCardNumber(cardnumber);
	}

	@Override
	public void attack(CardForm other)
	{}
	
	@Override
	public boolean CardUse(int handle)
	{
		Massage tmp = Massage.getMassage(Massage.CardUse);
		tmp.setHandle(handle);
		tmp.setCost(super.getCurrentCost());
		ClientSocket.sendMassage(tmp);
		return true;
	}

	@Override
	public void effect(ServerPlayer hostp,ServerPlayer theyp,int target,boolean spcon)
	{
		
	}
	
	
	
	@Override
	public CardForm copy()
	{
		Magic tmp = new Magic();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		return tmp;
	}

	public void copy(CardForm card)
	{
		this.setCost(card.getCost());
		this.setCurrentCost(this.getCost());
		this.setCardNumber(card.getCardNumber());
	}
	
	public CardForm checkSpecialCard(CardForm card,String CardName)
	{
		try
		{
			String name = "card."+CardName;
			Class<?> tmp = Class.forName(name);
			Magic rePawn = (Magic)tmp.newInstance();
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
	
	@Override
	public boolean spcondition(ClientPlayer p)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		Magic comp = (Magic) o;
		if(super.getCurrentCost() == comp.getCurrentCost() && super.getCardNumber() == comp.getCardNumber())
			return 0;
		else
			return -1;
	}
	
	
}