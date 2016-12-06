package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Socket.ClientSocket;
import Socket.GameServer;
import Socket.Massage;
import player.Player;

public class GameStart extends JPanel
{
	private JTextArea ip = new JTextArea("127.0.0.1");
	private JTextArea port = new JTextArea("8000");
	private JButton playServer = new JButton("방 만들기");
	private JButton plyClient = new JButton("게임 참가");
	private JTextField input = new JTextField("채팅 입력");
	private JTextArea output = new JTextArea();
	private Player player;
	private GameStart game = this;
	private JPanel ServerPanel = new JPanel();
	private JList<File>decklist = new JList<File>(); 
	private JScrollPane decklistscroll = new JScrollPane();
	
	
	public GameStart(JLayeredPane jpl)
	{
		player = new Player(jpl);
		this.setLayout(null);
		this.setBounds(0, 0, 1280, 720);
		this.add(ServerPanel);
		ServerPanel.setBounds(0, 0, 1280, 720);
		ServerPanel.setLayout(null);
		this.add(player);
		player.setVisible(false);
		
		decklistscroll.setViewportView(decklist);
		
		ServerPanel.add(ip);
		ip.setBounds(100, 100, 750, 20);
		ip.setEditable(true);
		
		ServerPanel.add(port);
		port.setBounds(100, 150, 750, 20);
		port.setEditable(true);
		
		ServerPanel.add(playServer);
		playServer.setBounds(100, 300, 200, 150);
		playServer.addActionListener(new ServerAction());
		
		ServerPanel.add(plyClient);
		plyClient.setBounds(500, 300, 200, 150);
		plyClient.addActionListener(new ClientAction());
		
		ServerPanel.add(decklistscroll);
		decklistscroll.setBounds(900, 300, 150, 50);
		decklist.setListData(new File("Deck").listFiles());
		
	}
	
	public JPanel getServerPanel() {
		return ServerPanel;
	}

	private class ServerAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			File tmp;
			tmp = decklist.getSelectedValue();
			Scanner sc = null;
			List<Integer> deck = new LinkedList<>();
			try
			{
				sc = new Scanner(new FileReader(tmp));
				sc.useDelimiter("#");
				if(sc.nextInt() < 40)
					JOptionPane.showMessageDialog(null, "덱이 미완성 입니다.");
				else
				{
					for(;sc.hasNext();)
					{
						Integer cardnumber = sc.nextInt();
						Integer cardvalue = sc.nextInt();
						for(int i =0;i<cardvalue;i++)
						{
							deck.add(cardnumber);
						}
					}
				}
				System.out.println(deck.toString());
			}
			catch (FileNotFoundException e1)
			{
				JOptionPane.showMessageDialog(null, "덱이 존재하지 않습니다.");
			}
			catch (NullPointerException e2)
			{
				JOptionPane.showMessageDialog(null, "덱을 선택해 주세요.");
			}
			finally
			{
				if(sc != null)
					sc.close();
			}
			
			player.setTurn(true);
			
			GameServer soc = new GameServer(Integer.parseInt(port.getText()));
			soc.start();
			ClientSocket csoc = new ClientSocket("127.0.0.1", Integer.parseInt(port.getText()), game);
			csoc.start();
			Massage msg = Massage.getMassage(Massage.JOIN);
			msg.setDeckList(deck);
			ClientSocket.sendMassage(msg);
			
		}
	}
	
	private class ClientAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			File tmp;
			tmp = decklist.getSelectedValue();
			Scanner sc = null;
			List<Integer> deck = new ArrayList<>();
			try
			{
				sc = new Scanner(new FileReader(tmp));
				sc.useDelimiter("#");
				if(sc.nextInt() < 40)
					JOptionPane.showMessageDialog(null, "덱이 미완성 입니다.");
				else
				{
					for(;sc.hasNext();)
					{
						Integer cardnumber = sc.nextInt();
						Integer cardvalue = sc.nextInt();
						for(int i =0;i<cardvalue;i++)
						{
							deck.add(cardnumber);
						}
					}
				}
			}
			catch (FileNotFoundException e1)
			{
				JOptionPane.showMessageDialog(null, "덱이 존재하지 않습니다.");
			}
			finally
			{
				if(sc != null)
					sc.close();
			}
			ClientSocket csoc = new ClientSocket(ip.getText(), Integer.parseInt(port.getText()),game);
			csoc.start();
			Massage msg = Massage.getMassage(Massage.JOIN);
			msg.setDeckList(deck);
			ClientSocket.sendMassage(msg);
			player.setTurn(false);
		}
	}
	
	public JTextArea getIp() {
		return ip;
	}

	public void setIp(JTextArea ip) {
		this.ip = ip;
	}

	public JTextArea getPort() {
		return port;
	}

	public void setPort(JTextArea port) {
		this.port = port;
	}

	public JButton getPlayServer() {
		return playServer;
	}

	public void setPlayServer(JButton playServer) {
		this.playServer = playServer;
	}

	public JButton getPlyClient() {
		return plyClient;
	}

	public void setPlyClient(JButton plyClient) {
		this.plyClient = plyClient;
	}

	public JTextArea getOutput() {
		return output;
	}

	public void setOutput(JTextArea output) {
		this.output = output;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
	
}