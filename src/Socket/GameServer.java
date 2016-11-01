package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import card.Type1;
import card.Type2;
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
	private ServerPlayer hostp = new ServerPlayer();
	private ServerPlayer theyp = new ServerPlayer();
	
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
			if(ssoc.getInetAddress().toString().compareTo("/127.0.0.1")==0)
			{
				outhost = new ObjectOutputStream(ssoc.getOutputStream());
				host = new Server(ssoc,this.host_q);
			}
		}
		catch(Exception e)
		{
			System.out.println("p1연결에 실패 했습니다.");
			System.exit(-1);
		}
		try
		{
			ssoc = soc.accept();
			they = new Server(ssoc,this.they_q);
			outthey = new ObjectOutputStream(ssoc.getOutputStream());
		}
		catch(Exception e)
		{
			System.out.println("p2연결에 실패 했습니다.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		host.start();
		they.start();
			try 
			{
				if(host_q.take().getAction() == Massage.JOIN)
					this.host_state = true;
				System.out.println("조인 메세지를 처리 했습니다");
				if(they_q.take().getAction() == Massage.JOIN)
				{
					System.out.println("메세지 처리");
					this.they_state = true;
				}
				System.out.println("조인 메세지를 처리 했습니다.");
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			this.sendToAll(new GameStart());
			System.out.println("스타트 메세지를 보냈습니다.");
			hostp.getFieldlist().getFiled().add(new Type1());
			theyp.getFieldlist().getFiled().add(new Type2());
		for(;;)
		{
			try {
			if(host_q.isEmpty())
			{
				if(they_q.isEmpty())
				{
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
							System.out.println(theyp.getFieldlist().getFiled().get(0).toString() + "서버");
							System.out.println(hostp.getFieldlist().getFiled().get(0).toString() + "서버");
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
						System.out.println(theyp.getFieldlist().getFiled().get(0).toString() + "서버");
						System.out.println(hostp.getFieldlist().getFiled().get(0).toString() + "서버");
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
					me.interrupt();
				}
			}
			catch(Exception e)
			{}
		}
	}
}
