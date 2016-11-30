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
	private boolean they;
	private int haveCard;
	
	@Override
	public void addCard(CardForm Card) 
	{
		Card.setLoc(CardForm.Hand);
		Hand.add(Card.copy());
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
			this.add(Component[i] = new CardViewer(CardForm.Hand));
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
		if(change)
		{
			if(!they)
			{
				for(int i=0; i<10;i++)
				{
					try
					{
						Component[i].setCard(null);
						Component[i].setCard(Hand.get(i));
						if(this.Component[i].getCard() != null)
							this.Component[i].setVisible(true);
						else
							this.Component[i].setVisible(false);
					}
					catch(Exception e)
					{}
				}
			}
			else
			{
				for(int i=0;i<10;i++)
				{
					if(i < this.haveCard)
					{
						this.Component[i].setVisible(true);
						this.Component[i].setDrawable(true);
					}
					else
					{
						this.Component[i].setVisible(false);
						this.Component[i].setDrawable(false);
					}
				}
			}
			change = false;
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
	public boolean isThey()
	{
		return they;
	}
	public void setThey(boolean they)
	{
		this.they = they;
		for(int i=0;i<10;i++)
		{
			this.Component[i].setThey(this.they);
		}
	}
	public int getHaveCard()
	{
		return haveCard;
	}
	public void setHaveCard(int haveCard)
	{
		this.haveCard = haveCard;
	}
	
	public boolean clickComponent()
	{
		for(int i=0;i<10;i++)
		{
			if(this.Component[i].isPress())
			{
				return true;
			}
		}
		return false;
	}
	
}
