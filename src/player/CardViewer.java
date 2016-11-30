package player;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	private int Location;
	private boolean press = false;
	
	
	public CardViewer(int loc)
	{
		this.Location = loc;
		this.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!they)
				{
					if(card.getLoc()==CardForm.Hand)
					{
						Massage m = Massage.getMassage(Massage.Summon);
						m.setHandle(handle);
						ClientSocket.sendMassage(m);
					}
				}
			}
		});
		this.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				int target;
				if(e.getY()<0 && e.getY() >-250 && (e.getX()+getX()) >=0 && (e.getX()+getX())<=660)
				{
					int tmp = e.getX() + getX();
					if(tmp <= 132)
						target = 0;
					else if(tmp <= 132*2)
						target = 1;
					else if(tmp <= 132*3)
						target = 2;
					else if(tmp <= 132*4)
						target = 3;
					else
						target = 4;
				}
				else
				{
					target = -1;
				}
				if(!they && target > -1)
				{
					if(card.getLoc() == CardForm.Field)
					{
						Massage m = Massage.getMassage(Massage.Attack);
						m.setMyFieldCard(handle);
						m.setAttackTarget(target);
						ClientSocket.sendMassage(m);
					}
				}
				press = false;
			}
			@Override
			public void mousePressed(MouseEvent e)
			{
				press = true;
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{}
		});
	}
	
	public CardForm getCard()
	{
		return card;
	}
	
	public void setCard(CardForm card)
	{
		this.card = card;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		switch(this.Location)
		{
			case 1://덱일때
			{
				break;
			}
			case 2://필드일때
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
				break;
			}
			case 3://묘지일때
			{
				break;
			}
			case 4://핸드일때
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
				}
				break;
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

	public boolean isThey()
	{
		return they;
	}

	public void setThey(boolean they)
	{
		this.they = they;
	}

	public boolean isPress()
	{
		return press;
	}
	
	
}
