package Socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public static final ReplyMassage getRMassage(boolean host,ServerPlayer vhostp,ServerPlayer vtheyp)
	{
		Update rm = new Update();
		ServerPlayer hostp;
		ServerPlayer theyp;
		if(host)
		{
			hostp = vhostp;
			theyp = vtheyp;
		}
		else
		{
			hostp = vtheyp;
			theyp = vhostp;
		}
		//호스트의 덱의 변화
		if (hostp.getDecklist().isChange())
		{
			rm.setDeck(rm.MyDeck, hostp.getDecklist().getDeckSize());
		}
		else
		{
			rm.setDeck(rm.MyDeck, -1);
		}
		
		//호스트의 패의 변화
		if (hostp.getHandlist().isChange())
		{
			for(int i =0;i<hostp.getHandlist().getHand().size();i++)
			{
				rm.getMeHand().add(hostp.getHandlist().getHand().get(i).copy());
			}
		}
		else
		{
			rm.setMeHand(null);
		}
		
		//호스트의 묘지의 변화
		if (hostp.getGravelist().isChange())
		{
			for(int i = 0;i<hostp.getGravelist().getGrave().size();i++)
			{
				rm.getGrave(Updating.MyGrave).add(hostp.getGravelist().getGrave().get(i).copy());
			}
		}
		else
		{
			rm.setGrave(Updating.MyGrave, null);
		}
		
		//호스트의 필드의 변화
		if (hostp.getFieldlist().isChange())
		{
			for(int i = 0;i<hostp.getFieldlist().getFiled().size();i++)
			{
				rm.getField(Updating.MyGrave).add((Pawn) hostp.getFieldlist().getFiled().get(i).copy());
			}
		}
		else
		{
			rm.setField(Updating.MyField, null);
		}
		
		//상대방의 덱의 변화
		if (theyp.getDecklist().isChange())
		{
			rm.setDeck(rm.TheyDeck, theyp.getDecklist().getDeckSize());
		}
		else
		{
			rm.setDeck(rm.TheyDeck, -1);
		}
		
		//상대방의 패의 변화
		if (theyp.getHandlist().isChange())
		{
			rm.setTheyHand(theyp.getHandlist().getHand().size());
		}
		else
		{
			rm.setTheyHand(null);
		}
		
		//상대방의 필드의 변화
		if (theyp.getFieldlist().isChange())
		{
			for(int i = 0;i<theyp.getFieldlist().getFiled().size();i++)
			{
				rm.getField(Updating.TheyField).add((Pawn) theyp.getFieldlist().getFiled().get(i).copy());
			}
		}
		else
		{
			rm.setField(Updating.TheyField, null);
		}
		
		//상대방의 묘지의 변화
		if (theyp.getGravelist().isChange())
		{
			for(int i = 0;i<theyp.getGravelist().getGrave().size();i++)
			{
				rm.getGrave(Updating.TheyGrave).add(theyp.getGravelist().getGrave().get(i).copy());
			}
		}
		else
		{
			rm.setGrave(Updating.TheyField, null);
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
	public List<Pawn> getField(int own)
	{return null;}
	@Override
	public void setField(int own, List<Pawn> Field)
	{}
	@Override
	public List<CardForm> getGrave(int own)
	{return null;}
	@Override
	public void setGrave(int own, List<CardForm> Grave)
	{}
	@Override
	public Integer getDeck(int own)
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
	public Integer getTheyHand()
	{return 0;}
	@Override
	public void setTheyHand(Integer Hand)
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
	
	List<Pawn> MyField = new ArrayList<>();
	List<Pawn> TheyField = new ArrayList<>();
	List<CardForm> MyGrave = new ArrayList<>();
	List<CardForm> TheyGrave = new ArrayList<>();
	
	Integer[] Deck = new Integer[2];
	List<CardForm> MyHand = new ArrayList<>();
	Integer theyHand;
	
	public Update()
	{
		
		Deck[Updating.MyDeck] = new Integer(1);
		Deck[Updating.TheyDeck] = new Integer(1);
	}
	
	
	
	@Override
	public List<Pawn> getField(int own)
	{
		if(own == Updating.MyField)
		{
			return this.MyField;
		}
		else
		{
			return this.TheyField;
		}
	}

	@Override
	public void setField(int own, List<Pawn> Field)
	{
		if(own == Updating.MyField)
		{
			this.MyField = Field;
		}
		else
		{
			this.TheyField = Field;
		}
	}

	@Override
	public List<CardForm> getGrave(int own)
	{
		if(own == Updating.MyGrave)
		{
			return this.MyGrave;
		}
		else
		{
			return this.TheyGrave;
		}
	}

	@Override
	public void setGrave(int own, List<CardForm> Grave)
	{
		if(own == Updating.MyGrave)
		{
			this.MyGrave = Grave;
		}
		else
		{
			this.TheyGrave = Grave;
		}
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
	public Integer getTheyHand()
	{
		return this.theyHand;
	}

	@Override
	public void setTheyHand(Integer hand)
	{
		this.theyHand = hand;
	}
	
}
