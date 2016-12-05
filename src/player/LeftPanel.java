package player;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class LeftPanel extends JPanel
{
	private JLabel MyLife;
	private JLabel TheyLife;
	private CardPlainPanel cardPlainPanel;
	private LifeDrawPanel MyDrawLife;
	private LifeDrawPanel TheyDrawLife;
	private JLabel MyMana;
	private JLabel TheyMana;

	/**
	 * Create the panel.
	 */
	public LeftPanel(int MaxLife)
	{
		this.setSize(200, 720);
		setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[30][20][20][grow]"));
		
		MyDrawLife = new LifeDrawPanel();
		add(MyDrawLife, "cell 0 0 2 1,grow");
		
		MyDrawLife.setMaxLife(MaxLife);
		
		TheyDrawLife = new LifeDrawPanel();
		add(TheyDrawLife,"cell 2 0 2 1,grow");
		TheyDrawLife.setMaxLife(MaxLife);
		
		MyLife = new JLabel("New label");
		add(MyLife, "cell 0 1 2 1");
		
		TheyLife = new JLabel("New label");
		add(TheyLife, "cell 2 1 2 1");
		
		
		MyMana = new JLabel();
		add(MyMana,"cell 0 2 2 1");
		
		TheyMana = new JLabel();
		add(TheyMana,"cell 2 2 2 1");
		
		
		cardPlainPanel = new CardPlainPanel();
		add(cardPlainPanel, "cell 0 3 4 1,grow");
	}
	
	public void setMyLife(int Life)
	{
		MyLife.setText(Integer.toString(Life));
		this.MyDrawLife.setLife(Life);
	}
	public void setTheyLife(int Life)
	{
		TheyLife.setText(Integer.toString(Life));
		this.TheyDrawLife.setLife(Life);
	}
	public void setMyMana(int mana)
	{
		this.MyMana.setText(Integer.toString(mana));
	}
	public void setTheyMana(int mana)
	{
		this.TheyMana.setText(Integer.toString(mana));
	}

	public CardPlainPanel getCardPlainPanel()
	{
		return cardPlainPanel;
	}
	
	public void reDraw()
	{
		this.MyDrawLife.repaint();
		this.TheyDrawLife.repaint();
	}
}
