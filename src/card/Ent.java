package card;

import Socket.ClientSocket;
import Socket.Massage;
import player.ServerPlayer;

public class Ent extends Pawn
{
	//16
	public Ent()
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
		if(spcon) // 타겟이 필요하면?
		{
			this.setCurrentatt(this.getCurrentatt());
		//	System.out.println("공격력 증가 효과 발동");
			this.setCurrentlife(this.getCurrentlife());
		//	System.out.println("체력 증가 효과 발동");
		}
		hostp.getFieldlist().setChange(true);
	}

	/*	이 문장은 무슨 몬스터가 소환 되어 있을때~~ 라는 문장으로 == 뒤에 카드 고유넘버를 붙여주면 된다.
	@Override
	public boolean spcondition(ClientPlayer p)
	{
		for(int i=0;i<p.getFieldlist().getFiled().size();i++)
		{
			if(p.getFieldlist().getFiled().get(i).getCardNumber() == ) // 자기 핸드, 필드 , 상대 필드 까지 참고가능
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
	}*/


	@Override
	public CardForm copy()
	{
		CardForm tmp = new Ent();
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCost(this.getCost());
		tmp.setLoc(this.getLoc());
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