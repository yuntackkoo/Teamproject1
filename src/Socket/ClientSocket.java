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
			try {
				ReplyMassage Rp = Rq.take();
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
						if(Rp.getField() != null)
						{
							if(Rp.getField().get(Rp.MyField) != null)
							{
								game.getPlayer().getMe().getFieldlist().setFiled(Rp.getField().get(Rp.MyField));
							}
							if(Rp.getField().get(Rp.MyField) != null)
							{
								game.getPlayer().getThey().getFieldlist().setFiled(Rp.getField().get(Rp.MyField));
							}
						}
						if(Rp.getGrave() != null)
						{
							if(Rp.getGrave().get(Rp.MyGrave) != null)
							{
								game.getPlayer().getMe().getGravelist().setGrave(Rp.getGrave().get(Rp.MyGrave));
							}
							if(Rp.getGrave().get(Rp.TheyGrave) != null)
							{
								game.getPlayer().getThey().getGravelist().setGrave(Rp.getGrave().get(Rp.TheyGrave));
							}
						}
						if(Rp.getMeHand() != null)
						{
							game.getPlayer().getMe().getHandlist().setHand(Rp.getMeHand());
						}
						game.getPlayer().getThey().setHand(Rp.getTheyHand());
						game.getPlayer().getThey().setDeck(Rp.getDeck(Rp.TheyDeck));
						game.getPlayer().getMe().setDeck(Rp.getDeck(Rp.MyDeck));
						break;
					}
					case ReplyMassage.TurnStart:
					{
						game.getPlayer().setTurn(true);
						game.getPlayer().getMe().getHandlist().setHand(Rp.getUpdate().getMeHand());
						System.out.println(Rp.getUpdate().getMeHand().size());
						break;
					}
					case ReplyMassage.TurnEnd:
					{
						game.getPlayer().setTurn(false);
						game.getPlayer().getThey().setHand(Rp.getUpdate().getTheyHand());
						break;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

