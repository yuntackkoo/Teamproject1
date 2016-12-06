package card;

import player.ServerPlayer;

public class Horn_of_Gondor extends Magic
{
	 //29
	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon) //host 자신 they 가 상대
	{
		
		for( int i = 0 ; i >= hostp.getFieldlist().getFiled().size(); i++)
		{
			hostp.getFieldlist().getFiled().get(target).setCurrentatt(hostp.getFieldlist().getFiled().get(target).getCurrentatt()+1);

			System.out.println(target);
			System.out.println("?");
		}
		theyp.getFieldlist().setChange(true); // 갱신
		hostp.getFieldlist().setChange(true); // 갱신
	}

	@Override
	public CardForm copy()
	{
		Magic tmp = new Horn_of_Gondor();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		tmp.setPesnolnumber(this.getPesnolnumber());
		return tmp;
	}
}