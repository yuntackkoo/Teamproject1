package dataload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import card.CardForm;
import card.Magic;
import card.Pawn;

public class LoadData
{
	//싱글톤 객체
	private Scanner reader;
	private static BufferedImage[] CardImageList;//불러온 이미지 저장 배열
	private static List<CardForm> Card = new ArrayList<CardForm>();//텍스트 파일을 읽어와서 카드로 만들어 저장
	private int max;//카드의 숫자 + 1
	private String[] CardName; // 카드 이름들 저장
	private String[] CardToolTip;// 카드의 툴팁 저장
	private File getMax = new File("Card");//카드 폴더를 파일 형태로 불러옴
	private BufferedImage BackImage;
	private BufferedImage DeckImage;
	private LoadData()//생성자가 private 이 객체는 외부에서 생성할 수 없음
	{
		try
		{
			max = getMax.listFiles().length/2+1;
			CardImageList = new BufferedImage[max];
			CardName = new String[max];
			CardToolTip = new String[max];
			Card.add(new Pawn());
			for (int i = 1; i < max; i++)
			{
				CardImageList[i] = CardImageList[i] = ImageIO.read(new File("Card/" + Integer.toString(i) + ".png"));
				reader = new Scanner(new FileReader("Card/"+Integer.toString(i) + ".txt")).useDelimiter("#");
				CardName[i] = reader.next();//카드의 이름
				if(reader.next().compareTo("Pawn") == 0)//카드의 종류에 따라 다르게 생성함
				{
					Card.add(new Pawn(reader.next(), Integer.parseInt(reader.next()),
							Integer.parseInt(reader.next()), Integer.parseInt(reader.next()), i));//읽어온 텍스트 파일을 파싱 종족,공격력,체력,코스트,툴팁 순서
				}
				else
				{
					Card.add(new Magic(reader.nextInt(),i));
				}
				if(reader.hasNext())
				{
					CardToolTip[i] = reader.next();
				}
			}
			BackImage = ImageIO.read(new File("TheyHand.png"));
			DeckImage = ImageIO.read(new File("DeckImage.jpg"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if(reader != null)//스트림 종료
				reader.close();
		}
		//네임드 카드들 재생성
		for(int i = 1;i<max;i++)
		{
			try
			{
				CardForm tmp = this.Card.get(i).checkSpecialCard(Card.get(i),CardName[i]);
				if(tmp != null)
				{
					this.Card.set(i, tmp);
				}
			}
			catch (NullPointerException e)
			{
			}
		}
	}
	
	public BufferedImage getImage(int i)
	{
		return CardImageList[i];
	}
	
	public CardForm getCard(int i)
	{
		return (CardForm)Card.get(i);
	}
	
	private static class DataLoad
	{
		private static final LoadData Instance = new LoadData();
	}
	
	public static LoadData getInstance()
	{
		return DataLoad.Instance;//싱글톤 패턴 이 객체를 사용 하고 싶으면 이 메소드를 사용 해야 함
	}

	public String getCardName(int index)
	{
		return CardName[index];
	}

	public int getMax()
	{
		return max-1;
	}
	public String getCardToolTip(int index)
	{
		return CardToolTip[index];
	}

	public BufferedImage getBackImage()
	{
		return BackImage;
	}

	public BufferedImage getDeckImage()
	{
		return DeckImage;
	}
	
	
}

