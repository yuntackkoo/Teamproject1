package Socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import card.CardForm;
import card.Pawn;
import player.ServerPlayer;

public abstract class ReplyMassage implements Serializable,RChating,RAttacking,Updating
{
	static public final int Draw = 1;
	static public final int Chat = 2;
	static public final int OK = 3;
	static public final int Summon = 4;
	static public final int Attack = 5;
	static public final int Update = 6;
	static public final int JOIN = 98;
	static public final int TurnEnd = 99;
	static public final int Surrender = 100;
	static public final int GameStart = 999;
	
	public abstract int getAction();
	

	public static final ReplyMassage getRMassage(int action,ServerPlayer p)
	{
		if(action == 1)
		{
			return null;
		}
		else if (action == 2)
		{
			return new Rchat();
		}
		else if(action == 3)
		{
			return null;
		}
		else if(action == 4)
		{
			return null;
		}
		else if(action == 5)
		{
			return new RAttack();
		}
		else if(action == 98)
		{
			return new GameStart();
		}
		else if(action == 99)
		{
			return null;
		}
		else
		{
			return null;
		}
	}
	
}

interface RChating
{
	abstract public String getChat();
	abstract public void setChat(String chat);
}

interface Drawing
{
	abstract public int getHand();
	abstract public CardForm getDrawCard();
	abstract public List getDeckList();
	abstract public int getDeck();
}

interface RAttacking
{
	abstract List<Pawn> getMe();
	abstract List<Pawn> getThey();
	abstract void setMe(List<Pawn> me);
	abstract void setThey(List<Pawn> they);
}

class ReplyMassageFactory extends ReplyMassage
{
	@Override
	public String getChat() {return null;}

	@Override
	public void setChat(String chat) {}
	
	@Override
	public int getAction() {return 0;}
	
	@Override
	public List<Pawn> getMe()
	{return null;}

	@Override
	public List<Pawn> getThey()
	{return null;}
	@Override
	public void setMe(List<Pawn> me)
	{}
	@Override
	public void setThey(List<Pawn> they)
	{}
	@Override
	public Map<Integer, List> getField()
	{return null;}
	@Override
	public void setField(int own, List<Pawn> Field)
	{}
	@Override
	public Map<Integer, List> getGrave()
	{return null;}
	@Override
	public void setGrave(int own, List<Pawn> Grave)
	{}
	@Override
	public int getDeck(int own)
	{return 0;}
	@Override
	public void setDeck(int own, int value)
	{}
	@Override
	public List<CardForm> getMeHand()
	{return null;}
	@Override
	public void setMeHand(List<CardForm> Hand)
	{}
	@Override
	public int getTheyHand()
	{return 0;}
	@Override
	public void setTheyHand(int Hand)
	{}
	
	
}

class GameStart extends ReplyMassageFactory
{
	private int Action = 999;
	public int getAction() {
		return Action;
	}
}

class Rchat extends ReplyMassageFactory implements RChating
{
	private int Action = ReplyMassage.Chat;
	String chat;
	
	public int getAction() {return this.Action;}
	
	@Override
	public String getChat() 
	{
		return this.chat;
	}
	
	@Override
	public void setChat(String chat) 
	{
		this.chat = chat;
	}
}

class RAttack extends ReplyMassageFactory
{
	private int Action = ReplyMassage.Attack;
	
	public int getAction()
	{
		return this.Action;
	}
	List<Pawn> Me = new ArrayList<>();
	List<Pawn> They = new ArrayList<>();
	@Override
	public List<Pawn> getMe()
	{
		return Me;
	}
	@Override
	public List<Pawn> getThey()
	{
		return They;
	}
	@Override
	public void setMe(List<Pawn> me)
	{
		this.Me = new LinkedList<>();
		for(int i=0;i<me.size();i++)
		{
			this.Me.add((Pawn) me.get(i).copy());
		}
	}
	@Override
	public void setThey(List<Pawn> they)
	{
		this.They = new LinkedList<>();
		for(int i=0;i<they.size();i++)
		{
			this.They.add((Pawn) they.get(i).copy());
		}
	}
	@Override
	public String toString()
	{
		return Me.get(0).toString()+They.get(0).toString();
	}
}

class Update extends ReplyMassageFactory
{
	@Override
	public int getAction()
	{
		return super.Update;
	}
	
	Map<Integer, List> Field = null;
	Map<Integer, List> Grave = null;
	int[] Deck = new int[2];
	List<CardForm> MyHand = null;
	int theyHand;
	
	@Override
	public Map<Integer, List> getField()
	{
		return this.Field;
	}

	@Override
	public void setField(int own, List<Pawn> Field)
	{
		this.Field.put(own, Field);
	}

	@Override
	public Map<Integer, List> getGrave()
	{
		return this.Grave;
	}

	@Override
	public void setGrave(int own, List<Pawn> Grave)
	{
		this.Grave.put(own, Grave);
	}

	@Override
	public void setDeck(int own, int value)
	{
		this.Deck[own] = value;
	}

	@Override
	public List<CardForm> getMeHand()
	{
		return this.MyHand;
	}

	@Override
	public void setMeHand(List<CardForm> Hand)
	{
		this.MyHand = Hand;
	}

	@Override
	public int getTheyHand()
	{
		return this.theyHand;
	}

	@Override
	public void setTheyHand(int hand)
	{
		this.theyHand = hand;
	}
	
}
