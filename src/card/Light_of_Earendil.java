package card;

import player.ServerPlayer;

public class Light_of_Earendil extends Magic
{
	 //27
	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon) //host �ڽ� they �� ���
	{
		
		for( int i = 0 ; i >= hostp.getFieldlist().getFiled().size(); i++)
		{
			hostp.getFieldlist().getFiled().get(target).setCurrentatt(hostp.getFieldlist().getFiled().get(target).getCurrentatt()+2);
			hostp.getFieldlist().getFiled().get(target).setCurrentlife(hostp.getFieldlist().getFiled().get(target).getCurrentlife()+2);
			System.out.println(target);
			System.out.println("?");
		}
		theyp.getFieldlist().setChange(true); // ����
		hostp.getFieldlist().setChange(true); // ����
	}

	@Override
	public CardForm copy()
	{
		Magic tmp = new Light_of_Earendil();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		tmp.setPesnolnumber(this.getPesnolnumber());
		return tmp;
	}
}