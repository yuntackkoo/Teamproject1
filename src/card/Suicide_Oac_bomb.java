package card;

import player.ServerPlayer;

public class Suicide_Oac_bomb extends Magic
{
	 //23
	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon)
	{
		if(target >= 5)
		{
			target = target - 5;
			theyp.getFieldlist().getFiled().get(target).setCurrentlife(theyp.getFieldlist().getFiled().get(target).getCurrentlife()-8);
			
			System.out.println(target);
			System.out.println("??");
		}
		else
		{
			hostp.getFieldlist().getFiled().get(target).setCurrentlife(hostp.getFieldlist().getFiled().get(target).getCurrentlife()-8);
			System.out.println(target);
			System.out.println("?");
		}
		theyp.getFieldlist().setChange(true); // ����
		hostp.getFieldlist().setChange(true); // ����
	}

	@Override
	public CardForm copy()
	{
		Magic tmp = new Suicide_Oac_bomb();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		tmp.setPesnolnumber(this.getPesnolnumber());
		return tmp;
	}
}

