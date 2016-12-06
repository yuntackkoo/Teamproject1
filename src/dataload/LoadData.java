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
	//�̱��� ��ü
	private Scanner reader;
	private static BufferedImage[] CardImageList;//�ҷ��� �̹��� ���� �迭
	private static List<CardForm> Card = new ArrayList<CardForm>();//�ؽ�Ʈ ������ �о�ͼ� ī��� ����� ����
	private int max;//ī���� ���� + 1
	private String[] CardName; // ī�� �̸��� ����
	private String[] CardToolTip;// ī���� ���� ����
	private File getMax = new File("Card");//ī�� ������ ���� ���·� �ҷ���
	private BufferedImage BackImage;
	private BufferedImage DeckImage;
	private LoadData()//�����ڰ� private �� ��ü�� �ܺο��� ������ �� ����
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
				CardName[i] = reader.next();//ī���� �̸�
				if(reader.next().compareTo("Pawn") == 0)//ī���� ������ ���� �ٸ��� ������
				{
					Card.add(new Pawn(reader.next(), Integer.parseInt(reader.next()),
							Integer.parseInt(reader.next()), Integer.parseInt(reader.next()), i));//�о�� �ؽ�Ʈ ������ �Ľ� ����,���ݷ�,ü��,�ڽ�Ʈ,���� ����
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
			if(reader != null)//��Ʈ�� ����
				reader.close();
		}
		//���ӵ� ī��� �����
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
		return DataLoad.Instance;//�̱��� ���� �� ��ü�� ��� �ϰ� ������ �� �޼ҵ带 ��� �ؾ� ��
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

