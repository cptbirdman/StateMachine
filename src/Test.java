

/**
 * Main class which executes the state machine.
 * @author MBE5
 */
public class Test {
	public static void main(String args[])
	{
		//Machine represents the state machine of which you only need one.
		StateMachine machine = new StateMachine();
		//Start passes in the position for the robot.
		machine.Start(1);
	}
}
