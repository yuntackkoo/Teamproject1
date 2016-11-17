package player;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import card.CardForm;

public class Grave extends JPanel implements CardTrans
{
	private List<CardForm> Grave = null;
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
	
	public Grave()
	{
		this.setBackground(Color.blue);
	}
	@Override
	protected void printComponent(Graphics g)
	{
	}
	
	public void setGrave(List<CardForm> grave)
	{
		this.Grave = grave;
	}
	
	public List<CardForm> getGrave()
	{
		return this.Grave;
	}
}
