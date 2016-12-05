package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import Socket.ClientSocket;
import Socket.Massage;
import card.CardForm;
import card.Pawn;
import dataload.LoadData;
import main.CardImage;

public class CardViewer extends JButton
{
	private CardForm card = null;
	private boolean they;
	private int handle;
	private LoadData data = LoadData.getInstance();
	private boolean drawable;
	private int Location;
	private boolean press = false;
	private boolean update = true;
	private boolean useable;
	private boolean SpConditon;
	BufferedImage img;
	private static boolean OnTarget = false;
	
	
	
	public CardViewer(int loc)
	{
		this.Location = loc;
		
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
				getParent().getParent().getParent().getParent().dispatchEvent(e);
				System.out.println(e.getPoint());
			}
			@Override
			public void mousePressed(MouseEvent e)
			{
				getParent().getParent().getParent().getParent().dispatchEvent(e);
				if(e.getClickCount() == 2)
				{
					if(card.CardUse(handle))
					{
						Massage tmp = Massage.getMassage(Massage.UseEffect);
						tmp.setCardNumber(card.getPesnolnumber());
						tmp.setSpCondition(SpConditon);
						System.out.println("이펙트 발동!");
						ClientSocket.sendMassage(tmp);
					}
				}
				if(loc == CardForm.Grave)
					getParent().dispatchEvent(e);
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
		
		this.addMouseMotionListener(new MouseMotionListener()
		{
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
				getParent().getParent().getParent().getParent().dispatchEvent(e);
			}
		});
	}
	
	
	
	public CardForm getCard()
	{
		return card;
	}
	
	public void setCard(CardForm updatecard)
	{
		if(this.card != null)
		{
			if(updatecard == null)
			{
				this.card = updatecard;
			}
			else if(this.card.compareTo(updatecard) != 0)
			{
				this.card = updatecard;
				update = true;
			}
		}
		else
		{
			this.card = updatecard;
			update = true;
		}
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
					super.paintComponent(g);
					if(update)
					{
						img = CardImage.get((Pawn) card);
						update = false;
					}
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
				super.paintComponent(g);
				if(update)
				{
					img = data.getImage(card.getCardNumber());
					update = false;
				}
				g.drawImage(this.img, 0, 0, this.getWidth(), this.getHeight(), null);
				break;
			}
			case 4://핸드일때
			{
				if(!they)
				{
					if(this.card != null)
					{
						super.paintComponent(g);
						if(update)
						{
							img = CardImage.get((Pawn) card);
							update = false;
						}
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
						if(update)
						{
							img = data.getBackImage();
							update = false;
						}
						g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
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

	public boolean isUseable()
	{
		return useable;
	}

	public void setUseable(boolean useable)
	{
		this.useable = useable;
		if(this.useable)
		{
			this.setBorder(BorderFactory.createBevelBorder(0, Color.GREEN, Color.BLACK));
		}
	}

	public boolean isSpConditon()
	{
		return SpConditon;
	}

	public void setSpConditon(boolean spConditon)
	{
		this.SpConditon = spConditon;
		if(this.SpConditon)
			this.setBorder(BorderFactory.createBevelBorder(0,Color.yellow,Color.black));
	}
	
	
}
