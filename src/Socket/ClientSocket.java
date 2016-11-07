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
		sendMassage((Massage) Massage.getMassage(Massage.JOIN));
		sendMassage(Massage.getMassage(Massage.Draw));
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
						game.getPlayer().showUI();
						break;
					}
					case ReplyMassage.Chat:
					{
						game.getOutput().setText(Rp.getChat());
						break;
					}
					case ReplyMassage.Attack:
					{
						game.getPlayer().getFieldlist().setFiled(Rp.getMe());
						game.getThey().getFieldlist().setFiled(Rp.getThey());
						System.out.println(game.getPlayer().getFieldlist().getFiled());
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

