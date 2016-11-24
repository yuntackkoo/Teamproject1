package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
						}
						if(Rp.getField(Rp.MyField) != null)
						{
							game.getPlayer().getThey().getFieldlist().setFiled(Rp.getField(Rp.MyField));
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
						}
						if(Rp.getTheyHand() != null)
						{
							game.getPlayer().getThey().setHand(Rp.getTheyHand());
						}
						if(Rp.getDeck(Rp.TheyDeck) != -1)
						{
							game.getPlayer().getThey().setDeck(Rp.getDeck(Rp.TheyDeck));
						}
						if(Rp.getDeck(Rp.MyDeck) != -1)
						{
							game.getPlayer().getMe().setDeck(Rp.getDeck(Rp.MyDeck));
						}
						break;
					}
					case ReplyMassage.TurnStart:
					{
						game.getPlayer().setTurn(true);
						Rq.add(Rp.getUpdate());
						break;
					}
					case ReplyMassage.TurnEnd:
					{
						game.getPlayer().setTurn(false);
						Rq.add(Rp.getUpdate());
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

