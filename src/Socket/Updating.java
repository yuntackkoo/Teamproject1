package Socket;

import java.util.List;
import java.util.Map;

import card.CardForm;
import card.Pawn;

public interface Updating
{
	public static int MyField = 0;
	public static int TheyField = 1;
	
	abstract public List<Pawn> getField(int own);
	abstract public void setField(int own,List<Pawn> Field);
	
	public static int MyGrave = 0;
	public static int TheyGrave = 1;
	
	abstract public List<CardForm> getGrave(int own);
	abstract public void setGrave(int own,List<CardForm> Grave);

	public static int MyDeck = 0;
	public static int TheyDeck = 1;
	
	abstract public Integer getDeck(int own);
	abstract public void setDeck(int own,int value);
	
	abstract public List<CardForm> getMeHand();
	abstract public void setMeHand(List<CardForm> Hand);
	
	abstract public Integer getTheyHand();
	abstract public void setTheyHand(Integer Hand);
}
