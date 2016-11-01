package player;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class Field extends JPanel implements CardTrans
{
	private boolean[] filed_target = new boolean[5];
	private List<CardForm> Filed = new LinkedList<>();
	public List<CardForm> getFiled()
	{
		return Filed;
	}
	public void setFiled(List<CardForm> filed)
	{
		this.Filed = filed;
	}
	@Override
	public void addCard(CardForm Card) 
	{
		Filed.add(Card);
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Filed.remove(i);
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}

