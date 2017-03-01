
import java.util.*; 
import java.lang.*;
/**
 * Represents a player in the Spy Game 
 *
 * @author Qiying 
 */
public class Player extends Object
{
	private String name; 
	private int budget; 
	private int spycams; 
	private GraphNode startnode; 
	private List<String> locationsSpycam; // a list of spycam locations 

	/**
	 * A constructor for the Player instance. 
	 *
	 * @param name - player's name 
	 * @param budget - player's budget to spend on moves 
	 * @param spycams - the number of spy cams the player starts with 
	 * @param startnode - the node that player starts on 
	 */
	public Player (String name, int budget, int spycams, GraphNode startnode)
	{
		this.name = name; 
		this.budget = budget; 
		this.spycams = spycams; 
		this.startnode = startnode; 
		this.locationsSpycam = new ArrayList<String>(); 
	}

	/**
	 * A getter for the name of the player 
	 *
	 * @return name of player 
	 */
	public String getName()
	{
		return this.name; 
	}

	/**
	 * A getter for the current budget remaining 
	 *
	 * @return remaining budget 
	 */
	public int getBudget() 
	{
		return this.budget; 
	}

	/**
	 * Decrement the budget by the amount specified 
	 *
	 * @param dec - the amount to be decremented from budget 
	 */
	public void decreaseBudget(int dec) 
	{
		if(dec>1)
			this.budget = this.budget - dec; 
	}

	/**
	 * Return false if there are no remaining spycams to drop. If there is no 
	 * spycam at the current location, drop spycam and decrease the spycam 
	 * count 
	 *
	 * @return true if a spycam is dropped 
	 */
	public boolean dropSpycam() 
	{
		// if there are no remaining spy cams to drop 
		if(this.spycams == 0)
		{
			System.out.println("Not enough spycams"); 
			return false; 
		}
		//if there is no spy cam at the current location 
		else if(!startnode.getSpycam())
		{
			this.startnode.setSpycam(true);
			this.spycams--; 
			this.locationsSpycam.add(startnode.getNodeName());
			System.out.println("Dropped a Spy Cam at " + 
					startnode.getNodeName()); 
			return true; 
		}
		// if there is already a spy cam at the current location 
		else if(startnode.getSpycam())
		{
			System.out.println("Already a Spy Cam there"); 
			return false; 
		}	
		return false; 
	}

	/**
	 * Check the node to see if there is a spycam. If there is a spycam at 
	 * the node, remove the spycam from that node and remove the spycam name 
	 * from the list of spycam names. 
	 *
	 * @param node - the node the player asked to remove a spycam from 
	 * @return true if a spycam is retrieved 
	 */
	public boolean pickupSpycam(GraphNode node) 
	{
		if(node.getSpycam() == true)
		{
			//remove the spycam
			node.setSpycam(false);
			this.locationsSpycam.remove(node.getNodeName()); 
			this.getSpycamBack(true);
			return true; 
		}
		else 
			return false; 
	}

	/**
	 * A getter for the number of spycams 
	 *
	 * @return numbers of spycams remaining 
	 */
	public int getSpycams()
	{
		return this.spycams; 
	}

	/**
	 * Returns true if the player successfully moves to this node if the cost 
	 * is greater than 1, decrement budget by that amount 
	 *
	 * @param name - neighboring node to move to 
	 * @return true if the player successfully moves to this node 
	 */
	public boolean move(String name) 
	{
		try
		{
			// target Graphnode 
			GraphNode target = startnode.getNeighbor(name); 
			//cost to get to the target 
			int currCost = startnode.getCostTo(name);  
			if(currCost >1 && this.budget >= currCost)
			{
				// move/update the current node
				this.startnode = target; 
				this.decreaseBudget(currCost);
				return true; 
			}
			else if (currCost <= 1)
			{
				//move/update the current node 
				this.startnode = target; 
				return true; 
			}
			else if (budget<currCost)
			{
				System.out.println("Not enought money cost is " + 
						currCost + " budget is " + budget); 
				return false; 
			} 
		}catch(NotNeighborException e)
		{
			//if the name given is not a name associated with one of neighbors
			System.out.println(name + 
					" is not a neighbor of your current location");
			return false; 
		}
		return false; 
	}

	/**
	 * A getter for the location name of the player 
	 *
	 * @return node label for the current location of the player 
	 */
	public String getLocationName()
	{
		return this.startnode.getNodeName(); 
	}

	/**
	 * Returns the node where the player is currently located. 
	 *
	 * @return player's current node 
	 */
	public GraphNode getLocation() 
	{
		return this.startnode;
	}

	/**
	 * If pickupSpyCam is true, increment the number of spy cams remaining 
	 *
	 * @param pickupSpyCam - true if a spy cam was picked up, false means there
	 * was no spy cam 
	 */
	public void getSpycamBack(boolean pickupSpyCam) 
	{
		if(pickupSpyCam == true) 
			this.spycams++; 
	}

	/**
	 * Display the names of the locations where Spy Cams were dropped and are 
	 * still there. 
	 */
	public void printSpyCamLocations() 
	{
		//create a new iterator 
		Iterator<String> itr = locationsSpycam.iterator(); 
		//iterate through all of the spy cam locations 
		while(itr.hasNext())
		{
			String currLocation = itr.next(); 
			System.out.println("Spy cam at " + currLocation);
		}
	}
}
