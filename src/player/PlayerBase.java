package player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class PlayerBase extends JPanel
{
	private Deck decklist = new Deck();
	private Hand handlist = new Hand();
	private Grave gravelist = new Grave();
	private Field fieldlist = new Field();
	private int Mana;
	private int Life;
	
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
	public int getMana()
	{
		return Mana;
	}
	public void setMana(int mana)
	{
		Mana = mana;
	}
	public PlayerBase()
	{
		this.setVisible(false);
	}
	public int getLife()
	{
		return Life;
	}
	public void setLife(int life)
	{
		Life = life;
	}
	public void showCardEffect(CardForm c)
	{
		// TODO Auto-generated method stub
		
	}
}