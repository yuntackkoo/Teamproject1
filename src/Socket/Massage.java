package Socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import card.CardForm;
import player.Player;

public abstract class Massage implements Serializable,Chating,Attacking,Joining
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
	abstract public String getHandle();
	
	abstract public String getTarget();
}

interface Joining
{
	abstract public List<Integer> getDeckList();
	abstract public void setDeckList(List<Integer> list);
}




class MassageFactory extends Massage
{

	@Override
	public String getHandle()
	{
		return null;
	}

	@Override
	public String getTarget()
	{
		return null;
	}

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
	
}

class Draw extends MassageFactory
{
	private int Action = this.Draw;
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
	
	public int getAction()
	{
		return Action;
	}
}

class Attack extends MassageFactory
{
	String Handle;
	String Target;
	
	private int Action = this.Attack;
	
	public Attack()
	{
		Handle = super.getMe().getHandle();
		Target = super.getMe().getTargetCard();
	}
	
	public int getAction()
	{
		return Action;
	}

	@Override
	public String getHandle()
	{
		return this.Handle;
	}

	@Override
	public String getTarget()
	{
		return this.Target;
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