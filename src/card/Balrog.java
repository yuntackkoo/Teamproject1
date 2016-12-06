package card;

import Socket.ClientSocket;
import Socket.Massage;
import player.ClientPlayer;
import player.ServerPlayer;

public class Balrog extends Pawn
{
	//31
	public Balrog()
	{
		this.setTargeting(false);//false 는 타겟팅 지정할 필요가 없다
	}
	@Override
	public boolean CardUse(int handle)
	{
		Massage tmp = Massage.getMassage(Massage.Summon);
		tmp.setHandle(handle);
		tmp.setCost(this.getCurrentCost());
		ClientSocket.sendMassage(tmp);
		return true;
	}


	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon)
	{
		if(spcon)
		{
			for(int i=0;i<hostp.getFieldlist().getFiled().size();i++)
			{
				hostp.getFieldlist().getFiled().get(i).setCurrentlife(hostp.getFieldlist().getFiled().get(i).getCurrentlife() - 2);
			}
			for(int i=0;i<theyp.getFieldlist().getFiled().size();i++)
			{
				theyp.getFieldlist().getFiled().get(i).setCurrentlife(theyp.getFieldlist().getFiled().get(i).getCurrentlife() - 2);
			}
		}
		else
		{
			for(int i=0;i<hostp.getFieldlist().getFiled().size();i++)
			{
				hostp.getFieldlist().getFiled().get(i).setCurrentlife(hostp.getFieldlist().getFiled().get(i).getCurrentlife() - 1);
			}
			for(int i=0;i<theyp.getFieldlist().getFiled().size();i++)
			{
				theyp.getFieldlist().getFiled().get(i).setCurrentlife(theyp.getFieldlist().getFiled().get(i).getCurrentlife() - 1);
			}
		}
		hostp.getFieldlist().setChange(true);
		theyp.getFieldlist().setChange(true);
	}


	@Override
	public boolean spcondition(ClientPlayer p)
	{
		if(p.getGravelist().getGrave().size()>=4) // 자기 핸드, 필드 , 상대 필드 까지 참고가능
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


	@Override
	public CardForm copy()
	{
		CardForm tmp = new Balrog();
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCost(this.getCost());
		tmp.setLoc(this.getLoc());
		tmp.setCurrentCost(this.getCurrentCost());
		tmp.setPesnolnumber(this.getPesnolnumber());
		Pawn tmp2 = (Pawn)tmp;
		tmp2.setCurrentatt(this.getCurrentatt());
		tmp2.setCurrentlife(this.getCurrentlife());
		tmp2.setRace(this.getRace());
		tmp2.setNativeatt(this.getNativeatt());
		tmp2.setNativelife(this.getNativelife());
		return tmp;
	}
	
	
}
