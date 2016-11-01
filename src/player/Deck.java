package player;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class Deck extends JPanel implements CardTrans
{
	private List<CardForm> Deck = new ArrayList<>();
	private boolean decktarget;
	@Override
	public void addCard(CardForm Card) 
	{
		Deck.add(Card);
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Deck.remove(i);
	}
	
}