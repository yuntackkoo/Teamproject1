package player;

import java.awt.Color;

import Socket.ClientSocket;
import Socket.Massage;
import card.CardForm;
import card.Type1;

public class Player extends ClientPlayer
{
	private boolean turn;
	private String Handle;
	private String targetCard;
	private CardForm a = new Type1(); 
	
	public String getHandle()
	{
		return Handle;
	}

	public void setHandle(String handle)
	{
		Handle = handle;
	}

	public String getTargetCard() {
		return targetCard;
	}

	public void setTargetCard(String targetCard) {
		this.targetCard = targetCard;
	}

	@Override
	public void showUI() 
	{
		super.setVisible(true);
	}

	@Override
	public void showCardEffect(CardForm c) 
	{
		
	}

	@Override
	public void showGraveList() 
	{
		
	}
	

	public Player()
	{
		Massage.setMe(this);
		this.setLayout(null);
		super.setBounds(350, 360, 930, 360);
		super.getHandlist().setLocation(0, 200);
		super.getHandlist().setSize(super.getHandlist().getPreferredSize());
		super.getFieldlist().setLocation(0, 0);
		super.getFieldlist().setSize(super.getFieldlist().getPreferredSize());
		super.getDecklist().setLocation(800, 200);
		super.getDecklist().setSize(super.getDecklist().getPreferredSize());
		super.getGravelist().setLocation(700, 0);
		super.getGravelist().setSize(super.getGravelist().getPreferredSize());
	}
	
	public void turnEnd()
	{
		ClientSocket.sendMassage(Massage.getMassage(Massage.TurnEnd));
	}
	
	public void surrender()
	{
		ClientSocket.sendMassage(Massage.getMassage(Massage.Surrender));
	}
	
	
}