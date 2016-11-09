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
	private int changepage=0;
	/**
	 * Create the panel.
	 */
	public CardImage(int CardNumber)
	{
		setBackground(Color.RED);
		this.CardNumber = CardNumber-1;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(data.getImage(CardNumber+(this.changepage*8)), 0, 0, 300, 300, this);
	}

	public void setPage(int cardNumber)
	{
		if(cardNumber>0)
		{
			Math.min(++this.changepage,(int)data.getMax()/8);
		}
		else
		{
			Math.max(--this.changepage, 0);
		}
	}
}
