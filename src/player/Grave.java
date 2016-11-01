package player;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class Grave extends JPanel implements CardTrans
{
	private List<CardForm> Grave = new ArrayList<>();
	private boolean[] grave_target;
	@Override
	public void addCard(CardForm Card)
	{
		Grave.add(Card);
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Grave.remove(i);
	}
	
}
