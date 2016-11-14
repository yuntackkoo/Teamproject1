package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataload.LoadData;
import net.miginfocom.swing.MigLayout;

public class DeckEditPage extends JPanel
{
	private static final String DefaultListModel = null;
	private File Dlist = new File("Deck");
	private File curDeck = null;
	private Scanner sc;
	private LoadData data = LoadData.getInstance();
	private JList cardlist;
	private JList deckList;
	private CardImage[] cardimage = new CardImage[8];
	private DeckEditPage me = this;
	private FileInputStream stream;
	private Map<Integer,Integer> CardValueList = new HashMap();
	private Map<Integer,String> CardNameList = new HashMap();
	private List<String> ShowCardList = new LinkedList<>();
	private int CardValueSum;
	/**
	 * Create the panel.
	 */
	public DeckEditPage()
	{
		this.setBounds(0, 0, 1280, 720);
		setLayout(new MigLayout("", "[::20][60][60][60][40][60][60][60][40][60][60][60][40][60][60][60][50][grow]", "[::20][grow][grow][grow][::30,grow][grow][grow][grow][80][50][40]"));
		
		for(int i=0;i<8;i++)
		{
			cardimage[i] = new CardImage(i+1,CardValueList,CardNameList,me);
		}
		//카드 이미지 패널 위치 지정
		add(cardimage[0], "cell 1 1 3 3,grow");
		add(cardimage[1], "cell 5 1 3 3,grow");
		add(cardimage[2], "cell 9 1 3 3,grow");
		add(cardimage[3], "cell 13 1 3 3,grow");
		add(cardimage[4], "cell 1 5 3 3,grow");
		add(cardimage[5], "cell 5 5 3 3,grow");
		add(cardimage[6], "cell 9 5 3 3,grow");
		add(cardimage[7], "cell 13 5 3 3,grow");
		
		//덱 편집 패널
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		add(panel_7, "cell 17 1 1 10,grow");
		panel_7.setLayout(new MigLayout("", "[grow][grow][grow]", "[100][grow][50]"));
		
		//스크롤바 삽입
		JScrollPane scrollPane = new JScrollPane();
		panel_7.add(scrollPane, "cell 0 0 3 1,grow");
		
		deckList = new JList(new DefaultListModel());
		deckList.setListData(Dlist.listFiles());
		deckList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) 
			{
				if((File)deckList.getSelectedValue() != null)
				{
					curDeck = (File)deckList.getSelectedValue();//선택된 인덱스의 파일을 받아옴
				}
				try
				{
					stream = new FileInputStream(curDeck);//받아온 파일을 파싱해서 리스트에 저장
					Scanner sc = new Scanner(new InputStreamReader(stream));
					sc.useDelimiter("#");
					CardValueSum = sc.nextInt();
					CardValueList.clear();
					CardNameList.clear();
					for(;sc.hasNext();)
					{
						int tmp = sc.nextInt();
						int tmp2 = sc.nextInt();
						CardValueList.put(tmp, tmp2);
						CardNameList.put(tmp,data.getCardName(tmp) + "      X  " + Integer.toString(tmp2));
					}
				} catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
				catch(NoSuchElementException e2)
				{
					CardValueList.clear();
					CardNameList.clear();
					JOptionPane.showMessageDialog(null, "덱이 비었습니다.");
				}
				finally
				{
					if(sc != null)
						sc.close();
					if(stream != null)
						try
						{
							stream.close();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
					UpdateCardList();
				}
			}
		});
		deckList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(deckList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_7.add(scrollPane_1, "cell 0 1 3 1,grow");
		
		cardlist = new JList();
		scrollPane_1.setViewportView(cardlist);
		cardlist.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) !=0)
				{
					
				}
			}
			
		});
		
		JButton btnNewButton_2 = new JButton("생성");
		btnNewButton_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String tmp;
				tmp = JOptionPane.showInputDialog(null, "덱의 이름을 입력하세요");
				File newdeck = new File(tmp);
				try
				{
					FileWriter rw = new FileWriter("Deck/" + tmp + ".txt", false);
					rw.close();
					deckList.setListData(new File("Deck").listFiles());
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		panel_7.add(btnNewButton_2, "cell 0 2,growx");
		
		JButton btnNewButton_3 = new JButton("저장");
		panel_7.add(btnNewButton_3, "cell 1 2,growx");
		btnNewButton_3.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				StringBuffer tmp = new StringBuffer() ;
				FileWriter out = null;
				CardValueSum = 0;
				for(Map.Entry<Integer,Integer> Entry : CardValueList.entrySet())
				{
					tmp.append(Integer.toString(Entry.getKey()) + "#" + Integer.toString(Entry.getValue()) + "#");
					CardValueSum += Entry.getValue();
				}
				try
				{
					tmp.insert(0, Integer.toString(CardValueSum) + "#");
					out = new FileWriter(curDeck,false);
					out.write(tmp.toString());
				}
				catch(IOException e1)
				{
					JOptionPane.showMessageDialog(null, "저장에 실패 했습니다.");
				}
				finally
				{
					if(out != null)
						try
						{
							out.close();
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
		});
		
		JButton btnNewButton_4 = new JButton("삭제");
		btnNewButton_4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					if(JOptionPane.showConfirmDialog(null, "정말 지우시겠 습니까?")==0)
					{
						File tmp = new File(curDeck.getPath());
						tmp.delete();
						deckList.setListData(new File("Deck").listFiles());
						
					}
				}
				catch(NullPointerException e1)
				{
					JOptionPane.showMessageDialog(null,"덱이 선택 되지 않았습니다.");
				}
			}
		});
		panel_7.add(btnNewButton_4, "cell 2 2,growx");
		
		
		JButton btnNewButton = new JButton("뒤로");
		add(btnNewButton, "cell 5 9 2 1,grow");
		btnNewButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0;i<8;i++)
				{
					cardimage[i].setPage(-1);
				}
				me.repaint();
			}
		});
		
		JButton btnNewButton_1 = new JButton("앞으로");
		add(btnNewButton_1, "cell 9 9 2 1,grow");
		btnNewButton_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0;i<8;i++)
				{
					cardimage[i].setPage(1);
				}
				me.repaint();
			}
		});
		
	}
	
	public void UpdateCardList()
	{
		ShowCardList.clear();
		System.out.println(ShowCardList.isEmpty());
		for(String Card : CardNameList.values())
		{
			ShowCardList.add(Card);
		}
		cardlist.setListData((Object[]) ShowCardList.toArray());
	}

	public int getCardValueSum()
	{
		return CardValueSum;
	}

	public void setCardValueSum()
	{
		CardValueSum++;
	}
}

