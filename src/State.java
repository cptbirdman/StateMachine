import java.util.ArrayList;

/**
 * The state represents a node within the state machine tree.
 * @author MBE5
 */
public class State {
	//are we finished?
	private boolean isFinished = false;
	//the duration for which we will wait
	private double duration = 0;
	//records the time we first entered the node state
	private long firstRun = 0;
	//just a helpful tag for naming the node
	private String name = "";
	//all of the positions this node is applicable to
	private int[] position;
	//the currently selected position for traversing the tree
	private int runningPos;
	//helpful for action
	protected boolean outputOnce = false;
	//list of children for this node
	private ArrayList<State> internalStates;
	
	/**
	 * Helpful costructor when applicable positions are not initially known.
	 * @param duration_i
	 * @param name_i
	 */
	public State(double duration_i, String name_i)
	{
		int[] positions = new int[0];
		initialize(duration_i,name_i, positions);
	}
	
	/**
	 * Main constructor for the State which sets the duration the node will execute, 
	 * the name, and the applicable positions.
	 * @param duration_i
	 * @param name_i
	 * @param position_i
	 */
	public State(double duration_i, String name_i, int[] position_i)
	{
		initialize(duration_i,name_i,position_i);
	}
	
	private void initialize(double duration_i, String name_i, int[] position_i)
	{
		//initialize the children, assign the duration name and position
		internalStates = new ArrayList<State>();
		duration = duration_i;
		name = name_i;
		position = position_i;
	}
	
	public int[] getPosition()
	{
		return position;
	}
	
	public boolean getIsFinished()
	{
		return isFinished;
	}
	
	public void setPositions(int[] position_i)
	{
		position = position_i;
	}
	
	/**
	 * Adds children nodes to this node.
	 * @param state_i
	 */
	public void addState(State state_i)
	{
		internalStates.add(state_i);
	}
	
	/**
	 * Initializes the node to start.
	 * @param runningPos_i
	 */
	public void Start(int runningPos_i)
	{
		System.out.println("State: Start " + name);
		isFinished = false;
		firstRun = System.currentTimeMillis();
		runningPos = runningPos_i;
	}
	
	/**
	 * This method is meant to be overloaded and will be where the robot will perform a given action.
	 */
	protected void action()
	{
		//this is where you might turn
		if( !outputOnce )
		{
			outputOnce = true;
			System.out.println("State: Output once");
		}
	}
	
	/**
	 * This method executes within the state machine loop.
	 * @return State
	 */
	public State Run()
	{
		State currentState = this;
		//determine the difference between when we started and now
		long currentTime = System.currentTimeMillis();
		long diffTime = currentTime - firstRun;
		
		//this is where the turn or go straight would go
		action();
		
		//if we've executed long enough we need to stop
		if( diffTime >= duration )
		{
			//lets find and return the next state if there is one
			currentState = Stop();
		}
		return currentState;
	}
	
	public State Stop()
	{
		//lets make sure we mark ourselves as finished so the state machine can stop if necessary
		isFinished = true;
		//now lets find the next state
		return runNextState();
	}
	
	public String getName()
	{
		return name;
	}
	
	/**
	 * We find the next state if there is one. If not we'll return ourselves and stop.
	 * @return State
	 */
	private State runNextState()
	{
		//System.out.println("State: RunNextState");
		
		//by default we should return ourselves
		State nextState = this;
		boolean found = false;
		
		//iterate over all of our children
		for( int i = 0; i < internalStates.size(); i++ )
		{
			State i_state = internalStates.get(i);
			int[] positions = i_state.getPosition();
			//look through all applicable positions and match on the selected position
			for( int a = 0; a < positions.length; a++ )
			{
				//we've found our next state
				if( positions[a] == runningPos )
				{
					nextState = i_state;
					found = true;
				}
			}
		}
		
		if(found)
		{
			//System.out.println("State: Found " + Integer.toString(internalStates.size()));
			//System.out.println("State: NextState " + nextState.getName() + " This: " + name);
			nextState.Start(runningPos);
			System.out.println("State: Duration " + Double.toString(nextState.getDuration()));
		}
		else
		{
			//System.out.println("State: Not found " + Integer.toString(internalStates.size()));
		}
		return nextState;
	}
	
	public double getDuration()
	{
		return duration;
	}
}
