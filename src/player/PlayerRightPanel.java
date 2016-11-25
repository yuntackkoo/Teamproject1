package player;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

import Socket.ClientSocket;
import Socket.Massage;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class PlayerRightPanel extends JPanel
{
	private JTextField input;
	private JTextArea output;
	private boolean turn = true;

	/**
	 * Create the panel.
	 */
	public PlayerRightPanel()
	{
		this.setSize(200, 720);
		setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[grow][grow][grow][20][]"));
		
		JButton btnNewButton = new JButton("\uD134 \uC885\uB8CC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(turn)
				{
					ClientSocket.sendMassage(Massage.getMassage(Massage.TurnEnd));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "자신의 턴이 아닙니다.");
				}
			}
		});
		add(btnNewButton, "cell 0 1 4 1,growx");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2 4 2,grow");
		
		output = new JTextArea();
		scrollPane.setViewportView(output);
		output.setColumns(10);
		
		input = new JTextField();
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Massage m = Massage.getMassage(Massage.Chat);
				m.setChat(input.getText());
				input.setText("");
				ClientSocket.sendMassage(m);
			}
		});
		add(input, "cell 0 4 4 1,grow");

	}

	public JTextArea getOutput()
	{
		return output;
	}

	public void setTurn(boolean turn)
	{
		this.turn = turn;
	}
	
}
