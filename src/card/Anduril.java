package card;

import player.ServerPlayer;

public class Anduril extends Magic
{
	 //21
	@Override
	public void effect(ServerPlayer hostp, ServerPlayer theyp, int target, boolean spcon) //host �ڽ� they �� ���
	{
		
		for( int i = 0 ; i >= hostp.getFieldlist().getFiled().size(); i++)
		{
			hostp.getFieldlist().getFiled().get(target).setCurrentatt(hostp.getFieldlist().getFiled().get(target).getCurrentatt()+2);

			System.out.println(target);
			System.out.println("?");
		}
		theyp.getFieldlist().setChange(true); // ����
		hostp.getFieldlist().setChange(true); // ����
	}

	@Override
	public CardForm copy()
	{
		Magic tmp = new Anduril();
		tmp.setCost(this.getCost());
		tmp.setCardNumber(this.getCardNumber());
		tmp.setCurrentCost(this.getCurrentCost());
		tmp.setPesnolnumber(this.getPesnolnumber());
		return tmp;
	}
}