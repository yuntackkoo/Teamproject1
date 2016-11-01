package card;

public class Type1 extends Pawn
{
	public Type1()
	{
		super.setCurrentatt(10);
		super.setCurrentlife(50);
		super.setLoc("Field1");
	}
	
	@Override
	public void effect() 
	{
	}

	public int abc()
	{
		return super.getCurrentlife();
	}

	@Override
	public String toString()
	{
		return Integer.toString(super.getCurrentlife());
	}
	
	
}
