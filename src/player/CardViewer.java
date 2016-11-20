package player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import card.CardForm;
import card.Pawn;
import main.CardImage;

public class CardViewer extends JButton
{
	CardForm card;

	public CardForm getCard()
	{
		return card;
	}
	
	public void setCard(CardForm card)
	{
		this.card = card;
		this.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if(this.card != null)
		{
			BufferedImage img;
			super.paintComponent(g);
			img = CardImage.get((Pawn) card);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		else
		{
			this.setVisible(false);
		}
	}
	
	
}
