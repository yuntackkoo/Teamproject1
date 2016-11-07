package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Socket.ClientSocket;
import Socket.GameServer;
import Socket.Massage;
import card.Type1;
import card.Type2;
import player.Player;
import player.They;

public class GameStart extends JPanel
{
	private JTextArea ip = new JTextArea("127.0.0.1");
	private JTextArea port = new JTextArea("8000");
	private JButton playServer = new JButton("방 만들기");
	private JButton plyClient = new JButton("게임 참가");
	private JTextField input = new JTextField("채팅 입력");
	private JTextArea output = new JTextArea();
	private Player player = new Player();
	private They they = new They();
	private GameStart game = this;
	private JPanel ServerPanel = new JPanel();

	public GameStart()
	{
		this.setLayout(null);
		this.setBounds(0, 0, 1280, 720);
		this.add(ServerPanel);
		ServerPanel.setBounds(0, 0, 1280, 720);
		ServerPanel.setLayout(null);
		this.add(player);
		
		
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
		
		player.add(input);
		input.setEditable(true);
		input.setBounds(0, 0, 750, 20);
		input.addActionListener(new Chatclass());
		
		player.add(output);
		output.setBounds(0, 100, 750, 20);
		output.setEditable(false);
	}
	
	public JPanel getServerPanel() {
		return ServerPanel;
	}

	private class ServerAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			GameServer soc = new GameServer(Integer.parseInt(port.getText()));
			soc.start();
			ClientSocket csoc = new ClientSocket("127.0.0.1", Integer.parseInt(port.getText()), game);
			csoc.start();
			
		}
	}
	
	private class ClientAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			ClientSocket csoc = new ClientSocket(ip.getText(), Integer.parseInt(port.getText()),game);
			csoc.start();
		}
	}
	
	private class Chatclass implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Massage m = Massage.getMassage(Massage.Chat);
			m.setChat(input.getText());
			ClientSocket.sendMassage(m);
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

	public They getThey()
	{
		return they;
	}

	public void setThey(They they)
	{
		this.they = they;
	}
	
	
}