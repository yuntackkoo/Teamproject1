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
					//클라이언트가 보낸 메세지 처리
					Massage theym = they_q.take();
					switch(theym.getAction())
					{
						case ReplyMassage.Draw:
						{
							CardForm drawcard = theyp.getDecklist().disCard(0);
							theyp.getDecklist().setChange(true);
							if(theyp.getHandlist().getHand().size() < 10)
							{
								theyp.getHandlist().addCard(drawcard);
								theyp.getHandlist().setChange(true);
							}
							else
							{
								theyp.getGravelist().addCard(drawcard);
								theyp.getGravelist().setChange(true);
							}
							ReplyMassage hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
							ReplyMassage theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
							this.changestate();
							this.sendHost(hostrm);
							this.sendThey(theyrm);
							break;
						}
						case Massage.Chat:
						{
							this.sendToAll(theyp.mProcess(theym));
							break;
						}
						case Massage.Summon:
						{
							theyp.getFieldlist().setChange(true);
							theyp.getHandlist().setChange(true);
							theyp.getFieldlist().addCard(theyp.getHandlist().disCard(theym.getHandle()));
							ReplyMassage hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
							ReplyMassage theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
							this.changestate();
							this.sendHost(hostrm);
							this.sendThey(theyrm);
							break;
						}
						case Massage.Attack:
						{
							theyp.getFieldlist().setChange(true);
							hostp.getFieldlist().setChange(true);
							theyp.getFieldlist().getFiled().get(theym.getMyFieldCard())
							.attack(hostp.getFieldlist().getFiled().get(theym.getAttackTarget()));
							this.checkLife();
							ReplyMassage hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
							ReplyMassage theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
							this.changestate();
							this.sendHost(hostrm);
							this.sendThey(theyrm);
							break;
						}
						case Massage.JOIN:
						{
							ReplyMassage hostrm;
							ReplyMassage theyrm;
							int size = theym.getDeckList().size();
							for(int i = 0;i<size;i++)
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
								this.changestate();
								this.sendHost(hostrm);
								this.sendThey(theyrm);
							}
							break;
						}
						case Massage.TurnEnd:
						{
							ReplyMassage theyrm = ReplyMassage.getRMassage(ReplyMassage.TurnEnd);
							ReplyMassage hostrm = ReplyMassage.getRMassage(ReplyMassage.TurnStart);
							
							//카드 드로우
							CardForm drawcard = hostp.getDecklist().disCard(0);
							hostp.getDecklist().setChange(true);
							if(hostp.getHandlist().getHand().size() < 10)
							{
								hostp.getHandlist().addCard(drawcard);
								hostp.getHandlist().setChange(true);
							}
							else
							{
								hostp.getGravelist().addCard(drawcard);
								hostp.getGravelist().setChange(true);
							}
							
							
							theyrm.setUpdate(ReplyMassage.getRMassage(false, hostp, theyp));
							hostrm.setUpdate(ReplyMassage.getRMassage(true, hostp, theyp));
							this.changestate();
							this.sendThey(theyrm);
							this.sendHost(hostrm);
							break;
						}
					}
				}
			}
			else
			{
				//호스트가 보낸 메세지 처리
				Massage hostm = host_q.take();
				switch(hostm.getAction())
				{
					case ReplyMassage.Draw:
					{
						CardForm drawcard = theyp.getDecklist().disCard(0);
						hostp.getDecklist().setChange(true);
						if(hostp.getHandlist().getHand().size() < 10)
						{
							hostp.getHandlist().addCard(drawcard);
							hostp.getHandlist().setChange(true);
						}
						else
						{
							hostp.getGravelist().addCard(drawcard);
							hostp.getGravelist().setChange(true);
						}
						ReplyMassage hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
						ReplyMassage theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
						this.changestate();
						this.sendHost(hostrm);
						this.sendThey(theyrm);
						break;
					}
					case ReplyMassage.Chat:
					{
						this.sendToAll(hostp.mProcess(hostm));
						break;
					}
					case Massage.Summon:
					{
						hostp.getFieldlist().setChange(true);
						hostp.getHandlist().setChange(true);
						hostp.getFieldlist().addCard(hostp.getHandlist().disCard(hostm.getHandle()));
						ReplyMassage hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
						ReplyMassage theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
						this.changestate();
						this.sendHost(hostrm);
						this.sendThey(theyrm);
						break;
					}
					case Massage.Attack:
					{
						theyp.getFieldlist().setChange(true);
						hostp.getFieldlist().setChange(true);
						hostp.getFieldlist().getFiled().get(hostm.getMyFieldCard())
						.attack(theyp.getFieldlist().getFiled().get(hostm.getAttackTarget()));
						this.checkLife();
						ReplyMassage hostrm = ReplyMassage.getRMassage(true, hostp, theyp);
						ReplyMassage theyrm = ReplyMassage.getRMassage(false, hostp, theyp);
						this.changestate();
						this.sendHost(hostrm);
						this.sendThey(theyrm);
						break;
					}
					case Massage.JOIN:
					{
						ReplyMassage hostrm;
						ReplyMassage theyrm;
						int size = hostm.getDeckList().size();
						for(int i = 0;i<size;i++)
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
							this.changestate();
							this.sendHost(hostrm);
							this.sendThey(theyrm);
						}
						break;
					}
					case Massage.TurnEnd:
					{
						ReplyMassage hostrm = ReplyMassage.getRMassage(ReplyMassage.TurnEnd);
						ReplyMassage theyrm = ReplyMassage.getRMassage(ReplyMassage.TurnStart);
						CardForm drawcard = theyp.getDecklist().disCard(0);
						theyp.getDecklist().setChange(true);
						if(theyp.getHandlist().getHand().size() < 10)
						{
							theyp.getHandlist().addCard(drawcard);
							theyp.getHandlist().setChange(true);
						}
						else
						{
							theyp.getGravelist().addCard(drawcard);
							theyp.getGravelist().setChange(true);
						}
						theyrm.setUpdate(ReplyMassage.getRMassage(false, hostp, theyp));
						hostrm.setUpdate(ReplyMassage.getRMassage(true, hostp, theyp));
						this.changestate();
						this.sendThey(theyrm);
						this.sendHost(hostrm);
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
	
	public void changestate()
	{
		hostp.getHandlist().setChange(false);
		hostp.getGravelist().setChange(false);
		hostp.getFieldlist().setChange(false);
		hostp.getDecklist().setChange(false);
		theyp.getHandlist().setChange(false);
		theyp.getGravelist().setChange(false);
		theyp.getFieldlist().setChange(false);
		theyp.getDecklist().setChange(false);
	}
	
	public void checkLife()
	{
		for(int i =0;i<hostp.getFieldlist().getFiled().size();i++)
		{
			if(hostp.getFieldlist().getFiled().get(i).getCurrentlife()<=0)
			{
				hostp.getGravelist().addCard(hostp.getFieldlist().disCard(i));
				hostp.getFieldlist().setChange(true);
				hostp.getGravelist().setChange(true);
			}
		}
		for(int i =0;i<theyp.getFieldlist().getFiled().size();i++)
		{
			if(theyp.getFieldlist().getFiled().get(i).getCurrentlife()<=0)
			{
				theyp.getGravelist().addCard(theyp.getFieldlist().disCard(i));
				theyp.getFieldlist().setChange(true);
				theyp.getGravelist().setChange(true);
			}
		}
	}
}
