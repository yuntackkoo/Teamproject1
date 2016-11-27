package player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class Deck extends JPanel implements CardTrans
{
	private transient List<CardForm> Deck = new ArrayList<>();
	private boolean decktarget;
	private boolean change;
	private int haveCard;
	@Override
	public void addCard(CardForm Card)
	{
		Card.setLoc(CardForm.Deck);
		Deck.add(Card.copy());
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Deck.remove(i);
	}
	
	public Deck()
	{
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		
		this.setBackground(Color.black);
		super.paintComponent(g);
	}
	public int getDeckSize()
	{
		return Deck.size();
	}

	public boolean isChange()
	{
		return change;
	}
	public void setChange(boolean change)
	{
		this.change = change;
	}
	public List<CardForm> getDeck()
	{
		return Deck;
	}
	public void setDeck(List<CardForm> deck)
	{
		Deck = deck;
	}
	public int getHaveCard()
	{
		return haveCard;
	}
	public void setHaveCard(int haveCard)
	{
		this.haveCard = haveCard;
	}
	
	
}