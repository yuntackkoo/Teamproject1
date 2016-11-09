package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	/**
	 * Create the panel.
	 */
	public DeckEditPage()
	{
		this.setSize(1280, 720);
		setLayout(new MigLayout("", "[::20][60,grow][60][60][::40,grow][60,grow][60][60][40][60,grow][60][60][40][60,grow][60][60][50][grow]", "[::20,grow][grow][grow][grow][::30,grow][grow][grow][grow][80][50][40]"));
		
		CardImage cardImage_1 = new CardImage(2);
		add(cardImage_1, "cell 1 1 3 3,grow");
		
		CardImage cardImage_2 = new CardImage(3);
		add(cardImage_2, "cell 5 1 3 3,grow");
		
		CardImage cardImage_4 = new CardImage(5);
		add(cardImage_4, "cell 9 1 3 3,grow");
		
		CardImage cardImage_6 = new CardImage(7);
		add(cardImage_6, "cell 13 1 3 3,grow");
		
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
		
		CardImage cardImage = new CardImage(1);
		add(cardImage, "cell 1 5 3 3,grow");
		
		CardImage cardImage_3 = new CardImage(3);
		add(cardImage_3, "cell 5 5 3 3,grow");
		
		CardImage cardImage_5 = new CardImage(5);
		add(cardImage_5, "cell 9 5 3 3,grow");
		
		CardImage cardImage_7 = new CardImage(7);
		add(cardImage_7, "cell 13 5 3 3,grow");
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton, "cell 5 9 2 1,grow");
		
		JButton btnNewButton_1 = new JButton("New button");
		add(btnNewButton_1, "cell 9 9 2 1,grow");
		
	}
}

