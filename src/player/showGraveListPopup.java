package player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class showGraveListPopup extends JDialog
{
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main()
	{
		try
		{
			showGraveListPopup dialog = new showGraveListPopup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public showGraveListPopup()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				panel = new JPanel();
				scrollPane.setViewportView(panel);
				panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			}
		}
	}
	
	public void setCard(List<CardViewer> list)
	{
		for(int i=0;i<list.size();i++)
		{
			this.panel.add(list.get(i));
		}
	}

}
