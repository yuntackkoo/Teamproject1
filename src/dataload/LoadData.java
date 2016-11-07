package dataload;

import java.awt.Image;
import java.awt.Toolkit;
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
	private static Image[] CardImage;
	private static List<CardForm> Card = new ArrayList<CardForm>();
	private int max;

	private LoadData()
	{
		try
		{
			reader = new Scanner(new FileInputStream("Card.txt"));
			max = reader.nextInt();
			CardImage = new Image[max];
			System.out.println(max);
			reader.close();

			Toolkit tool = Toolkit.getDefaultToolkit();
			for (int i = 0; i < max; i++)
			{
				CardImage[i] = tool.getImage(Integer.toString(i+1) + ".png");
				reader = new Scanner(new FileReader(Integer.toString(i+1) + ".txt")).useDelimiter("#");
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
		return CardImage[i];
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
}

