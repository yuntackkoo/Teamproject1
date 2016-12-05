package card;

import Socket.ClientSocket;
import Socket.Massage;
import player.ClientPlayer;
import player.ServerPlayer;

public class WitchKing extends Pawn
{
	@Override
	public boolean CardUse(int handle)
	{
		Massage tmp = Massage.getMassage(Massage.Summon);
		tmp.setHandle(handle);
		tmp.setCost(super.getCurrentCost());
		ClientSocket.sendMassage(tmp);
		System.out.println("À§Ä¡Å·");
		return true;
	}


	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon)
	{
		if(spcon)
		{
			super.setCurrentatt(super.getCurrentatt() + 3);
			super.setCurrentlife(super.getCurrentlife() + 4);
		}
		else
		{
			super.setCurrentlife(super.getCurrentlife() + 4);
		}
		System.out.println("asldkfjklas");
		hostp.getFieldlist().setChange(true);
	}


	@Override
	public boolean spcondition(ClientPlayer p)
	{
		if(p.getFieldlist().getFiled().size()>=3)
		{
			super.setSpcon(true);
			return true;
		}
		else
		{
			super.setSpcon(false);
			return false;
		}
	}
	
	
}
