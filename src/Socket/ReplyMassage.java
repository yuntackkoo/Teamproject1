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

public abstract class ReplyMassage implements Serializable,RChating,Updating,TurnChange
{
	static public final int Draw = 1;
	static public final int Chat = 2;
	static public final int OK = 3;
	static public final int Summon = 4;
	static public final int Attack = 5;
	static public final int Update = 6;
	static public final int JOIN = 98;
	static public final int TurnEnd = 99;
	static public final int TurnStart = 100;
	static public final int Surrender = 101; 
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
		else if(action == 98)
		{
			return new GameStart();
		}
		return null;
	}
	
	public static final ReplyMassage getRMassage(int action)
	{
		switch(action)
		{
			case ReplyMassage.TurnEnd:
			{
				return new RTurnEnd();
			}
			case ReplyMassage.TurnStart:
			{
				return new RTurnStart();
			}
		}
		return null;
	}
	
	public static final ReplyMassage getRMassage(boolean host,ServerPlayer hostp,ServerPlayer theyp)
	{
		Update rm = new Update();
		if(host)
		{
			if(hostp.getDecklist().isChange())
			{
				rm.setDeck(rm.MyDeck, hostp.getDecklist().getDeckSize());
			}
			if(hostp.getHandlist().isChange())
			{
				rm.setMeHand(hostp.getHandlist().getHand());
			}
			if(hostp.getGravelist().isChange())
			{
				rm.setGrave(rm.MyGrave, hostp.getGravelist().getGrave());
			}
			if(hostp.getFieldlist().isChange())
			{
				rm.setField(rm.MyField, hostp.getFieldlist().getFiled());
			}
			
			
			if(theyp.getDecklist().isChange())
			{
				rm.setDeck(rm.theyHand, theyp.getDecklist().getDeckSize());
			}
			if(theyp.getHandlist().isChange())
			{
				rm.setTheyHand(theyp.getHandlist().getHand().size());
			}
			if(theyp.getGravelist().isChange())
			{
				rm.setGrave(rm.TheyGrave, theyp.getGravelist().getGrave());
			}
			if(theyp.getFieldlist().isChange())
			{
				rm.setField(rm.TheyField, theyp.getFieldlist().getFiled());
			}
		}
		else
		{
			if(theyp.getDecklist().isChange())
			{
				rm.setDeck(rm.MyDeck, theyp.getDecklist().getDeckSize());
			}
			if(theyp.getHandlist().isChange())
			{
				rm.setMeHand(theyp.getHandlist().getHand());
			}
			if(theyp.getGravelist().isChange())
			{
				rm.setGrave(rm.MyGrave, theyp.getGravelist().getGrave());
			}
			if(theyp.getFieldlist().isChange())
			{
				rm.setField(rm.MyField, theyp.getFieldlist().getFiled());
			}
			
			
			if(hostp.getDecklist().isChange())
			{
				rm.setDeck(rm.theyHand, hostp.getDecklist().getDeckSize());
			}
			if(hostp.getHandlist().isChange())
			{
				rm.setTheyHand(hostp.getHandlist().getHand().size());
			}
			if(hostp.getGravelist().isChange())
			{
				rm.setGrave(rm.TheyGrave, hostp.getGravelist().getGrave());
			}
			if(hostp.getFieldlist().isChange())
			{
				rm.setField(rm.TheyField, hostp.getFieldlist().getFiled());
			}
		}
		return rm;
	}
	
}

interface RChating
{
	abstract public String getChat();
	abstract public void setChat(String chat);
}



class ReplyMassageFactory extends ReplyMassage
{
	@Override
	public void setUpdate(ReplyMassage rm)
	{}

	@Override
	public ReplyMassage getUpdate()
	{return null;}

	@Override
	public String getChat() {return null;}

	@Override
	public void setChat(String chat) {}
	
	@Override
	public int getAction() {return 0;}
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
	public void setGrave(int own, List<CardForm> Grave)
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

class RTurnEnd extends ReplyMassageFactory
{
	private int action = super.TurnEnd;
	private ReplyMassage update;
	@Override
	public void setUpdate(ReplyMassage rm)
	{
		this.update = rm;
	}
	@Override
	public ReplyMassage getUpdate()
	{
		return this.update;
	}
	@Override
	public int getAction()
	{
		return this.action;
	}
	
	
}

class RTurnStart extends ReplyMassageFactory
{
	private int action = super.TurnStart;
	private ReplyMassage update;
	@Override
	public void setUpdate(ReplyMassage rm)
	{
		this.update = rm;
	}
	@Override
	public ReplyMassage getUpdate()
	{
		return this.update;
	}
	@Override
	public int getAction()
	{
		return this.action;
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
	public void setGrave(int own, List<CardForm> Grave)
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
