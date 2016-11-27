package Socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import card.CardForm;
import player.Player;

public abstract class Massage implements Serializable,Chating,Joining,Selecting,Attacking
{
	static public final int Draw = 1;
	static public final int Chat = 2;
	static public final int OK = 3;
	static public final int Summon = 4;
	static public final int Attack = 5;
	static public final int DeckList = 97;
	static public final int JOIN = 98;
	static public final int TurnEnd = 99;
	static public final int Surrender = 100;
	
	private int Action;
	
	private static Player me;
	
	public static void setMe(Player me)
	{
		Massage.me = me;
	}


	public int getAction() {
		return Action;
	}
	

	public static Player getMe()
	{
		return me;
	}


	public static final Massage getMassage(int action)
	{
		if(action == 1)
		{
			return new Draw();
		}
		else if (action == 2)
		{
			return new Chat();
		}
		else if(action == 3)
		{
			return new OK();
		}
		else if(action == 4)
		{
			return new Summon();
		}
		else if(action == 5)
		{
			return new Attack();
		}
		else if(action == 98)
		{
			return new Join();
		}
		else if(action == 99)
		{
			return new TurnEnd();
		}
		else
		{
			return new Surrender();
		}
	}
	
}

interface Chating
{
	abstract public void setChat(String chat);
	
	abstract public String getChat();
}

interface Attacking
{
	abstract public void setMyFieldCard(int handle);
	abstract public int getMyFieldCard();
	
	abstract public void setAttackTarget(int target);
	abstract public int getAttackTarget();
}

interface Joining
{
	abstract public List<Integer> getDeckList();
	abstract public void setDeckList(List<Integer> list);
}




class MassageFactory extends Massage
{
	
	
	@Override
	public void setMyFieldCard(int handle)
	{}

	@Override
	public int getMyFieldCard()
	{return 0;}

	@Override
	public void setAttackTarget(int target)
	{}

	@Override
	public int getAttackTarget()
	{return 0;}

	@Override
	public void setChat(String chat) {	}
	
	@Override
	public List<Integer> getDeckList()
	{return null;}

	@Override
	public void setDeckList(List<Integer> list)
	{}

	@Override
	public String getChat() {return null;}

	@Override
	public Integer getHandle()
	{return null;}

	@Override
	public void setHandle(Integer handle)
	{}
	
	
}

class Draw extends MassageFactory
{
	private int Action = this.Draw;

	@Override
	public int getAction()
	{
		return this.Action;
	}
}

class Chat extends MassageFactory
{
	private int Action = this.Chat;
	private String chat;
	
	@Override
	public int getAction() {
		return this.Action;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getChat() {
		return chat;
	}
	
}

class OK extends MassageFactory
{
	private int Action=this.OK;
}

class Join extends MassageFactory
{
	private int Action = this.JOIN;
	private List<Integer> decklist;

	public int getAction() {
		return Action;
	}

	@Override
	public List<Integer> getDeckList()
	{
		return this.decklist;
	}

	@Override
	public void setDeckList(List<Integer> list)
	{
		this.decklist = list;
	}
}

class Summon extends MassageFactory
{
	private int Action = this.Summon;
	private Integer handle;
	
	public int getAction()
	{
		return Action;
	}

	@Override
	public Integer getHandle()
	{
		return this.handle;
	}

	@Override
	public void setHandle(Integer handle)
	{
		this.handle = new Integer(handle);
	}
	
}

class Attack extends MassageFactory
{
	private int handle;
	private int target;
	
	private int Action = this.Attack;
	
	public int getAction()
	{
		return Action;
	}

	@Override
	public void setMyFieldCard(int handle)
	{
		this.handle = handle;
	}

	@Override
	public int getMyFieldCard()
	{
		return this.handle;
	}

	@Override
	public void setAttackTarget(int target)
	{
		this.target = target;
	}

	@Override
	public int getAttackTarget()
	{
		return this.target;
	}
	
	
}

class TurnEnd extends MassageFactory
{
	private int Action = this.TurnEnd;

	@Override
	public int getAction()
	{
		return this.Action;
	}
	
}

class Surrender extends MassageFactory
{
	private int Action = this.Surrender;
}