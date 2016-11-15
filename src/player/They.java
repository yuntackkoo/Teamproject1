package player;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.plaf.PanelUI;

import card.CardForm;

public class They extends ClientPlayer
{
	public They()
	{
	}

	@Override
	public void showUI() 
	{
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(-Math.PI, this.getLocationX()+this.getWidth()/2.0F, this.getLocationY()+this.getHeight()/2.0F);
	}

	@Override
	public void showCardEffect(CardForm c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showGraveList() {
		// TODO Auto-generated method stub
		
	}
	
}