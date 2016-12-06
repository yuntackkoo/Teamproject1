package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import card.Magic;
import card.Pawn;
import dataload.LoadData;
import net.miginfocom.swing.MigLayout;

public class CardPlainPanel extends JPanel
{
	private JLabel Name;
	private JLabel Attack;
	private JLabel Number;
	private JLabel Life;
	private JTextArea Plain;
	private LoadData data = LoadData.getInstance();
	private Panel panel;

	/**
	 * Create the panel.
	 */
	public CardPlainPanel()
	{
		setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][grow][grow][40][40][40][40][grow]"));
		
		panel = new Panel();
		add(panel,"cell 0 0 3 3,grow");
		
		Name = new JLabel("New label");
		add(Name, "cell 0 3 3 1");
		
		Number = new JLabel("New label");
		add(Number, "cell 0 4 3 1");
		
		Attack = new JLabel("New label");
		add(Attack, "cell 0 5 3 1");
		
		Life = new JLabel("New label");
		add(Life, "cell 0 6 3 1");
		
		Plain = new JTextArea("New label");
		add(Plain, "cell 0 7 3 1");
		Plain.setBackground(new Color(255,0,0,0));
		Plain.setFocusable(false);
		Plain.setLineWrap(true);
		
	}
	
	public void setCard(Magic Card)
	{
		this.Name.setText("카드 이름 : "+data.getCardName(Card.getCardNumber()));
		this.Plain.setText(data.getCardToolTip(Card.getCardNumber()));
		this.Number.setText("카드 번호 : " + Integer.toString(Card.getCardNumber()));
		panel.setCardnumber(Card.getCardNumber());
		this.repaint();
	}
	
	public void setCard(Pawn Card)
	{
		this.Name.setText("카드 이름 : "+data.getCardName(Card.getCardNumber()));
		this.Plain.setText(data.getCardToolTip(Card.getCardNumber()));
		this.Number.setText("카드 번호 : " + Integer.toString(Card.getCardNumber()));
		this.Attack.setText("카드 공격력 : " + Integer.toString(Card.getCurrentatt()));
		this.Life.setText("카드 체력 : " + Integer.toString(Card.getCurrentlife()));
		panel.setCardnumber(Card.getCardNumber());
		this.repaint();
	}
}

class Panel extends JPanel
{
	private int cardnumber;
	Image img;
	LoadData data = LoadData.getInstance();
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),null);
	}
	public void setCardnumber(int cardnumber)
	{
		this.cardnumber = cardnumber;
		img = data.getImage(this.cardnumber);
	}
	
}