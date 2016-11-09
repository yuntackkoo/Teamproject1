package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataload.LoadData;
import net.miginfocom.swing.MigLayout;

public class DeckEditPage extends JPanel
{
	private File Dlist = new File("Deck");
	private File curDeck = null;
	private String[] CardNameList; 
	private Scanner sc;
	private LoadData data = LoadData.getInstance();
	private JList list_1;
	private CardImage[] cardimage = new CardImage[8];
	private JPanel me = this;
	/**
	 * Create the panel.
	 */
	public DeckEditPage()
	{
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[::20][60][60][60][40][60][60][60][40][60,grow][60][60][40][60][60][60][50][grow]", "[::20][grow][grow][grow][::30,grow][grow][grow][grow][80][50][40]"));
		
		for(int i=0;i<8;i++)
		{
			cardimage[i] = new CardImage(i+1);
		}
		
		add(cardimage[0], "cell 1 1 3 3,grow");
		add(cardimage[1], "cell 5 1 3 3,grow");
		add(cardimage[2], "cell 9 1 3 3,grow");
		add(cardimage[3], "cell 13 1 3 3,grow");
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		add(panel_7, "cell 17 1 1 10,grow");
		panel_7.setLayout(new MigLayout("", "[288px,grow]", "[50][grow][grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_7.add(scrollPane, "cell 0 0,grow");
		
		JList list = new JList(Dlist.listFiles());
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) 
			{
				curDeck = (File)list.getSelectedValue();
				try
				{
					Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(curDeck)));
					sc.useDelimiter("#");
					CardNameList = new String[sc.nextInt()];
					for(int i=0;i<CardNameList.length;i++)
					{
						CardNameList[i] = data.getCardName(sc.nextInt()-1);
					}
					list_1.setListData((Object[]) CardNameList);
				} catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_7.add(scrollPane_1, "cell 0 1,grow");
		
		list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		
		add(cardimage[4], "cell 1 5 3 3,grow");
		add(cardimage[5], "cell 5 5 3 3,grow");
		add(cardimage[6], "cell 9 5 3 3,grow");
		add(cardimage[7], "cell 13 5 3 3,grow");
		
		JButton btnNewButton = new JButton("뒤로");
		add(btnNewButton, "cell 5 9 2 1,grow");
		btnNewButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0;i<8;i++)
				{
					cardimage[i].setPage(-1);
				}
				me.repaint();
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("앞으로");
		add(btnNewButton_1, "cell 9 9 2 1,grow");
		btnNewButton_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0;i<8;i++)
				{
					cardimage[i].setPage(1);
				}
				me.repaint();
			}
		});
		
	}
}

