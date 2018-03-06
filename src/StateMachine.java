
/**
 * This represents the state machine for the robot.
 * @author MBE5
 */
public class StateMachine {
	private State rootNode;
	private State currentNode;
	
	
	/**
	 * Constructs the State Machine with a generic Entry node and no wait time.
	 * Then we configure the states.
	 */
	public StateMachine()
	{
		rootNode = new State(0,"Entry");
		currentNode = rootNode;
		ConfigureStates();
	}
	
	/**
	 * This method is a simple script for creating the nodes within the state machine hierarchy.
	 */
	private void ConfigureStates()
	{
		//for position 1 we want to:
		//WaitFive -> WaitOne -> WaitFive
		
		//for position 2 we want to:
		//WaitOne -> WaitOne -> WaitOne
		WaitFiveState firstNode_one = new WaitFiveState("FirstNodeOne", new int[] {1});
		WaitOneState firstNode_two = new WaitOneState("FirstNodeTwo", new int[] {2});
		
		WaitOneState secondNode = new WaitOneState("SecondNodeOne", new int[] {1,2});
		
		WaitFiveState thirdNode_one = new WaitFiveState("ThirdNodeOne", new int[] {1});
		WaitOneState thirdNode_two = new WaitOneState("ThirdNodeTwo", new int[] {2});
		
		//here we need to add the children to the tree
		secondNode.addState(thirdNode_one);
		secondNode.addState(thirdNode_two);
		
		firstNode_one.addState(secondNode);
		firstNode_two.addState(secondNode);
		
		rootNode.addState(firstNode_two);
		rootNode.addState(firstNode_one);
	}
	
	/**
	 * Start calls the start of the root node and runs until we reach the end of the tree.
	 * @param position_i
	 */
	public void Start(int position_i)
	{
		System.out.println("StateMachine: Started");
		rootNode.Start(position_i);
		Run();
	}
	
	/**
	 * This method executes until the end of the tree is reached.
	 */
	public void Run()
	{
		//while the next node is not the end of the tree
		while ( !currentNode.getIsFinished() )
		{
			//run the current node we're pointing too
			currentNode = currentNode.Run();
		}
		System.out.println("StateMachine: Finished");
	}
	
}
