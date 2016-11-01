package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import card.CardForm;

public class They extends ClientPlayer
{
	public They()
	{
		this.setLayout(null);
		super.setBounds(350, 360, 930, 360);
		super.getHandlist().setBounds(0, 260, 600, 100);
		super.getHandlist().setBackground(Color.black);
		super.getDecklist().setBounds(600, 210, 300, 150);
		super.getDecklist().setBackground(Color.yellow);
		super.getGravelist().setBounds(600, 100, 100, 100);
		super.getGravelist().setBackground(Color.red);
		super.getFieldlist().setBounds(0, 0, 600, 260);
		super.getFieldlist().setBackground(Color.blue);
	}

	@Override
	public void showUI() 
	{
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(Math.toRadians(180), 350F, 360F);
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