package main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import dataload.LoadData;
import java.awt.Color;

public class CardImage extends JPanel
{
	private LoadData data = LoadData.getInstance();
	private int CardNumber;
	/**
	 * Create the panel.
	 */
	public CardImage(int CardNumber)
	{
		setBackground(Color.RED);
		this.CardNumber = CardNumber-1;
	}

	@Override
	protected void printComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(data.getImage(5), 0, 0, 300, 300, this);
	}

	public void setCardNumber(int cardNumber)
	{
		this.CardNumber = cardNumber;
	}
}
