import javax.swing.JPanel;

import card.CardForm;
import dataload.LoadData;
import player.CardViewer;
import player.ClientPlayer;
import player.They;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;

public class testa extends JPanel
{
	CardViewer card = new CardViewer();
	LoadData data = LoadData.getInstance();
	public testa()
	{
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[200][grow][200]", "[grow][grow]"));
		this.add(card, "cell 0 0,grow");
		card.setCard(data.getCard(2));
		card.repaint();
	}

}
