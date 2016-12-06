import javax.swing.JFrame;

import main.MainFrame;

public class Test
{

	public static void main(String[] args)
	{
		MainFrame m = new MainFrame();
		
		//testframe m = new testframe();
	}
}

class testframe extends JFrame
{
	public testframe()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}
}
