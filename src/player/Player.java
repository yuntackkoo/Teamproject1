package player;

import javax.swing.JPanel;

import Socket.ClientSocket;
import Socket.Massage;
import card.CardForm;
import net.miginfocom.swing.MigLayout;

public class Player extends JPanel
{
	private boolean turn;
	private String Handle;
	private String targetCard;
	private They they = new They();
	private ClientPlayer me = new ClientPlayer()
	{
		@Override
		public void showGraveList()
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void showCardEffect(CardForm c)
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	public String getHandle()
	{
		return Handle;
	}

	public ClientPlayer getMe()
	{
		return me;
	}

	public void setMe(ClientPlayer me)
	{
		this.me = me;
	}

	public They getThey()
	{
		return they;
	}

	public void setThey(They they)
	{
		this.they = they;
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
	

	public Player()
	{
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[200][grow][200]", "[grow][grow]"));
		
		
		They they = new They();
		add(they, "cell 1 0,grow");
		add(me,"cell 1 1,grow");
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