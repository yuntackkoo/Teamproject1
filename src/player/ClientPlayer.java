package player;

import java.lang.reflect.Field;

import card.CardForm;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;

public class ClientPlayer extends PlayerBase
{
	public ClientPlayer()
	{
		this.setSize(880, 360);
		this.setLayout(new MigLayout("", "[50][50][grow][grow][grow][grow][grow][grow][50][50]", "[grow][30][grow]"));
		Grave grave = super.getGravelist();
		grave.setBackground(Color.WHITE);
		this.add(grave, "cell 0 0 2 1,grow");
		player.Field field = super.getFieldlist();
		this.add(field, "cell 2 0 6 1,grow");
		Hand hand = super.getHandlist();
		this.add(hand, "cell 2 2 6 1,grow");
		Deck deck = super.getDecklist();
		deck.setBackground(Color.WHITE);
		this.add(deck, "cell 8 2 2 1,grow");
		
		hand.setThey(false);
		field.setThey(false);
	}
	
	private int LocationX;
	private int LocationY;
	
	public int getLocationX() {
		return LocationX;
	}
	public void setLocationX(int locationX) {
		LocationX = locationX;
	}
	public int getLocationY() {
		return LocationY;
	}
	public void setLocationY(int locationY) {
		LocationY = locationY;
	}
	public void showCardEffect(CardForm c)
	{}
	public void showGraveList()
	{}
}

interface CardTrans
{
	abstract public void addCard(CardForm Card);
	abstract public CardForm disCard(int i);
}

