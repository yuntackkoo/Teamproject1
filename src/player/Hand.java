package player;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class Hand extends JPanel implements CardTrans
{
	private List<CardForm> Hand = new LinkedList<>();
	private boolean[] hand_target = new boolean[10];
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
}
