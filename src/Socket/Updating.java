package Socket;

import java.util.List;
import java.util.Map;

import card.CardForm;
import card.Pawn;

public interface Updating
{
	public static int MyField = 1;
	public static int TheyField = 2;
	
	abstract public Map<Integer,List> getField();
	abstract public void setField(int own,List<Pawn> Field);
	
	public static int MyGrave = 1;
	public static int TheyGrave = 2;
	
	abstract public Map<Integer,List> getGrave();
	abstract public void setGrave(int own,List<CardForm> Grave);

	public static int MyDeck = 0;
	public static int TheyDeck = 1;
	
	abstract public int getDeck(int own);
	abstract public void setDeck(int own,int value);
	
	abstract public List<CardForm> getMeHand();
	abstract public void setMeHand(List<CardForm> Hand);
	
	abstract public int getTheyHand();
	abstract public void setTheyHand(int Hand);
}
