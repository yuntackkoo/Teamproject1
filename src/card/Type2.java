package card;

public class Type2 extends Pawn
{
	public Type2()
	{
		super.setCurrentatt(100);
		super.setCurrentlife(500);
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
