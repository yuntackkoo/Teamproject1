package player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import card.CardForm;
import card.Pawn;

public class Field extends JPanel implements CardTrans
{
	private boolean[] filed_target = new boolean[5];
	private List<Pawn> Filed = new ArrayList<>();
	private JButton[] FieldComponent = new JButton[5];
	private boolean change;
	
	
	public List<Pawn> getFiled()
	{
		return Filed;
	}
	public void setFiled(List<Pawn> filed)
	{
		this.Filed = new LinkedList<>();
		for(int i=0;i<filed.size();i++)
		{
			this.Filed.add((Pawn) filed.get(i).copy());
		}
	}
	@Override
	public void addCard(CardForm Card) 
	{
		Card.setLoc(CardForm.Field);
		Filed.add((Pawn)Card);
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Filed.remove(i);
	}
	public Field()
	{
		this.setLayout(new GridLayout());
		for(int i =0;i<5;i++)
		{
			this.add(FieldComponent[i] = new JButton());
		}
	}
	@Override
	public void update(Graphics g)
	{
		
	}
	public void update()
	{
		for(int i =0;i<5;i++)
		{
			try
			{
				this.FieldComponent[i].setText(Integer.toString(this.Filed.get(i).getCurrentlife()));
			}
			catch (Exception e)
			{
				this.FieldComponent[i].setVisible(false);
			}
		}
	}

	public boolean isChange()
	{
		return change;
	}
	public void setChange(boolean change)
	{
		this.change = change;
	}
}

