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
		super.getHandlist().setBounds(0, 260, 600, 100);
		super.getHandlist().setBackground(Color.black);
		super.getHandlist().setLayout(null);
		super.getDecklist().setBounds(600, 210, 300, 150);
		super.getDecklist().setBackground(Color.yellow);
		super.getGravelist().setBounds(600, 100, 100, 100);
		super.getGravelist().setBackground(Color.red);
		super.getFieldlist().setLayout(null);
		super.getFieldlist().setBounds(0, 0, 600, 260);
		super.getFieldlist().setBackground(Color.blue);
		
		
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