package player;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.CardForm;

public class Grave extends JPanel implements CardTrans
{
	private List<CardForm> Grave = new ArrayList<>();
	private List<CardViewer> Component = new ArrayList<>();
	private boolean[] grave_target;
	private boolean change;
	private showGraveListPopup popup = new showGraveListPopup();
	
	@Override
	public void addCard(CardForm Card)
	{
		Card.setLoc(CardForm.Grave);
		Grave.add(Card.copy());
	}
	@Override
	public CardForm disCard(int i) 
	{
		return Grave.remove(i);
	}
	
	public Grave()
	{
		this.setBackground(Color.blue);
		this.setIgnoreRepaint(true);
		this.setLayout(new CardLayout());
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				//popup.main();
			}
		});
	}
	
	public void setGrave(List<CardForm> grave)
	{
		this.Grave = grave;
		int size = Grave.size() - Component.size();
		for(int i = 0;i<size;i++)
		{
			this.Component.add(new CardViewer(CardForm.Grave));
			Component.get(Component.size()-1).setCard(Grave.get(Grave.size()-1));
			this.add(Component.get(Component.size()-1));
		}
	}
	
	public List<CardForm> getGrave()
	{
		return this.Grave;
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
