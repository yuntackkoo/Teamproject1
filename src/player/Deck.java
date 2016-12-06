package player;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;
import dataload.LoadData;

public class Deck extends JPanel implements CardTrans
{
	private transient List<CardForm> Deck = new ArrayList<>();
	private boolean decktarget;
	private boolean change;
	private int haveCard;
	private LoadData data = LoadData.getInstance();
	private BufferedImage img = data.getDeckImage();
	
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
		this.setIgnoreRepaint(true);
		
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
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
		this.setToolTipText(Integer.toString(this.haveCard));
	}
	
	
}