package player;

import java.awt.Dimension;

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
	private ClientPlayer me = new ClientPlayer();
	
	public String getHandle()
	{
		return Handle;
	}

	public ClientPlayer getMe()
	{
		return me;
	}

	public They getThey()
	{
		return they;
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
		setLayout(new MigLayout("", "[grow][880][grow]", "[grow][grow]"));

		add(they, "cell 1 0,grow");
		they.setVisible(true);
		add(me, "cell 1 1,grow");
		me.setVisible(true);
		this.setVisible(false);
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