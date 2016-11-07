package main;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DeckEdit extends JPanel
{
	JList<File> list = new JList<>();
	File Dlist = new File("Deck");
	JScrollPane scroll = new JScrollPane(list);
	public DeckEdit()
	{
		this.setPreferredSize(new Dimension(1280, 720));
		this.setVisible(false);
		this.setLayout(null);
		list.setListData(Dlist.listFiles());
		this.add(scroll);
		scroll.setBounds(850, 200, 100, 100);
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2;
		g2 = (Graphics2D) g;
		Image Bg;
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6F);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Bg = tool.getImage("DeckEditBg.jpg");
		g2.setComposite(alpha);
		g2.drawImage(Bg,0 , 0, 1280, 720, this);
	}
	
}