package player;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import Socket.ClientSocket;
import Socket.Massage;
import card.CardForm;
import card.Pawn;
import dataload.LoadData;
import main.CardImage;

public class CardViewer extends JButton
{
	private CardForm card;
	private boolean they;
	private int handle;
	private LoadData data = LoadData.getInstance();
	private boolean drawable;
	
	public CardViewer(boolean they)
	{
		this.they = they;
		this.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(card.getLoc());
				if(!they && (card.getLoc()==CardForm.Hand))
				{
					Massage m = Massage.getMassage(Massage.Summon);
					m.setHandle(handle);
					System.out.println(handle);
					System.out.println(getLocation());
					ClientSocket.sendMassage(m);
				}
			}
		});
	}
	
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
		if(!they)
		{
			if(this.card != null)
			{
				BufferedImage img;
				super.paintComponent(g);
				img = CardImage.get((Pawn) card);
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
				this.getHandle();
			}
			else
			{
				this.setVisible(false);
			}
		}
		else
		{
			if(this.drawable)
			{
				g.drawImage(data.getBackImage(), 0, 0, this.getWidth(), this.getHeight(), null);
			}
			else
			{
				this.setVisible(false);
			}
		}
	}
	
	public void getHandle()
	{
		for(int i=0;i<10;i++)
		{
			if(this.getLocation().getX() <= (i*this.getWidth()))
			{
				this.handle = i;
				break;
			}
		}
	}

	public boolean isDrawable()
	{
		return drawable;
	}

	public void setDrawable(boolean drawable)
	{
		this.drawable = drawable;
	}
	
}
