package player;

import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import card.CardForm;

public class Hand extends JPanel implements CardTrans
{
	private List<CardForm> Hand = new LinkedList<>();
	private boolean[] hand_target = new boolean[10];
	private CardViewer[] Component = new CardViewer[10];
	private boolean change;
	
	@Override
	public void addCard(CardForm Card) 
	{
		Hand.add(Card);
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Hand.remove(i);
	}
	
	public Hand()
	{
		this.setLayout(new GridLayout());
		for(int i =0;i<10;i++)
		{
			this.add(Component[i] = new CardViewer());
		}
	}
	
	public List<CardForm> getHand()
	{
		return this.Hand;
	}
	
	public void setHand(List<CardForm> hand)
	{
		this.Hand = hand;
	}
	
	public void update()
	{
		for(int i=0; i<10;i++)
		{
			try
			{
				Component[i].setCard(Hand.get(i));
				Component[i].repaint();
			}
			catch(Exception e)
			{}
		}
	}
	
	public boolean isChange()
	{
		return change;
	}
	public void setChange(boolean change)
	{
		this.change = change;
	}
	
}
