package player;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private JButton[] HandComponent = new JButton[10];
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
			this.add(HandComponent[i] = new JButton());
		}
	}
	
	public void update()
	{
		for(int i =0;i<10;i++)
		{
			try
			{
				this.HandComponent[i].setText(Integer.toString(this.Hand.get(i).getCardNumber()));
			}
			catch (Exception e)
			{
				this.HandComponent[i].setVisible(false);
			}
		}
	}
}
