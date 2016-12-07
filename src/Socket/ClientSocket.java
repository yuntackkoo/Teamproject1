package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import main.GameStart;

public class ClientSocket extends Thread
{
	private String ipaddr;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Sender send=null;
	private Recei recei=null;
	private static BlockingQueue<Massage> sendm = new ArrayBlockingQueue<>(50);
	private BlockingQueue<ReplyMassage> Rq = new ArrayBlockingQueue<>(50);
	private GameStart game;
	
	public ClientSocket(String ipaddr,int port,GameStart game)
	{
		this.game = game;
		try
		{
			Socket soc = new Socket(ipaddr,port);
			in = new ObjectInputStream(soc.getInputStream());
			out = new ObjectOutputStream(soc.getOutputStream());
			recei = new Recei(in);
			send = new Sender(out);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		recei.start();
		send.start();
	}
	
	public void run()
	{
		for(;;)
		{
			ReplyMassage Rp;
			try {
				Rp = Rq.take();
				switch(Rp.getAction())
				{
					case ReplyMassage.GameStart:
					{
						game.getServerPanel().setVisible(false);
						game.getPlayer().setVisible(true);
						break;
					}
					case ReplyMassage.Chat:
					{
						game.getPlayer().getRightPanel().getOutput().append(Rp.getChat() + "\n");
						break;
					}
					case ReplyMassage.Update:
					{
						if(Rp.getField(Rp.MyField) != null)
						{
							game.getPlayer().getMe().getFieldlist().setFiled(Rp.getField(Rp.MyField));
							game.getPlayer().getMe().getFieldlist().setChange(true);
						}
						if(Rp.getField(Rp.TheyField) != null)
						{
							game.getPlayer().getThey().getFieldlist().setFiled(Rp.getField(Rp.TheyField));
							game.getPlayer().getThey().getFieldlist().setChange(true);
						}
						if(Rp.getGrave(Rp.MyGrave) != null)
						{
							game.getPlayer().getMe().getGravelist().setGrave(Rp.getGrave(Rp.MyGrave));
						}
						if(Rp.getGrave(Rp.TheyGrave) != null)
						{
							game.getPlayer().getThey().getGravelist().setGrave(Rp.getGrave(Rp.TheyGrave));
						}
						if(Rp.getMeHand() != null)
						{
							game.getPlayer().getMe().getHandlist().setHand(Rp.getMeHand());
							game.getPlayer().getMe().getHandlist().setChange(true);
						}
						
						if(Rp.getTheyHand() != null)
						{
							game.getPlayer().getThey().getHandlist().setHaveCard(Rp.getTheyHand());
							game.getPlayer().getThey().getHandlist().setChange(true);
						}
						if(Rp.getDeck(Rp.TheyDeck) != -1)
						{
							game.getPlayer().getThey().getDecklist().setHaveCard(Rp.getDeck(ReplyMassage.TheyDeck));
						}
						if(Rp.getDeck(Rp.MyDeck) != -1)
						{
							game.getPlayer().getMe().getDecklist().setHaveCard(Rp.getDeck(ReplyMassage.MyDeck));
						}
						
						game.getPlayer().getMe().setMana(Rp.getMana(Updating.MyMana));
						game.getPlayer().getThey().setMana(Rp.getMana(Updating.TheyMana));
						
						game.getPlayer().getMe().setLife(Rp.getLife(Updating.MyLife));
						game.getPlayer().getThey().setLife(Rp.getLife(Updating.TheyLife));
						
						game.getPlayer().getRightPanel().getWpanel().setWhether(Rp.getCurrentW());
						game.getPlayer().getRightPanel().getWpanel().setNextturn(Rp.getNextW());
						game.getPlayer().getRightPanel().repaint();
						game.getPlayer().getLeftPanel().reDraw();
						break;
					}
					case ReplyMassage.TurnStart:
					{
						game.getPlayer().setTurn(true);
						JOptionPane.showMessageDialog(null, "턴이 시작 됐습니다.");
						game.getPlayer().getMe().getHandlist().setturn(true);
						game.getPlayer().getMe().getFieldlist().setturn(true);
						Rq.add(Rp.getUpdate());
						break;
					}
					case ReplyMassage.TurnEnd:
					{
						game.getPlayer().setTurn(false);
						game.getPlayer().getMe().getFieldlist().setatt();
						game.getPlayer().getMe().getHandlist().setturn(false);
						game.getPlayer().getMe().getFieldlist().setturn(false);
						Rq.add(Rp.getUpdate());
						break;
					}
					case ReplyMassage.Defeat:
					{
						JOptionPane.showMessageDialog(null, "패배 하였습니다.");
						System.exit(-1);
						break;
					}
					case ReplyMassage.Victory:
					{
						JOptionPane.showMessageDialog(null, "승리 하였습니다.");
						System.exit(-1);
						break;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				Rp = null;
			}
			
		}
	}
	
	public static void sendMassage(Massage m)
	{
		try {
			sendm.put(m);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private class Sender extends Thread
	{
		ObjectOutputStream out;
		public Sender(ObjectOutputStream out)
		{
			this.out = out;
		}
		
		public void run()
		{
			for(;;)
			{
				try
				{
					out.writeObject(sendm.take());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private class Recei extends Thread
	{
		ObjectInputStream in;
		ReplyMassage Rm;
		
		public Recei(ObjectInputStream in)
		{
			this.in = in;
		}
		
		public void run()
		{

			for(;;)
			{
				try
				{
					Rq.add((ReplyMassage)in.readObject());
				}
				catch(IOException e)
				{
				}
				catch(ClassNotFoundException e)
				{
				}
			}
		}
	}
}

