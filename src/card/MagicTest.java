package card;

import player.ServerPlayer;

public class MagicTest extends Magic
{

	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon)
	{
		if(target >= 5)
		{
			target -=5;
			theyp.getFieldlist().getFiled().get(target).setCurrentlife(theyp.getFieldlist().getFiled().get(target).getCurrentlife()-4);
			System.out.println("??");
			theyp.getFieldlist().setChange(true);
		}
		else
		{
			hostp.getFieldlist().getFiled().get(target).setCurrentlife(hostp.getFieldlist().getFiled().get(target).getCurrentlife()-4);
			System.out.println("?");
			hostp.getFieldlist().setChange(true);
		}
	}

	@Override
	public CardForm copy()
	{
		Magic tmp = new MagicTest();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		return tmp;
	}
}