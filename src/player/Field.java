package player;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;
import card.Pawn;

public class Field extends JPanel implements CardTrans
{
	private boolean[] filed_target = new boolean[5];
	private List<Pawn> Filed = new ArrayList<>();
	private CardViewer[] Component = new CardViewer[5];
	private boolean change;
	private boolean they;
	
	
	public List<Pawn> getFiled()
	{
		return Filed;
	}
	public void setFiled(List<Pawn> filed)
	{
		this.Filed = filed;
	}
	@Override
	public void addCard(CardForm Card) 
	{
		Card.setLoc(CardForm.Field);
		Filed.add((Pawn)Card.copy());
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
			this.add(Component[i] = new CardViewer(CardForm.Field));
		}
		
		this.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
			}
			
		});
		
	}
	public void update()
	{
		if(change)
		{
			for(int i=0;i<5;i++)
			{
				Component[i].setCard(null);
			}
			for(int i=0;i<Filed.size();i++)
			{
				Component[i].setCard(Filed.get(i));
			}
			for(int i=0;i<5;i++)
			{
				if(this.Component[i].getCard()!= null)
					this.Component[i].setVisible(true);
				else
					this.Component[i].setVisible(false);
			}
			this.change = false;
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
	public boolean isThey()
	{
		return they;
	}
	public void setThey(boolean they)
	{
		this.they = they;
		for(int i=0;i<5;i++)
		{
			this.Component[i].setThey(this.they);
		}
	}
	
	public CardForm focusCheck()
	{
		for(int i=0;i<Component.length;i++)
		{
			if(Component[i].isFocusOwner())
			{
				return Component[i].getCard();
			}
		}
		return null;
	}
	
	public void setatt()
	{
		for(int i=0;i<this.Component.length;i++)
		{
			this.Component[i].setAttckcount(1);
		}
	}
	
	public void setturn(boolean turn)
	{
		for(int i=0;i<Component.length;i++)
		{
			this.Component[i].setTurn(turn);
		}
	}
}

