package dataload;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import card.CardForm;
import card.Pawn;

public class LoadData
{
	private Scanner reader;
	private static Image[] CardImageList;
	private static List<CardForm> Card = new ArrayList<CardForm>();
	private int max;
	private String[] CardName;
	private File getMax = new File("Card");
	private LoadData()
	{
		try
		{
			max = getMax.listFiles().length/2;
			CardImageList = new Image[max];
			CardName = new String[max];
			Toolkit tool = Toolkit.getDefaultToolkit();
			for (int i = 0; i < max; i++)
			{
				CardImageList[i] = tool.getImage("Card/"+Integer.toString(i+1) + ".png");
				reader = new Scanner(new FileReader("Card/"+Integer.toString(i+1) + ".txt")).useDelimiter("#");
				CardName[i] = reader.next();
				if(reader.next().compareTo("Pawn") == 0)
				{
					
					Card.add(new Pawn(reader.next(), Integer.parseInt(reader.next()),
							Integer.parseInt(reader.next()), Integer.parseInt(reader.next()), i));
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if(reader != null)
				reader.close();
		}
	}
	
	public Image getImage(int i)
	{
		return CardImageList[i];
	}
	
	public CardForm getCard(int i)
	{
		return Card.get(i);
	}
	
	private static class DataLoad
	{
		private static final LoadData Instance = new LoadData();
	}
	
	public static LoadData getInstance()
	{
		return DataLoad.Instance;
	}

	public String getCardName(int index)
	{
		return CardName[index];
	}

	public int getMax()
	{
		return max;
	}
	
	
}

