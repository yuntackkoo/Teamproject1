package player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class PlayerBase extends JPanel
{
	private int deck;
	private int hand;
	private int grave;
	private Deck decklist = new Deck();
	private Hand handlist = new Hand();
	private Grave gravelist = new Grave();
	private Field fieldlist = new Field();
	
	public Deck getDecklist() {
		return decklist;
	}
	public Hand getHandlist() {
		return handlist;
	}
	public Grave getGravelist() {
		return gravelist;
	}
	public Field getFieldlist() {
		return fieldlist;
	}
	public int getDeck() {
		return deck;
	}
	public void setDeck(int deck) {
		this.deck = deck;
	}
	public int getHand() {
		return hand;
	}
	public void setHand(int hand) {
		this.hand = hand;
	}
	public int getGrave() {
		return grave;
	}
	public void setGrave(int grave) {
		this.grave = grave;
	}
	
	public PlayerBase()
	{
		this.setLayout(null);
		this.add(decklist);
		this.add(fieldlist);
		this.add(handlist);
		this.add(gravelist);
		this.setVisible(false);
	}
}

abstract class ClientPlayer extends PlayerBase
{
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
	public abstract void showUI();
	public abstract void showCardEffect(CardForm c);
	public abstract void showGraveList();
}



interface CardTrans
{
	abstract public void addCard(CardForm Card);
	abstract public CardForm disCard(int i);
}


