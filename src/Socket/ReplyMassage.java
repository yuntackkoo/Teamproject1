package Socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import card.CardForm;
import player.ServerPlayer;

public abstract class ReplyMassage implements Serializable,RChating,RAttacking
{
	static public final int Draw = 1;
	static public final int Chat = 2;
	static public final int OK = 3;
	static public final int Summon = 4;
	static public final int Attack = 5;
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
	abstract List<CardForm> getMe();
	abstract List<CardForm> getThey();
	abstract void setMe(List<CardForm> me);
	abstract void setThey(List<CardForm> they);
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
	public List<CardForm> getMe()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CardForm> getThey()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMe(List<CardForm> me)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setThey(List<CardForm> they)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}


class RDraw extends ReplyMassageFactory
{
	public RDraw()
	{
		
	}
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
	List<CardForm> Me = new ArrayList<>();
	List<CardForm> They = new ArrayList<>();
	@Override
	public List<CardForm> getMe()
	{
		return Me;
	}
	@Override
	public List<CardForm> getThey()
	{
		return They;
	}
	@Override
	public void setMe(List<CardForm> me)
	{
		this.Me.clear();
		this.Me.addAll(me);
	}
	@Override
	public void setThey(List<CardForm> they)
	{
		this.They.clear();
		this.They.addAll(they);
	}
	
}
