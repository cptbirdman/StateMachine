
/**
 * This class is an override of the standard State which waits for five seconds.
 * @author MBE5
 *
 */
public class WaitFiveState extends State{
	
	/**
	 * This is an override of the standard constructor that overrides the duration to 5000ms.
	 * @param name_i
	 * @param position_i
	 */
	public WaitFiveState(String name_i, int[] position_i) {
		super(5000, name_i, position_i );
		outputOnce = false;
	}
	
	//override the action to turn or move straight
	protected void action()
	{
		if( !outputOnce )
		{
			outputOnce = true;
			System.out.println("WaitFiveState: Output once");
		}
	}

}
