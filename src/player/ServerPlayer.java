package player;

import java.util.LinkedList;
import java.util.List;

import Socket.Massage;
import Socket.ReplyMassage;
import card.CardForm;
import dataload.LoadData;

public class ServerPlayer extends PlayerBase
{
	private boolean host;
	private LoadData data = LoadData.getInstance();
	
	public ServerPlayer(boolean host)
	{
		this.host = host;
	}
	
	public ReplyMassage mProcess(Massage m)
	{
		switch(m.getAction())
		{
			case Massage.Draw:
			{
				CardForm drawcard = super.getDecklist().disCard(0);
				super.getDecklist().setChange(true);
				if(super.getHandlist().getHand().size() < 10)
				{
					super.getHandlist().addCard(drawcard);
					super.getHandlist().setChange(true);
				}
				else
				{
					super.getGravelist().addCard(drawcard);
					super.getGravelist().setChange(true);
				}
				System.out.println(super.getDecklist().getDeckSize());
				break;
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
				break;
			}
		}
		
		return null;
		
	}
	
	
}
