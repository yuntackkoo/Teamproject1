package card;

import player.ServerPlayer;

public class Cure_of_Soul  extends Magic
{
	 //21
	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon) //host 자신 they 가 상대
	{
		
		if(target >= 5)
		{
			target = target - 5;
			theyp.getFieldlist().getFiled().get(target).setCurrentlife(theyp.getFieldlist().getFiled().get(target).getCurrentlife()+2);
			
			System.out.println(target);
			System.out.println("??");
		}
		else
		{
			hostp.getFieldlist().getFiled().get(target).setCurrentlife(hostp.getFieldlist().getFiled().get(target).getCurrentlife()+2);
			System.out.println(target);
			System.out.println("?");
		}
		theyp.getFieldlist().setChange(true); // 갱신
		hostp.getFieldlist().setChange(true); // 갱신
	}

	@Override
	public CardForm copy()
	{
		Magic tmp = new Fireball();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		tmp.setPesnolnumber(this.getPesnolnumber());
		return tmp;
	}
}