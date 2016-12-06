package card;

import Socket.ClientSocket;
import Socket.Massage;
import player.ClientPlayer;
import player.ServerPlayer;

public class Warg extends Pawn
{
	//19
	public Warg()
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
		if(spcon) // Ÿ���� �ʿ��ϸ�?
		{
			this.setCurrentatt(this.getCurrentatt());
		//	System.out.println("���ݷ� ���� ȿ�� �ߵ�");
			this.setCurrentlife(this.getCurrentlife()+1);
		//	System.out.println("ü�� ���� ȿ�� �ߵ�");
		}
		hostp.getFieldlist().setChange(true);
	}

	//	�� ������ ���� ���Ͱ� ��ȯ �Ǿ� ������~~ ��� �������� == �ڿ� ī�� �����ѹ��� �ٿ��ָ� �ȴ�.
	@Override
	public boolean spcondition(ClientPlayer p)
	{
		for(int i=0;i<p.getFieldlist().getFiled().size();i++)
		{
			if(p.getFieldlist().getFiled().get(i).getCardNumber() == 18) // �ڱ� �ڵ�, �ʵ� , ��� �ʵ� ���� ������
			{
				super.setSpcon(true);
				return true;
			}
			else
			{
				super.setSpcon(false);
			}
		}
		return false;
	}


	@Override
	public CardForm copy()
	{
		CardForm tmp = new Warg();
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

