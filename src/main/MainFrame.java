package main;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame
{
	GameStart game = new GameStart();
	DeckEditPage edit = new DeckEditPage();
	MainMenu main = new MainMenu(game,edit);
	public MainFrame()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(game,BorderLayout.CENTER);
		game.setVisible(false);
		this.add(edit,BorderLayout.CENTER);
		edit.setVisible(false);
		this.add(main,BorderLayout.CENTER);
		main.setVisible(true);
		this.pack();
		FrameUpdate update = new FrameUpdate(this);
		update.fps = 60;
		update.start();
		System.out.println(this.getSize());
	}
}

class FrameUpdate extends Thread
{
	MainFrame main;
	int fps;
	public FrameUpdate(MainFrame main)
	{
		this.main = main;
	}
	
	public void run()
	{
		if(main.main.isGame())
		{
			main.game.getPlayer().getMe().getFieldlist().update();
			main.game.getPlayer().getMe().getHandlist().update();
		}
		try {
			this.sleep((int)1000/fps);
		} catch (InterruptedException e) {}
	}

	public void setFps(int fps) {
		this.fps = fps;
	}	
}


class MainMenu extends JPanel
{
	JButton start = new JButton("게임 시작");
	JButton deckedit = new JButton("덱 수정");
	MainMenu main = this;
	GameStart s;
	DeckEditPage edit;
	private boolean Game = false;
	int width = 400;
	int height = 50;
	int xlocation = (1280-width)/2;
	
	public MainMenu(GameStart s,DeckEditPage edit)
	{
		this.s = s;
		this.edit = edit;
		this.setPreferredSize(new Dimension(1280, 720));
		this.setLayout(null);
		this.add(start);
		start.setBounds(xlocation, 100, width, height);
		start.addActionListener(new gStart());
		this.add(deckedit);
		deckedit.setBounds(xlocation, 200, width, height);
		deckedit.addActionListener(new dEdit());
	}
	
	
	private class gStart implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			main.setVisible(false);
			s.setVisible(true);
		}
	}
	
	private class dEdit implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			main.setVisible(false);
			edit.setVisible(true);
			System.out.println(edit.getLocation());
			System.out.println(edit.isVisible());
			System.out.println(edit.isValid());
		}
	}

	public boolean isGame()
	{
		return Game;
	}
	
	
}




