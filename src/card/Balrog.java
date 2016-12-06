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
		this.setTargeting(false);//false �� Ÿ���� ������ �ʿ䰡 ����
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
			this.setCurrentatt(this.getCurrentatt() + 3);
			this.setCurrentlife(this.getCurrentlife() + 4);
		}
		else
		{
			this.setCurrentlife(this.getCurrentlife() + 4);
		}
		hostp.getFieldlist().setChange(true);
	}


	@Override
	public boolean spcondition(ClientPlayer p)
	{
		if(p.getFieldlist().getFiled().size()>=3) // �ڱ� �ڵ�, �ʵ� , ��� �ʵ� ���� ������
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
