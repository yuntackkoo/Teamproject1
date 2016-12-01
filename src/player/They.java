package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.plaf.PanelUI;

import card.CardForm;
import net.miginfocom.swing.MigLayout;

public class They extends PlayerBase
{
	public They()
	{
		this.setSize(880, 360);
		this.setLayout(new MigLayout("", "[50][50][grow][grow][grow][grow][grow][grow][50][50]", "[grow][30][grow]"));
		Grave grave = super.getGravelist();
		grave.setBackground(Color.WHITE);
		this.add(grave, "cell 0 2 2 1,grow");
		player.Field field = super.getFieldlist();
		this.add(field, "cell 2 2 6 1,grow");
		Hand hand = super.getHandlist();
		this.add(hand, "cell 2 0 6 1,grow");
		Deck deck = super.getDecklist();
		deck.setBackground(Color.WHITE);
		this.add(deck, "cell 8 0 2 1,grow");
		
		hand.setThey(true);
		field.setThey(true);
		
	}
	public void showCardEffect(CardForm c)
	{}
	public void showGraveList()
	{}
	
	public void update()
	{
		
	}
}