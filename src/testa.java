import javax.swing.JPanel;

import card.CardForm;
import player.ClientPlayer;
import player.They;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;

public class testa extends JPanel
{
	ClientPlayer me = new ClientPlayer()
	{
		
		@Override
		public void showUI()
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void showGraveList()
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void showCardEffect(CardForm c)
		{
			// TODO Auto-generated method stub
			
		}
	};
	/**
	 * Create the panel.
	 */
	public testa()
	{
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[200][grow][200]", "[grow][grow]"));
		
		
		They they = new They();
		add(they, "cell 1 0,grow");
		they.setVisible(true);
		add(me,"cell 1 1,grow");
		me.setVisible(true);
	}

}
