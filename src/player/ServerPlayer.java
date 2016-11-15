package player;

import Socket.Massage;
import Socket.ReplyMassage;

public class ServerPlayer extends PlayerBase
{
	private boolean turn;
	
	public ServerPlayer()
	{
		
	}
	
	public ReplyMassage mProcess(Massage m)
	{
		switch(m.getAction())
		{
			case Massage.Draw:
			{
				super.setDeck(super.getDeck()-1);
				if(super.getDeck() < 10)
				{
					super.setHand(super.getHand() + 1);
					super.setDeck(super.getDeck() - 1);
					super.getHandlist().addCard(super.getDecklist().disCard(0));
					return ReplyMassage.getRMassage(ReplyMassage.Draw,this);
				}
				else
				{
					super.setGrave(super.getGrave() + 1);
					super.setDeck(super.getDeck() - 1);
					super.getDecklist().addCard(super.getDecklist().disCard(0));
					return ReplyMassage.getRMassage(ReplyMassage.Draw, this);
				}
			}
			case Massage.Chat:
			{
				ReplyMassage rm = ReplyMassage.getRMassage(ReplyMassage.Chat, this);
				rm.setChat(m.getChat());
				return rm;
			}
			case Massage.Attack:
			{
				ReplyMassage rm = ReplyMassage.getRMassage(ReplyMassage.Attack, this);
				return rm;
			}
		}
		
		return null;
		
	}
	
	public void setTurn(boolean turn) 
	{
		this.turn = turn;
	}
	
}
