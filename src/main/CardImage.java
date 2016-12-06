package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import card.Magic;
import card.Pawn;
import dataload.LoadData;

public class CardImage extends JPanel
{
	private LoadData data = LoadData.getInstance();
	private int CardNumber;
	private int changepage = 0;
	private boolean isExit;
	private int CurPage;
	private int MaxDeck = 40;

	/**
	 * Create the panel.
	 * 
	 * @param cardValueList
	 * @param cardNumberList
	 */
	public CardImage(int CardNumber, Map<Integer, Integer> CardValueList, Map<Integer, String> CardNameList,
			DeckEditPage me)
	{
		this.CardNumber = CardNumber;
		this.CurPage = this.CardNumber;
		this.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{}
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (isExit)
				{
					if (me.getCardValueSum() < MaxDeck)
					{
						if (CardValueList.containsKey(CurPage))
						{
							if (CardValueList.get(CurPage) < 3)
							{
								CardValueList.put(CurPage, CardValueList.get(CurPage) + 1);
								CardNameList.put(CurPage, data.getCardName(CurPage) + "      X  "
										+ Integer.toString(CardValueList.get(CurPage)));
								me.setCardValueSum();
							} 
							else
								JOptionPane.showMessageDialog(null, "3장이하만 가능 합니다");
						} 
						else
						{
							CardValueList.put(CurPage, 1);
							CardNameList.put(CurPage, data.getCardName(CurPage) + "      X  "
									+ Integer.toString(CardValueList.get(CurPage)));
							me.setCardValueSum();
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, Integer.toString(MaxDeck) + "장이하만 가능합니다");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "카드가 없습니다.");
				}
				me.UpdateCardList();
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

	@Override
	protected void paintComponent(Graphics g)
	{
		isExit = true;
		Graphics2D g2 = (Graphics2D) g;
		try
		{
			g2.drawImage(data.getImage(CurPage), 0, 0, this.getWidth(), this.getHeight(), this);
			g2.setColor(Color.red);
			g2.drawString(Integer.toString(this.CurPage), 280, 280);
			this.setToolTipText(data.getCardToolTip(CurPage));
		} catch (ArrayIndexOutOfBoundsException e)
		{
			Toolkit tool = Toolkit.getDefaultToolkit();
			Image image = tool.getImage("NoCard.jpg");
			g2.drawImage(image, 0, 0, 300, 300, this);
			isExit = false;
		}
	}
	
	public static <T extends Pawn> BufferedImage get (T card)
	{
		LoadData data = LoadData.getInstance();
		Pawn cardtmp = (Pawn) card;
		BufferedImage img = new BufferedImage(300,300,data.getImage(card.getCardNumber()).getType()); 
		Graphics2D g2 = img.createGraphics();
		g2.drawImage(data.getImage(card.getCardNumber()), 0, 0, 300, 300, null);
		g2.drawImage(data.getTemPawnImage(), 0, 0,300,300,null);
//		g2.drawArc(0, 0, 50, 50, 0, 360);
//		g2.drawArc(0, 250, 50, 50, 0, 360);
//		g2.drawArc(250, 250, 50, 50, 0, 360);
		g2.setFont(new Font("바탕",Font.PLAIN,30));
		if(card.getCost() < card.getCurrentCost())
		{
			g2.setColor(Color.red);
		}
		else
		{
			g2.setColor(Color.white);
		}
		g2.drawString(Integer.toString(card.getCost()), 24, 30);
		
		if(card.getNativeatt() > card.getCurrentatt())
		{
			g2.setColor(Color.red);
		}
		else
		{
			g2.setColor(Color.white);
		}
		g2.drawString(Integer.toString(cardtmp.getCurrentatt()), 10, 275);
		
		if(card.getNativelife() > card.getCurrentlife())
		{
			g2.setColor(Color.red);
		}
		else
		{
			g2.setColor(Color.white);
		}
		g2.drawString(Integer.toString(cardtmp.getCurrentlife()), 250, 275);
		g2.dispose();
		return img;
	}
	
	public static <T extends Magic> BufferedImage get (T card)
	{
		LoadData data = LoadData.getInstance();
		Magic cardtmp = (Magic) card;
		BufferedImage img = new BufferedImage(300,300,data.getImage(card.getCardNumber()).getType()); 
		Graphics2D g2 = img.createGraphics();
		g2.drawImage(data.getImage(card.getCardNumber()), 0, 0, 300, 300, null);
		g2.drawImage(data.getTemMasicImage(), 0, 0,300,300,null);
//		g2.drawArc(0, 0, 50, 50, 0, 360);
//		g2.drawArc(0, 250, 50, 50, 0, 360);
//		g2.drawArc(250, 250, 50, 50, 0, 360);
		g2.setFont(new Font("바탕",Font.PLAIN,30));
		if(card.getCost() < card.getCurrentCost())
		{
			g2.setColor(Color.red);
		}
		else
		{
			g2.setColor(Color.white);
		}
		g2.drawString(Integer.toString(card.getCost()), 20, 30);
		
		
		g2.dispose();
		return img;
	}
	
	public void setPage(int cardNumber)
	{
		if (cardNumber > 0)
		{
			this.changepage = Math.min(++this.changepage, (int) data.getMax() / 8);
		} else
		{
			this.changepage = Math.max(--this.changepage, 0);
		}
		this.CurPage = changepage * 8 + this.CardNumber;
	}
	
	
	
}
