import java.awt.Dimension;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import player.ClientPlayer;
import player.They;

public class testp extends JPanel
{
	
	/**
	 * Create the panel.
	 */
	public testp()
	{
		this.setPreferredSize(new Dimension(1280, 720));
		setLayout(new MigLayout("", "[grow][880][grow]", "[grow][grow]"));
		
		They they = new They();
		
		
		ClientPlayer clientPlayer = new ClientPlayer();
		add(they, "cell 1 0,grow");
		add(clientPlayer, "cell 1 1,grow");
		they.setVisible(true);
		clientPlayer.setVisible(true);
	}
}
