package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import card.CardForm;
import dataload.LoadData;
import player.ServerPlayer;

public class GameServer extends Thread
{
	private GameServer me = this;
	private int portnumber;
	private String p1ipaddr;
	private String p2ipaddr;
	private int currentPlayer;
	private BlockingQueue<Massage> host_q = new ArrayBlockingQueue<>(50);
	private BlockingQueue<Massage> they_q = new ArrayBlockingQueue<>(50);
	private ServerSocket soc=null;
	private Socket ssoc=null;
	private ObjectOutputStream outhost;
	private ObjectOutputStream outthey;
	private Server host;
	private Server they;
	private boolean host_state;
	private boolean they_state;
	private ServerPlayer hostp = new ServerPlayer(true);
	private ServerPlayer theyp = new ServerPlayer(false);
	private boolean state;
	private LoadData data = LoadData.getInstance();
	
	public GameServer(int port)
	{
		this.portnumber = port;
	}
	
	public void sendToAll(ReplyMassage m)
	{
		try
		{
			outhost.writeObject(m);
			outthey.writeObject(m);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendHost(ReplyMassage m)
	{
		try
		{
			outhost.writeObject(m);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendThey(ReplyMassage m)
	{
		try
		{
			outthey.writeObject(m);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		Massage m;
		try
		{
			soc = new ServerSocket(this.portnumber);
			ssoc = soc.accept();
			if (ssoc.getInetAddress().toString().compareTo("/127.0.0.1") == 0)
			{
				outhost = new ObjectOutputStream(ssoc.getOutputStream());
				host = new Server(ssoc, this.host_q);
			} else
			{
				ssoc = soc.accept();
				they = new Server(ssoc, this.they_q);
				outthey = new ObjectOutputStream(ssoc.getOutputStream());
			}
			ssoc = soc.accept();
			they = new Server(ssoc, this.they_q);
			outthey = new ObjectOutputStream(ssoc.getOutputStream());
		}
		catch (Exception e)
		{
			System.out.println("연결에 실패 했습니다.");
			System.exit(-1);
		}
		host.start();
		they.start();
		
		this.sendHost(ReplyMassage.getRMassage(ReplyMassage.JOIN, hostp));
		this.sendThey(ReplyMassage.getRMassage(ReplyMassage.JOIN, theyp));
		System.out.println("스타트 메세지를 보냈습니다.");
		for(;;)
		{
			this.state = true;
			try {
			if(host_q.isEmpty())
			{
				if(they_q.isEmpty())
				{
					this.state = false;
					this.sleep(Long.MAX_VALUE);
				}
				else
				{
					Massage theym = they_q.take();
					switch(theym.getAction())
					{
						case Massage.Draw:
						{
							break;
						}
						case Massage.Chat:
						{
							this.sendToAll(theyp.mProcess(theym));
							break;
						}
						case Massage.Attack:
						{
							theyp.getFieldlist().getFiled().get(0).attack(hostp.getFieldlist().getFiled().get(0));
							ReplyMassage rm = theyp.mProcess(theym);
							ReplyMassage rm1 = theyp.mProcess(theym);
							rm.setMe(theyp.getFieldlist().getFiled());
							rm.setThey(hostp.getFieldlist().getFiled());
							rm1.setThey(theyp.getFieldlist().getFiled());
							rm1.setMe(hostp.getFieldlist().getFiled());
							this.sendThey(rm);
							this.sendHost(rm1);
							break;
						}
						case Massage.JOIN:
						{
							ReplyMassage hostrm;
							ReplyMassage theyrm;
							for(int i = 0;i<theym.getDeckList().size();i++)
							{
								theyp.getDecklist().addCard(data.getCard(theym.getDeckList().remove(0)));
							}
							Collections.shuffle(theyp.getDecklist().getDeck());
							theyp.getHandlist().setChange(true);
							theyp.getDecklist().setChange(true);
							for(int i=0;i<5;i++)
							{
								theyp.getHandlist().addCard(theyp.getDecklist().disCard(0));
							}
							if(hostp.getHandlist().getHand() != null)
							{
								hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
								theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
								hostp.getHandlist().setChange(false);
								hostp.getGravelist().setChange(false);
								hostp.getFieldlist().setChange(false);
								hostp.getDecklist().setChange(false);
								theyp.getHandlist().setChange(false);
								theyp.getGravelist().setChange(false);
								theyp.getFieldlist().setChange(false);
								theyp.getDecklist().setChange(false);
								this.sendHost(hostrm);
								this.sendThey(theyrm);
							}
							break;
						}
					}
				}
			}
			else
			{
				Massage hostm = host_q.take();
				switch(hostm.getAction())
				{
					case ReplyMassage.Draw:
					{
						break;
					}
					case ReplyMassage.Chat:
					{
						this.sendToAll(hostp.mProcess(hostm));
						break;
					}
					case Massage.Attack:
					{
						theyp.getFieldlist().getFiled().get(0).attack(hostp.getFieldlist().getFiled().get(0));
						ReplyMassage rm = theyp.mProcess(hostm);
						ReplyMassage rm1 = theyp.mProcess(hostm);
						rm.setMe(theyp.getFieldlist().getFiled());
						rm.setThey(hostp.getFieldlist().getFiled());
						rm1.setThey(theyp.getFieldlist().getFiled());
						rm1.setMe(hostp.getFieldlist().getFiled());
						this.sendThey(rm1);
						this.sendHost(rm);
						break;
					}
					case Massage.JOIN:
					{
						ReplyMassage hostrm;
						ReplyMassage theyrm;
						for(int i = 0;i<hostm.getDeckList().size();i++)
						{
							hostp.getDecklist().addCard(data.getCard(hostm.getDeckList().remove(0)));
						}
						
						Collections.shuffle(hostp.getDecklist().getDeck());
						hostp.getHandlist().setChange(true);
						hostp.getDecklist().setChange(true);
						for(int i=0;i<5;i++)
						{
							hostp.getHandlist().addCard(hostp.getDecklist().disCard(0));
						}
						if(hostp.getHandlist().getHand() != null)
						{
							hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
							theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
							hostp.getHandlist().setChange(false);
							hostp.getGravelist().setChange(false);
							hostp.getFieldlist().setChange(false);
							hostp.getDecklist().setChange(false);
							theyp.getHandlist().setChange(false);
							theyp.getGravelist().setChange(false);
							theyp.getFieldlist().setChange(false);
							theyp.getDecklist().setChange(false);
							this.sendHost(hostrm);
							this.sendThey(theyrm);
						}
						break;
					}
				}
			}
			}
			catch (InterruptedException e) 
			{
				continue;
			}
		}
	}
	
	private class Server extends Thread
	{
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private Socket ssoc=null;
		private BlockingQueue<Massage> m1; 
		
		public Server(Socket soc,BlockingQueue<Massage> host)
		{
			this.ssoc = soc;
			m1 = host;
		}
		
		public void run()
		{
			try
			{
				in = new ObjectInputStream(ssoc.getInputStream());
				for(;;)
				{
					m1.put((Massage)in.readObject());
					System.out.println(m1.peek());
					if(!me.isState())
					{
						me.interrupt();
					}
				}
			}
			catch(Exception e)
			{}
		}
	}

	public boolean isState()
	{
		return state;
	}
}
