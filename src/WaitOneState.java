
public class WaitOneState extends State{

	public WaitOneState(String name_i, int[] position_i) {
		super(1000, name_i, position_i );
	}
	
	protected void action()
	{
		if( !outputOnce )
		{
			outputOnce = true;
			System.out.println("WaitOneState: Output once");
		}
	}

}
