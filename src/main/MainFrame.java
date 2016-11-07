package main;
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
	MainMenu main = new MainMenu(game);
	public MainFrame()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(game);
		game.setVisible(false);
		this.add(main);
		main.setVisible(true);
		this.pack();
		FrameUpdate update = new FrameUpdate(this);
		update.fps = 60;
		update.start();
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
		main.game.getPlayer().getFieldlist().update();
		main.game.getPlayer().getHandlist().update();
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
	int width = 400;
	int height = 50;
	int xlocation = (1280-width)/2;
	
	public MainMenu(GameStart s)
	{
		this.s = s;
		this.setPreferredSize(new Dimension(1280, 720));
		this.setLayout(null);
		this.add(start);
		start.setBounds(xlocation, 100, width, height);
		start.addActionListener(new gStart());
		this.add(deckedit);
		deckedit.setBounds(xlocation, 200, width, height);
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
}




